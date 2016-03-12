package com.milkenknights.frc2016.auto.modes;

import com.milkenknights.frc2016.HardwareAdapter;
import com.milkenknights.frc2016.auto.AutoMode;
import com.milkenknights.frc2016.auto.AutoModeEndedException;

/**
 * This AutoMode will reach the OuterWorks and stop.
 * 
 * TODO: Test this AutoMode
 */
public class ReachAutoMode extends AutoMode {

    @Override
    protected void routine() throws AutoModeEndedException {
        waitTime(0.25);
        
        HardwareAdapter.DRIVE.setTurnSetpoint(45);

        //HardwareAdapter.INTAKE.setPosition(IntakePosition.INTAKE);
        //waitTime(5.0);
        //HardwareAdapter.DRIVE.setDistanceSetpoint(-(25 * 12));
    }

}
