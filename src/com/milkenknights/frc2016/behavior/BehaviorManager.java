package com.milkenknights.frc2016.behavior;

import com.milkenknights.frc2016.HardwareAdapter;
import com.milkenknights.frc2016.subsystems.BallClamp.BallClampState;
import com.milkenknights.frc2016.subsystems.Catapult;
import com.milkenknights.frc2016.subsystems.IntakeArm;
import com.milkenknights.util.drive.ArcadeDriveHelper;

public class BehaviorManager {
    
    private final ArcadeDriveHelper driveHelper;
    private boolean driveReversed;
    
    public BehaviorManager() {
        driveHelper = new ArcadeDriveHelper(HardwareAdapter.DRIVE);
    }
    
    /**
     * Update the drive subsystem with the latest commands.
     * 
     * @param commands The commands
     */
    private void drive(final Commands commands) {
        if (commands.reverseDrive) {
            driveReversed = ! driveReversed;
        }

        if (commands.alignRobot == Commands.AlignRobot.START) {
            HardwareAdapter.DRIVE.setTurnSetpoint(HardwareAdapter.GRIP.getAngleToTarget()
                        + HardwareAdapter.DRIVE.getPhysicalPose().heading);
            System.out.println("Aligning Robot to:" + (HardwareAdapter.GRIP.getAngleToTarget()
                        + HardwareAdapter.DRIVE.getPhysicalPose().heading));
        } else if (driveReversed && commands.alignRobot != Commands.AlignRobot.CONTINUE) {
            driveHelper.commandDrive(-commands.driveSpeed, commands.driveRotate);
        } else if (commands.alignRobot != Commands.AlignRobot.CONTINUE) {
            driveHelper.commandDrive(commands.driveSpeed, commands.driveRotate);
        }
        
        if (commands.driveGear != null && commands.driveGear != HardwareAdapter.DRIVE.getGear()) {
            HardwareAdapter.DRIVE.setGear(commands.driveGear);
        }
    }
    
    /**
     * Update the intake subsystem with the latest commands.
     * 
     * @param commands The commands
     */
    private void intake(final Commands commands) {
        if (commands.zeroIntakeArm && HardwareAdapter.CATAPULT.getState() == Catapult.CatapultState.READY) {
            HardwareAdapter.INTAKE_ARM.zero();
        }
        
        if (commands.intakePosition != null && commands.intakePosition != HardwareAdapter.INTAKE_ARM.getPosition()) {
            if (commands.intakePosition != IntakeArm.IntakePosition.STORED) {
                HardwareAdapter.INTAKE_ARM.setPosition(commands.intakePosition);
            } else if (commands.intakePosition == IntakeArm.IntakePosition.STORED
                    && HardwareAdapter.CATAPULT.getState() == Catapult.CatapultState.READY) {
                HardwareAdapter.INTAKE_ARM.setPosition(commands.intakePosition);
            }
        }
        
        if (commands.intakeSpeed != null && commands.intakeSpeed != HardwareAdapter.INTAKE_SPEED.getSpeed()) {
            HardwareAdapter.INTAKE_SPEED.setSpeed(commands.intakeSpeed);
        }
    }
    
    /**
     * Update the catapult subsystem with the latest commands.
     * 
     * @param commands The commands
     */
    private void catapult(final Commands commands) {
        if (commands.fireCatapult && HardwareAdapter.CATAPULT.getState() != Catapult.CatapultState.ZERO) {
            if (HardwareAdapter.INTAKE_ARM.getPosition() == IntakeArm.IntakePosition.STORED) {
                HardwareAdapter.INTAKE_ARM.setPosition(IntakeArm.IntakePosition.PROTECT);
            } else if (HardwareAdapter.INTAKE_ARM.isOnTarget() && HardwareAdapter.BALL_CLAMP.getState() == BallClampState.OPEN) {
                HardwareAdapter.CATAPULT.fire();
            }
        }
    }
    
    /**
     * Update the catapult subsystem with the latest commands.
     * 
     * @param commands The commands
     */
    private void ballClamp(final Commands commands) {
    	if (commands.toggleBallClamp) {
    		HardwareAdapter.BALL_CLAMP.toggle();
    	}
    }
    
    /**
     * Update the robot subsystems with the latest commands. 
     * 
     * @param commands The commands
     */
    public void update(final Commands commands) {
        drive(commands);
        intake(commands);
        ballClamp(commands);
        catapult(commands);
    }
}
