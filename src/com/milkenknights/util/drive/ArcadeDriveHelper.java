package com.milkenknights.util.drive;

public class ArcadeDriveHelper extends DriveHelper {

    public ArcadeDriveHelper(final DriveAbstract drive) {
        super(drive);
    }
    
    /**
     * Arcade drive control.
     */
    @Override
    public void driveMode(final double speed, final double rotate) {
        drive.setOpenLoop(new MotorPairSignal(speed - rotate, speed + rotate));
    }
    
}
