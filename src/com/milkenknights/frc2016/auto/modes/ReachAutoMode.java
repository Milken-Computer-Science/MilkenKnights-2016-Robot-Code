package com.milkenknights.frc2016.auto.modes;

import com.milkenknights.frc2016.HardwareAdapter;
import com.milkenknights.frc2016.auto.AutoMode;
import com.milkenknights.frc2016.auto.AutoModeEndedException;

/**
 * This AutoMode will reach the OuterWorks.
 */
public class ReachAutoMode extends AutoMode {

    @Override
    protected void routine() throws AutoModeEndedException {
        waitTime(0.25);
        
        //HardwareAdapter.DRIVE.driveAtSpeed(50, 3);
        //HardwareAdapter.DRIVE.setDistanceSetpoint(5 * 12);
        HardwareAdapter.DRIVE.setTurnSetpoint(180);
        waitForDrive(15.0);
    }

}
