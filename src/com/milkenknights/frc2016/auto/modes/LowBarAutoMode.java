package com.milkenknights.frc2016.auto.modes;

import com.milkenknights.frc2016.HardwareAdapter;
import com.milkenknights.frc2016.auto.AutoMode;
import com.milkenknights.frc2016.auto.AutoModeEndedException;
import com.milkenknights.frc2016.subsystems.IntakeArm.IntakePosition;

/**
 * This AutoMode will breach the OuterWorks.
 */
public class LowBarAutoMode extends AutoMode {
    
    private final double turn = 45.0;

    @Override
    protected void routine() throws AutoModeEndedException {
        HardwareAdapter.DRIVE.setDistanceSetpoint(80, 30);
        HardwareAdapter.BALL_CLAMP.lock();
        HardwareAdapter.INTAKE_ARM.setPosition(IntakePosition.INTAKE);
        waitForIntake(1.5);
        
        HardwareAdapter.DRIVE.setDistanceSetpoint(200, 80);
        waitForDriveDistance(130, true, 4.0);
        
        HardwareAdapter.INTAKE_ARM.setPosition(IntakePosition.STORED);
        waitForDrive(3.0);
        waitForIntake(1.0);
        
        HardwareAdapter.DRIVE.setTurnSetpoint(turn);
        waitForDrive(1.5);
        
        HardwareAdapter.DRIVE.resetEncoders();
        HardwareAdapter.DRIVE.setDistanceSetpoint(12, 20);
        HardwareAdapter.INTAKE_ARM.setPosition(IntakePosition.PROTECT);
        waitForIntake(1.5);
        
        HardwareAdapter.BALL_CLAMP.open();
        HardwareAdapter.DRIVE.setTurnSetpoint(HardwareAdapter.GRIP.getAngleToTarget()
                + HardwareAdapter.DRIVE.getPhysicalPose().heading);
        waitForDrive(1.0);
        waitTime(0.25);
        
        HardwareAdapter.CATAPULT.fire();
        //wait for Catapult to fire
        waitTime(0.5);
        
        /** -------------------------- **/
        
        HardwareAdapter.DRIVE.setTurnSetpoint(turn);
        waitForDrive(1.0);
        
        HardwareAdapter.DRIVE.resetEncoders();
        HardwareAdapter.DRIVE.setDistanceSetpoint(-12, 20);
        waitForDrive(1.5);
        
        HardwareAdapter.DRIVE.setTurnSetpoint(0.0);
        HardwareAdapter.INTAKE_ARM.setPosition(IntakePosition.INTAKE);
        waitForDrive(1.5);
        waitForIntake(1.0);
        
        HardwareAdapter.DRIVE.resetEncoders();
        HardwareAdapter.DRIVE.setDistanceSetpoint(-200, 100);
    }

}
