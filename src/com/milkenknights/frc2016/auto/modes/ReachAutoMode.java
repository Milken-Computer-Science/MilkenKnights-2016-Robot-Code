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
        HardwareAdapter.DRIVE.setDistanceSetpoint(75, 25);
        waitForDrive(10.0);
    }

}
