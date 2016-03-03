package com.milkenknights.util.drive;

public class TankDriveHelper extends DriveHelper {
    
    private MotorPairSignal signal = new MotorPairSignal();

    public TankDriveHelper(DriveAbstract drive) {
        super(drive);
    }
    
    /**
     * Left and Right throttle control.
     */
    @Override
    protected void driveMode(double leftThrottle, double rightThrottle) {
        signal.leftMotor = leftThrottle;
        signal.rightMotor = rightThrottle;

        drive.setOpenLoop(signal);
    }
    
}
