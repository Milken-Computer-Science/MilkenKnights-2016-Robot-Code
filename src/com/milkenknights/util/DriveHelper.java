package com.milkenknights.util;

import edu.wpi.first.wpilibj.DriverStation;

public abstract class DriveHelper {

    protected Drive drive;

    public DriveHelper(Drive drive) {
        this.drive = drive;
    }

    /**
     * Override this method to drive.
     * 
     * @param leftThrottle The left throttle power
     * @param rightThrottle The right throttle power
     */
    public void drive(double leftThrottle, double rightThrottle) {
        if (DriverStation.getInstance().isAutonomous()) {
            return;
        }
    }

    public double handleDeadband(double val, double deadband) {
        return (Math.abs(val) > Math.abs(deadband)) ? val : 0.0;
    }
}
