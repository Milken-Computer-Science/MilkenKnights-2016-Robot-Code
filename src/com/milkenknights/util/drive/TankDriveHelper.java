package com.milkenknights.util.drive;

public class TankDriveHelper extends DriveHelper {

    public TankDriveHelper(final DriveAbstract drive) {
        super(drive);
    }
    
    /**
     * Left and Right throttle control.
     */
    @Override
    protected void driveMode(final double leftThrottle, final double rightThrottle) {
        drive.setOpenLoop(new MotorPairSignal(leftThrottle, rightThrottle));
    }
    
}
