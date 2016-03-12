package com.milkenknights.frc2016.subsystems;

import com.milkenknights.frc2016.Constants;
import com.milkenknights.frc2016.subsystems.controllers.DriveStraightController;
import com.milkenknights.frc2016.subsystems.controllers.TurnInPlaceController;
import com.milkenknights.util.MkCanTalon;
import com.milkenknights.util.MkEncoder;
import com.milkenknights.util.Pose;
import com.milkenknights.util.drive.DriveAbstract;
import com.milkenknights.util.drive.MotorPairSignal;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive extends DriveAbstract {
        
    private MkCanTalon leftMotor;
    private MkCanTalon rightMotor;
    private Encoder leftEncoder;
    private Encoder rightEncoder;
    private Solenoid shifter;
    private AHRS gyro;
    
    private DriveController controller;
    private DriveGear driveGear = DriveGear.LOW;
    private final Pose cachedPose = new Pose(0, 0, 0, 0, 0, 0);
    
    public enum DriveGear {
        LOW(false), HIGH(true);
        
        public final boolean shifter;
        DriveGear(final boolean shifter) {
            this.shifter = shifter;
        }
    }

    /**
     * Create a new drive subsystem.
     * 
     * @param name The name of this subsystem
     * @param leftMotor The left motor object
     * @param rightMotor The right motor object
     * @param leftEncoder The left encoder object
     * @param rightEncoder The right encoder object
     * @param gyro The AHRS object (NavX)
     * @param shifter The solenoid object to shift gears
     */
    public Drive(final String name, final MkCanTalon leftMotor, final MkCanTalon rightMotor,
            final MkEncoder leftEncoder, final MkEncoder rightEncoder, final Solenoid shifter, final AHRS gyro) {
        super(name);
        
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
        this.leftEncoder = leftEncoder;
        this.rightEncoder = rightEncoder;
        this.shifter = shifter;
        this.gyro = gyro;
        
        this.leftMotor.setInverted(true);
        
        this.leftEncoder.setDistancePerPulse(-Math.PI * Constants.Subsystems.Drive.WHEEL_DIAMETER
                * Constants.Subsystems.Drive.GEAR_RATIO / leftEncoder.getPulsesPerRevolution());
        this.rightEncoder.setDistancePerPulse(Math.PI * Constants.Subsystems.Drive.WHEEL_DIAMETER
                * Constants.Subsystems.Drive.GEAR_RATIO / rightEncoder.getPulsesPerRevolution());
    }

    @Override
    public void update() {        
        if (controller == null) {
            return;
        }
        setDriveOutputs(controller.update(getPhysicalPose()));
    }

    @Override
    public void updateSmartDashboard() {
        SmartDashboard.putBoolean("Gyro Connected", gyro.isConnected());
        SmartDashboard.putNumber("Drive: Heading", getPhysicalPose().heading);
        SmartDashboard.putNumber("Drive: Left Distance", getPhysicalPose().leftDistance);
        SmartDashboard.putNumber("Drive: Right Distance", getPhysicalPose().rightDistance);
        SmartDashboard.putNumber("Drive: Left Speed", getPhysicalPose().leftVelocity);
        SmartDashboard.putNumber("Drive: Right Speed", getPhysicalPose().rightVelocity);
        SmartDashboard.putNumber("Drive: Heading Speed", getPhysicalPose().headingVelocity);
        
        if (controller != null) {
            SmartDashboard.putNumber("Drive: Error", controller.getError());
        }
    }

    @Override
    public void setOpenLoop(final MotorPairSignal signal) {
        controller = null;
        setDriveOutputs(signal);
    }

    @Override
    public void setDistanceSetpoint(final double distance) {
        setDistanceSetpoint(distance, Constants.kDriveMaxSpeedInchesPerSec);
    }

    @Override
    public void setDistanceSetpoint(final double distance, final double velocity) {
        controller = new DriveStraightController(getPoseToContinueFrom(false), distance, 
                Math.min(Constants.kDriveMaxSpeedInchesPerSec, Math.max(velocity, 0)));
    }

    @Override
    public void setTurnSetpoint(final double heading) {
        setTurnSetpoint(heading, Constants.kTurnMaxSpeedRadsPerSec);
    }

    @Override
    public void setTurnSetpoint(final double heading, final double velocity) {
        controller = new TurnInPlaceController(getPoseToContinueFrom(true), heading, 
                Math.min(Constants.kTurnMaxSpeedRadsPerSec, Math.max(velocity, 0)));
    }
    
    /**
     * If the current controller is a TurnInPlaceController this method will return the heading goal.  If not, it will
     * return 0.
     */
    public double getTurnSetpoint() {
        return controller instanceof TurnInPlaceController ? ((TurnInPlaceController) controller).getHeadingGoal() : 0;
    }
    
    /**
     * Gets if the drive is under closed loop control.
     */
    public boolean isClosedLoop() {
        return controller != null;
    }
    
    /**
     * Sets the gear of the drive train.
     * @param gear The gear to set to
     */
    public void setGear(final DriveGear gear) {
        driveGear = gear;
        shifter.set(gear.shifter);
    }
    
    /**
     * Get what gear the drive is in. 
     * 
     * @return The current gear
     */
    public DriveGear getGear() {
        return driveGear;
    }

    @Override
    public void reset() {
        leftEncoder.reset();
        rightEncoder.reset();
        gyro.reset();
    }

    @Override
    public Pose getPhysicalPose() {
        cachedPose.reset(leftEncoder.getDistance(), rightEncoder.getDistance(), leftEncoder.getRate(),
                rightEncoder.getRate(), gyro.getYaw(), gyro.getRate());
        return cachedPose;
    }
    
    private void setDriveOutputs(final MotorPairSignal signal) {
        SmartDashboard.putString("Drive: Last Motor Outputs", signal.toString());
        leftMotor.set(signal.leftMotor);
        rightMotor.set(signal.rightMotor);
    }
    
    private Pose getPoseToContinueFrom(final boolean forTurnController) {
        Pose poseToContinueFrom = getPhysicalPose();
        System.out.println(controller);
        if (!forTurnController && controller instanceof TurnInPlaceController) {
            final Pose poseToUse = getPhysicalPose();
            poseToUse.heading = ((TurnInPlaceController) controller).getHeadingGoal();
            poseToUse.headingVelocity = 0;
            poseToContinueFrom = poseToUse;
        } else if (controller != null && controller.isOnTarget()) {
            poseToContinueFrom = controller.getCurrentSetpoint();
        }
        
        return poseToContinueFrom;
    }

}
