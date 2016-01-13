package com.milkenknights.util;

public class TankDriveHelper extends DriveHelper {

    public TankDriveHelper(Drive drive) {
		super(drive);
	}

	public static final double LEFT_THROTTLE_DEADBAND = 0.0;
    public static final double RIGHT_THROTTLE_DEADBAND = 0.0;
    
    private MotorPairSignal signal = new MotorPairSignal();
    
    public void drive(double leftThrottle, double rightThrottle) {
    	super.drive(leftThrottle, rightThrottle);
    	
        signal.leftMotor = handleDeadband(leftThrottle, LEFT_THROTTLE_DEADBAND);
        signal.rightMotor = handleDeadband(leftThrottle, RIGHT_THROTTLE_DEADBAND);

        drive.setOpenLoop(signal);
    }
    
}
