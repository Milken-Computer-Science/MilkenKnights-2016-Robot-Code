package com.milkenknights.frc2016.auto.modes;

import com.milkenknights.frc2016.HardwareAdapter;
import com.milkenknights.frc2016.auto.AutoMode;
import com.milkenknights.frc2016.auto.AutoModeEndedException;
import com.milkenknights.frc2016.subsystems.IntakeArm.IntakePosition;

/**
 * This AutoMode will breach the OuterWorks.
 */
public class Position3AutoMode extends AutoMode {

    @Override
    protected void routine() throws AutoModeEndedException {
        waitTime(0.25);
        
        HardwareAdapter.BALL_CLAMP.lock();
        HardwareAdapter.INTAKE_ARM.setPosition(IntakePosition.STORED);
        waitForIntake(1.0);
        
        HardwareAdapter.DRIVE.setDistanceSetpoint(180, 140);
        waitForDrive(4.0);
        
        HardwareAdapter.DRIVE.setTurnSetpoint(0.0);
        waitForDrive(1.5);
        
        HardwareAdapter.BALL_CLAMP.open();
        HardwareAdapter.INTAKE_ARM.setPosition(IntakePosition.PROTECT);
        waitForIntake(1.5);
        
        HardwareAdapter.DRIVE.setTurnSetpoint(HardwareAdapter.GRIP.getAngleToTarget()
                + HardwareAdapter.DRIVE.getPhysicalPose().heading);
        waitForDrive(4.0);
        
        HardwareAdapter.CATAPULT.fire();
    }

}
