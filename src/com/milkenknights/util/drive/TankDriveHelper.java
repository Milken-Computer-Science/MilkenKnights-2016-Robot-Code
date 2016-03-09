package com.milkenknights.util.drive;

public class TankDriveHelper extends DriveHelper {
    
    private final MotorPairSignal signal = new MotorPairSignal();

    public TankDriveHelper(final DriveAbstract drive) {
        super(drive);
    }
    
    /**
     * Left and Right throttle control.
     */
    @Override
    protected void driveMode(final double leftThrottle, final double rightThrottle) {
        signal.leftMotor = leftThrottle;
        signal.rightMotor = rightThrottle;

        drive.setOpenLoop(signal);
    }
    
}
