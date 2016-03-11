package com.milkenknights.frc2016.auto.modes;

import com.milkenknights.frc2016.HardwareAdapter;
import com.milkenknights.frc2016.auto.AutoMode;
import com.milkenknights.frc2016.auto.AutoModeEndedException;
import com.milkenknights.frc2016.subsystems.Intake.IntakePosition;

/**
 * This AutoMode will reach the OuterWorks and stop.
 * 
 * TODO: Test this AutoMode
 */
public class ReachAutoMode extends AutoMode {

    @Override
    protected void routine() throws AutoModeEndedException {
        //waitTime(0.25);
        
//        HardwareAdapter.INTAKE.setPosition(IntakePosition.INTAKE);
//        waitForIntake(1.0);
        HardwareAdapter.DRIVE.setDistanceSetpoint(120);
        waitForDrive(10.0);
    }

}
