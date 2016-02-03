package com.milkenknights.frc2016.behavior;

import com.milkenknights.frc2016.HardwareAdapter;

public class BehaviorManager {
    
    public BehaviorManager() {

    }
    
    /**
     * Update the robot subsystems with the latest commands. 
     * 
     * @param commands The commands
     */
    public void update(Commands commands) {
        
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
