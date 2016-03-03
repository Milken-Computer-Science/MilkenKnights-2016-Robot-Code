package com.milkenknights.frc2016.auto.modes;

import com.milkenknights.frc2016.HardwareAdapter;
import com.milkenknights.frc2016.auto.AutoMode;
import com.milkenknights.frc2016.auto.AutoModeEndedException;
import com.milkenknights.frc2016.subsystems.Intake.IntakePosition;

/**
 * This AutoMode will breach the OuterWorks, aim, and fire.
 * 
 * TODO: Test this AutoMode
 */
public class BreachAutoMode extends AutoMode {

    @Override
    protected void routine() throws AutoModeEndedException {
        waitTime(0.25);
        
        HardwareAdapter.INTAKE.setPosition(IntakePosition.INTAKE);
        waitForIntake(0.5);
        
        HardwareAdapter.DRIVE.setDistanceSetpoint(42);
        waitForDrive(2.0);
    }

}
