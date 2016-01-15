package com.milkenknights.frc2016.subsystems;

import com.milkenknights.frc2016.Constants;
import com.milkenknights.frc2016.subsystems.controllers.DriveStraightController;
import com.milkenknights.frc2016.subsystems.controllers.TurnInPlaceController;
import com.milkenknights.util.DriveAbstract;
import com.milkenknights.util.MkCanTalon;
import com.milkenknights.util.MkEncoder;
import com.milkenknights.util.MotorPairSignal;
import com.milkenknights.util.Pose;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;

public class Drive extends DriveAbstract {
        
    private MkCanTalon leftMotor;
    private MkCanTalon rightMotor;
    private Encoder leftEncoder;
    private Encoder rightEncoder;
    private AHRS gyro;
    
    private DriveController controller = null;
    private Pose cachedPose = new Pose(0, 0, 0, 0, 0, 0);

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
    public Drive(String name, MkCanTalon leftMotor, MkCanTalon rightMotor, MkEncoder leftEncoder, 
            MkEncoder rightEncoder, AHRS gyro, Solenoid shifter) {
        super(name);
        
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
        this.leftEncoder = leftEncoder;
        this.rightEncoder = rightEncoder;
        this.gyro = gyro;
        
        this.rightMotor.setInverted(true);
        this.leftEncoder.setDistancePerPulse(leftEncoder.getPulsesPerRevolution() * Math.PI 
                / Constants.Drive.WHEEL_DIAMETER);
        this.rightEncoder.setDistancePerPulse(rightEncoder.getPulsesPerRevolution() * Math.PI 
                / Constants.Drive.WHEEL_DIAMETER);
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

    }

    @Override
    public void setOpenLoop(MotorPairSignal signal) {
        controller = null;
        setDriveOutputs(signal);
    }

    @Override
    public void setDistanceSetpoint(double distance) {
        setDistanceSetpoint(distance, Constants.Drive.MAX_SPEED);

    }

    @Override
    public void setDistanceSetpoint(double distance, double velocity) {
        velocity = Math.min(Constants.Drive.MAX_SPEED, Math.max(velocity, 0));
        controller = new DriveStraightController(getPoseToContinueFrom(false), distance, velocity);
    }

    @Override
    public void setTurnSetpoint(double heading) {
        setTurnSetpoint(heading, Constants.kTurnMaxSpeedRadsPerSec);
    }

    @Override
    public void setTurnSetpoint(double heading, double velocity) {
        velocity = Math.min(Constants.kTurnMaxSpeedRadsPerSec, Math.max(velocity, 0));
        controller = new TurnInPlaceController(getPoseToContinueFrom(true), heading, velocity);
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
    
    private void setDriveOutputs(MotorPairSignal signal) {
        leftMotor.set(signal.leftMotor);
        rightMotor.set(signal.rightMotor);
    }
    
    private Pose getPoseToContinueFrom(boolean for_turn_controller) {
        if (!for_turn_controller && controller instanceof TurnInPlaceController) {
            Pose pose_to_use = getPhysicalPose();
            pose_to_use.heading = ((TurnInPlaceController) controller).getHeadingGoal();
            pose_to_use.headingVelocity = 0;
            return pose_to_use;
        } else if (controller == null || (controller instanceof DriveStraightController && for_turn_controller)) {
            return getPhysicalPose();
        } else if (controller.isOnTarget()) {
            return controller.getCurrentSetpoint();
        } else {
            return getPhysicalPose();
        }
    }

}
