package com.milkenknights.frc2016.auto.modes;

import com.milkenknights.frc2016.HardwareAdapter;
import com.milkenknights.frc2016.auto.AutoMode;
import com.milkenknights.frc2016.auto.AutoModeEndedException;
import com.milkenknights.frc2016.subsystems.IntakeArm.IntakePosition;

/**
 * This AutoMode will breach the OuterWorks.
 */
public class LowBarAutoMode extends AutoMode {

    @Override
    protected void routine() throws AutoModeEndedException {
        waitTime(0.25);
        
        HardwareAdapter.INTAKE_ARM.setPosition(IntakePosition.INTAKE);
        waitForIntake(0.5);
        
        HardwareAdapter.DRIVE.setDistanceSetpoint(225, 75);
        waitForDrive(5.0);
        
        HardwareAdapter.DRIVE.setTurnSetpoint(45.0);
        waitForDrive(2.0);
        
        HardwareAdapter.DRIVE.setTurnSetpoint(HardwareAdapter.GRIP.getAngleToTarget()
                + HardwareAdapter.DRIVE.getPhysicalPose().heading);
        waitForDrive(5.0);
        
        HardwareAdapter.CATAPULT.fire();
    }

}
