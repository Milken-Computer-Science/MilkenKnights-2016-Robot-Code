package com.milkenknights.frc2016.behavior;

import com.milkenknights.frc2016.HardwareAdapter;
import com.milkenknights.util.TankDriveHelper;

public class BehaviorManager {
    
    private TankDriveHelper tankDriveHelper;
    
    public BehaviorManager() {
        tankDriveHelper = new TankDriveHelper(HardwareAdapter.DRIVE);
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
            tankDriveHelper.drive(HardwareAdapter.LEFT_STICK.getY(), HardwareAdapter.RIGHT_STICK.getY());
        }
        
        if (commands.fireCatapult) {
            HardwareAdapter.CATAPULT.fire();
        } else {
            HardwareAdapter.CATAPULT.stop();
        }
        
        if (commands.intakePosition != null && commands.intakePosition != HardwareAdapter.INTAKE.getPosition()) {
            HardwareAdapter.INTAKE.setPosition(commands.intakePosition);
        }
        
        if (commands.intakeSpeed != null && commands.intakeSpeed != HardwareAdapter.INTAKE.getSpeed()) {
            HardwareAdapter.INTAKE.setSpeed(commands.intakeSpeed);
        }
        
    }
}
