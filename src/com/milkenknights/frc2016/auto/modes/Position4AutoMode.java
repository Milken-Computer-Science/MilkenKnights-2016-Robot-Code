package com.milkenknights.frc2016.auto.modes;

import com.milkenknights.frc2016.HardwareAdapter;
import com.milkenknights.frc2016.auto.AutoMode;
import com.milkenknights.frc2016.auto.AutoModeEndedException;
import com.milkenknights.frc2016.subsystems.ActionArm.ActionArmPosition;
import com.milkenknights.frc2016.subsystems.IntakeArm.IntakePosition;

/**
 * This AutoMode will breach the OuterWorks.
 */
public class Position4AutoMode extends AutoMode {

    @Override
    protected void routine() throws AutoModeEndedException {
        HardwareAdapter.BALL_CLAMP.lock();
        HardwareAdapter.INTAKE_ARM.setPosition(IntakePosition.STORED);
        HardwareAdapter.ACTION_ARM.setPosition(ActionArmPosition.STORED);
        waitTime(0.5);
        waitForIntake(1.0);
        
        HardwareAdapter.DRIVE.setDistanceSetpoint(180, 80);
        waitForDrive(3.0);
        
        HardwareAdapter.BALL_CLAMP.open();
        HardwareAdapter.INTAKE_ARM.setPosition(IntakePosition.PROTECT);
        HardwareAdapter.ACTION_ARM.setPosition(ActionArmPosition.CDF);
        HardwareAdapter.DRIVE.setTurnSetpoint(0.0);
        waitForDrive(1.0);
        waitForActionArm(3.0);
        
        HardwareAdapter.DRIVE.setTurnSetpoint(HardwareAdapter.GRIP.getAngleToTarget()
                + HardwareAdapter.DRIVE.getPhysicalPose().heading);
        waitForDrive(1.0);
        waitForIntake(1.0);
        waitTime(0.25);
        
        HardwareAdapter.CATAPULT.fire();
        waitTime(0.5);
    }

}
