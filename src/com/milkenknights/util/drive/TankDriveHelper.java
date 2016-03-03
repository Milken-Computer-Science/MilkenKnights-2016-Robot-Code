package com.milkenknights.util.drive;

public class TankDriveHelper extends DriveHelper {

    public TankDriveHelper(DriveAbstract drive) {
        super(drive);
    }

    public static final double LEFT_THROTTLE_DEADBAND = 0.0;
    public static final double RIGHT_THROTTLE_DEADBAND = 0.0;
    
    private MotorPairSignal signal = new MotorPairSignal();
    
    /**
     * Left and Right throttle control.
     */
    public void drive(double leftThrottle, double rightThrottle) {
        super.drive(leftThrottle, rightThrottle);

        signal.leftMotor = handleDeadband(leftThrottle, LEFT_THROTTLE_DEADBAND);
        signal.rightMotor = handleDeadband(rightThrottle, RIGHT_THROTTLE_DEADBAND);

        drive.setOpenLoop(signal);
    }
    
}
