package com.milkenknights.frc2016.subsystems;

import com.milkenknights.frc2016.Constants;
import com.milkenknights.frc2016.subsystems.controllers.DriveStraightController;
import com.milkenknights.frc2016.subsystems.controllers.TimedVelocityController;
import com.milkenknights.frc2016.subsystems.controllers.TurnInPlaceController;
import com.milkenknights.util.MkCanTalon;
import com.milkenknights.util.MkEncoder;
import com.milkenknights.util.MkGyro;
import com.milkenknights.util.Pose;
import com.milkenknights.util.SynchronousPid;
import com.milkenknights.util.drive.DriveAbstract;
import com.milkenknights.util.drive.MotorPairSignal;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class Drive extends DriveAbstract {
        
    private MkCanTalon leftMotor;
    private MkCanTalon rightMotor;
    private Encoder leftEncoder;
    private Encoder rightEncoder;
    private Solenoid shifter;
    private MkGyro gyro;
    
    private DriveController controller;
    private DriveGear driveGear = DriveGear.HIGH;
    private final SynchronousPid leftVelocityPid;
    private final SynchronousPid rightVelocityPid;
    
    public enum DriveGear {
        LOW(true), HIGH(false);
        
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
            final MkEncoder leftEncoder, final MkEncoder rightEncoder, final Solenoid shifter, final MkGyro gyro) {
        super(name);
        
        leftMotor.setInverted(true);
        
        leftEncoder.setDistancePerPulse(-Math.PI * Constants.Subsystems.Drive.WHEEL_DIAMETER
                * Constants.Subsystems.Drive.GEAR_RATIO / leftEncoder.getPulsesPerRevolution());
        rightEncoder.setDistancePerPulse(Math.PI * Constants.Subsystems.Drive.WHEEL_DIAMETER
                * Constants.Subsystems.Drive.GEAR_RATIO / rightEncoder.getPulsesPerRevolution());
        
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
        this.leftEncoder = leftEncoder;
        this.rightEncoder = rightEncoder;
        this.shifter = shifter;
        this.gyro = gyro;
        
        leftVelocityPid = new SynchronousPid();
        rightVelocityPid = new SynchronousPid();
        
        leftVelocityPid.enableMaxVelocityFeedForward(Constants.Subsystems.Drive.MAXIMUM_SPEED);
        rightVelocityPid.enableMaxVelocityFeedForward(Constants.Subsystems.Drive.MAXIMUM_SPEED);
        
        leftVelocityPid.sumOutput();
        rightVelocityPid.sumOutput();
        
        leftVelocityPid.setOutputRange(-Constants.Subsystems.Drive.VELOCITY_OUTPUT_RANGE,
                Constants.Subsystems.Drive.VELOCITY_OUTPUT_RANGE);
        rightVelocityPid.setOutputRange(-Constants.Subsystems.Drive.VELOCITY_OUTPUT_RANGE,
                Constants.Subsystems.Drive.VELOCITY_OUTPUT_RANGE);
        
        leftVelocityPid.setPid(Constants.Subsystems.Drive.VELOCITY_KP,
                Constants.Subsystems.Drive.VELOCITY_KI,
                Constants.Subsystems.Drive.VELOCITY_KD);
        rightVelocityPid.setPid(Constants.Subsystems.Drive.VELOCITY_KP,
                Constants.Subsystems.Drive.VELOCITY_KI,
                Constants.Subsystems.Drive.VELOCITY_KD);
    }

    @Override
    public void update() {        
        if (controller == null) {
            return;
        } else if (controller instanceof TurnInPlaceController) {
            setDriveSpeed(controller.update(getPhysicalPose()));
        } else {
            setDriveOutputs(controller.update(getPhysicalPose()));
        }
    }

    @Override
    public void updateSmartDashboard() {
        SmartDashboard.putNumber("Drive: Heading", getPhysicalPose().heading);
        SmartDashboard.putNumber("Drive: Left Distance", getPhysicalPose().leftDistance);
        SmartDashboard.putNumber("Drive: Right Distance", getPhysicalPose().rightDistance);
        SmartDashboard.putNumber("Drive: Left Speed", getPhysicalPose().leftVelocity);
        SmartDashboard.putNumber("Drive: Right Speed", getPhysicalPose().rightVelocity);
        SmartDashboard.putNumber("Drive: Heading Speed", getPhysicalPose().headingVelocity);
        
        if (controller != null) {
            SmartDashboard.putNumber("Drive: Error", controller.getError());
            SmartDashboard.putBoolean("Drive: Controller on target", controller.isOnTarget());
        }
    }

    @Override
    public void setOpenLoop(final MotorPairSignal signal) {
        controller = null;
        setDriveOutputs(signal);
    }

    @Override
    public void setDistanceSetpoint(final double distance) {
        setDistanceSetpoint(distance, Constants.Subsystems.Drive.MAXIMUM_SPEED);
    }

    @Override
    public void setDistanceSetpoint(final double distance, final double velocity) {
        controller = new DriveStraightController(getPoseToContinueFrom(false), distance, 
                Math.min(Constants.Subsystems.Drive.MAXIMUM_SPEED, Math.max(velocity, 0)));
    }

    @Override
    public void setTurnSetpoint(final double heading) {
        controller = new TurnInPlaceController(heading);
    }
    
    public void driveAtSpeed(final double speed, final double time) {
        controller = new TimedVelocityController(speed, time, 0, 0);
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
    
    public void resetGyro() {
        gyro.zeroYaw();
    }

    @Override
    public void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
    }

    @Override
    public Pose getPhysicalPose() {
        Pose pose = new Pose(leftEncoder.getDistance(), rightEncoder.getDistance(), leftEncoder.getRate(),
                rightEncoder.getRate(), gyro.getAngle(), gyro.getRate());
        
        if (pose.leftDistance + Constants.Subsystems.Drive.ENCODER_BROKEN_DISTANCE > pose.rightDistance) {
            pose.rightDistance = pose.leftDistance;
            pose.rightVelocity = pose.leftVelocity;
        } else if (pose.rightDistance + Constants.Subsystems.Drive.ENCODER_BROKEN_DISTANCE > pose.leftDistance) {
            pose.leftDistance = pose.rightDistance;
            pose.leftVelocity = pose.rightVelocity;
        }
        return pose;
    }
    
    private void setDriveSpeed(final MotorPairSignal signal) {
        leftVelocityPid.setSetpoint(signal.leftMotor);
        rightVelocityPid.setSetpoint(signal.rightMotor);
        
        setDriveOutputs(new MotorPairSignal(
                leftVelocityPid.calculate(getPhysicalPose().leftVelocity),
                rightVelocityPid.calculate(getPhysicalPose().rightVelocity)));
    }
    
    private void setDriveOutputs(final MotorPairSignal signal) {
        SmartDashboard.putString("Drive: Last Motor Outputs", signal.toString());
        leftMotor.set(signal.leftMotor);
        rightMotor.set(signal.rightMotor);
    }

    private Pose getPoseToContinueFrom(final boolean forTurnController) {
        Pose poseToContinueFrom = getPhysicalPose();
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
