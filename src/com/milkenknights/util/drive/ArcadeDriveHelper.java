package com.milkenknights.util.drive;

public class ArcadeDriveHelper extends DriveHelper {

    public ArcadeDriveHelper(DriveAbstract drive) {
        super(drive);
    }
    
    private MotorPairSignal signal = new MotorPairSignal();
    
    /**
     * Arcade drive control.
     */
    @Override
    public void driveMode(double speed, double rotate) {
        signal.leftMotor = speed + rotate;
        signal.rightMotor = speed - rotate;

        drive.setOpenLoop(signal);
    }
    
}
