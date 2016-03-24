package com.milkenknights.frc2016.subsystems.controllers;

import com.milkenknights.frc2016.subsystems.Drive;
import com.milkenknights.util.Pose;
import com.milkenknights.util.drive.MotorPairSignal;

import edu.wpi.first.wpilibj.Timer;

public class TimedVelocityController implements Drive.DriveController {

    protected final double initialTime; // Time of control loop init
    protected final double decelTime; // Time to start deceling
    protected final double endDecelTime; // Time to end decel
    protected final double startPower;
    protected final double endPower;

    /**
     * Create a new TimedOpenLoopController.
     * 
     * @param startPower The inital power to start with
     * @param timeFullOn The time to leave the power at full
     * @param endPower The power to have at the end
     * @param timeToDecel The time to spend decelerating
     */
    public TimedVelocityController(final double startPower, final double timeFullOn, final double endPower,
            final double timeToDecel) {
        super();
        
        initialTime = Timer.getFPGATimestamp();
        decelTime = initialTime + timeFullOn;
        endDecelTime = decelTime + timeToDecel;
        this.startPower = startPower;
        this.endPower = endPower;
    }

    public boolean expired() {
        return Timer.getFPGATimestamp() > endDecelTime;
    }

    @Override
    public boolean isOnTarget() {
        return expired();
    }

    /**
     * Called every control loop.
     * 
     * @return The power to send to the motor
     */
    @Override
    public MotorPairSignal update(Pose pose) {
        double power;
        final double cur = Timer.getFPGATimestamp();
        if (cur <= decelTime) {
            power = startPower;
        } else if (cur > decelTime && cur <= endDecelTime) {
            // decel
            final double slope = (endPower - startPower) / (endDecelTime - decelTime);
            power = startPower + (slope * (cur - decelTime));
        } else {
            power = endPower;
        }
        
        return new MotorPairSignal(power, power);
    }

    @Override
    public Pose getCurrentSetpoint() {
        return null;
    }

    @Override
    public double getError() {
        return 0;
    }

}
