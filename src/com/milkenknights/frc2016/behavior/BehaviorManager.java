package com.milkenknights.frc2016.behavior;

import com.milkenknights.frc2016.HardwareAdapter;
import com.milkenknights.util.drive.ArcadeDriveHelper;

public class BehaviorManager {
    
    private final ArcadeDriveHelper driveHelper;
    private boolean driveReversed = false;
    
    public BehaviorManager() {
        driveHelper = new ArcadeDriveHelper(HardwareAdapter.DRIVE);
    }
    
    /**
     * Update the robot subsystems with the latest commands. 
     * 
     * @param commands The commands
     */
    public void update(final Commands commands) {
    	
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
