package com.milkenknights.frc2016.behavior;

import com.milkenknights.frc2016.HardwareAdapter;
import com.milkenknights.util.drive.ArcadeDriveHelper;

public class BehaviorManager {
    
    private ArcadeDriveHelper driveHelper;
    
    public BehaviorManager() {
        driveHelper = new ArcadeDriveHelper(HardwareAdapter.DRIVE);
    }
    
    /**
     * Update the robot subsystems with the latest commands. 
     * 
     * @param commands The commands
     */
    public void update(Commands commands) {
        
        if (commands.alignRobot) {
            if (HardwareAdapter.GRIP.getAngleToTarget() != HardwareAdapter.DRIVE.getTurnSetpoint()) {
                HardwareAdapter.DRIVE.setTurnSetpoint(HardwareAdapter.GRIP.getAngleToTarget()
                        + HardwareAdapter.DRIVE.getPhysicalPose().heading);
            }
        } else {
            driveHelper.drive(commands.driveSpeed, commands.driveRotate);
        }
        
        if (commands.fireCatapult) {
            HardwareAdapter.CATAPULT.fire();
        }
        
        if (commands.intakePosition != null && commands.intakePosition != HardwareAdapter.INTAKE.getPosition()) {
            HardwareAdapter.INTAKE.setPosition(commands.intakePosition);
        }
        
        if (commands.intakeSpeed != null && commands.intakeSpeed != HardwareAdapter.INTAKE.getSpeed()) {
            HardwareAdapter.INTAKE.setSpeed(commands.intakeSpeed);
        }
        
    }
}
