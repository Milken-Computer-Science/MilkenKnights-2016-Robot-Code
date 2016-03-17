package com.milkenknights.frc2016.behavior;

import com.milkenknights.frc2016.HardwareAdapter;
import com.milkenknights.util.drive.ArcadeDriveHelper;

public class BehaviorManager {
    
    private final ArcadeDriveHelper driveHelper;
    private boolean driveReversed;
    
    public BehaviorManager() {
        driveHelper = new ArcadeDriveHelper(HardwareAdapter.DRIVE);
    }
    
    /**
     * Update the drive subsystem with the latest commands.
     * 
     * @param commands The commands
     */
    private void drive(final Commands commands) {
        if (commands.reverseDrive) {
            driveReversed = ! driveReversed;
        }

        if (commands.alignRobot) {
            if (HardwareAdapter.GRIP.getAngleToTarget() != HardwareAdapter.DRIVE.getTurnSetpoint()) {
                HardwareAdapter.DRIVE.setTurnSetpoint(HardwareAdapter.GRIP.getAngleToTarget()
                        + HardwareAdapter.DRIVE.getPhysicalPose().heading);
            }
        } else if (driveReversed) {
            driveHelper.commandDrive(-commands.driveSpeed, commands.driveRotate);
        } else {
            driveHelper.commandDrive(commands.driveSpeed, commands.driveRotate);
        }
        
        if (commands.driveGear != null && commands.driveGear != HardwareAdapter.DRIVE.getGear()) {
            HardwareAdapter.DRIVE.setGear(commands.driveGear);
        }
    }
    
    /**
     * Update the intake subsystem with the latest commands.
     * 
     * @param commands The commands
     */
    private void intake(final Commands commands) {
        if (commands.intakePosition != null && commands.intakePosition != HardwareAdapter.INTAKE_ARM.getPosition()) {
            HardwareAdapter.INTAKE_ARM.setPosition(commands.intakePosition);
        }
        
        if (commands.intakeSpeed != null && commands.intakeSpeed != HardwareAdapter.INTAKE_SPEED.getSpeed()) {
            HardwareAdapter.INTAKE_SPEED.setSpeed(commands.intakeSpeed);
        }
    }
    
    /**
     * Update the catapult subsystem with the latest commands.
     * 
     * @param commands The commands
     */
    private void catapult(final Commands commands) {
        if (commands.fireCatapult && HardwareAdapter.CATAPULT.isZeroed()) {
            HardwareAdapter.CATAPULT.fire();
            HardwareAdapter.BALL_CLAMP.open();
        }
    }
    
    /**
     * Update the robot subsystems with the latest commands. 
     * 
     * @param commands The commands
     */
    public void update(final Commands commands) {
        drive(commands);
        intake(commands);
        catapult(commands);
    }
}
