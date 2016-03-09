package com.milkenknights.util.drive;

import edu.wpi.first.wpilibj.DriverStation;

public abstract class DriveHelper {

    protected DriveAbstract drive;

    public DriveHelper(final DriveAbstract drive) {
        this.drive = drive;
    }

    /**
     * Override this method to drive.
     * 
     * @param value1 The first drive parameter
     * @param value2 The second drive parameter
     */
    public final void drive(final double value1, final double value2) {
        if (DriverStation.getInstance().isAutonomous()) {
            return;
        }
        
        driveMode(value1, value2);
    }
    
    protected abstract void driveMode(double leftThrottle, double rightThrottle);

    public final double handleDeadband(final double val, final double deadband) {
        return (Math.abs(val) > Math.abs(deadband)) ? val : 0.0;
    }
}
