package com.milkenknights.util.drive;

public class TankDriveHelper extends DriveHelper {

    public TankDriveHelper(DriveAbstract drive) {
        super(drive);
    }
    
    private MotorPairSignal signal = new MotorPairSignal();
    
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
