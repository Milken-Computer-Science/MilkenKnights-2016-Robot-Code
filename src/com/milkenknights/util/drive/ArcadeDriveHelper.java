package com.milkenknights.util.drive;

public class ArcadeDriveHelper extends DriveHelper {
    
    private final MotorPairSignal signal = new MotorPairSignal();

    public ArcadeDriveHelper(final DriveAbstract drive) {
        super(drive);
    }
    
    /**
     * Arcade drive control.
     */
    @Override
    public void driveMode(final double speed, final double rotate) {
        signal.leftMotor = speed - rotate;
        signal.rightMotor = speed + rotate;

        drive.setOpenLoop(signal);
    }
    
}
