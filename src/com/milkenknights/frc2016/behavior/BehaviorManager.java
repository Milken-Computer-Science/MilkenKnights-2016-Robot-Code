package com.milkenknights.frc2016.behavior;

import com.milkenknights.frc2016.HardwareAdapter;
import com.milkenknights.frc2016.subsystems.ActionArm.ActionArmPosition;
import com.milkenknights.frc2016.subsystems.BallClamp.BallClampState;
import com.milkenknights.frc2016.subsystems.Catapult.CatapultState;
import com.milkenknights.frc2016.subsystems.IntakeArm.IntakePosition;
import com.milkenknights.frc2016.subsystems.IntakeArm;
import com.milkenknights.util.drive.ArcadeDriveHelper;

public class BehaviorManager {
    
    private final ArcadeDriveHelper driveHelper;
    
    public BehaviorManager() {
        driveHelper = new ArcadeDriveHelper(HardwareAdapter.DRIVE);
    }
    
    /**
     * Update the drive subsystem with the latest commands.
     * 
     * @param commands The commands
     */
    private void drive(final Commands commands) {
        if (commands.alignRobot == Commands.AlignRobot.START) {
            HardwareAdapter.DRIVE.setTurnSetpoint(HardwareAdapter.GRIP.getAngleToTarget()
                        + HardwareAdapter.DRIVE.getPhysicalPose().heading);
            System.out.println("Aligning Robot to:" + (HardwareAdapter.GRIP.getAngleToTarget()
                        + HardwareAdapter.DRIVE.getPhysicalPose().heading));
        } else if (commands.alignRobot != Commands.AlignRobot.CONTINUE) {
            driveHelper.commandDrive(commands.driveSpeed, commands.driveRotate);
        }
        
        if (commands.driveGear != null) {
            HardwareAdapter.DRIVE.setGear(commands.driveGear);
        }
    }
    
    /**
     * Update the Intake subsystem with the latest commands.
     * 
     * @param commands The commands
     */
    private void intake(final Commands commands) {
        if (commands.zeroArms && HardwareAdapter.CATAPULT.getState() == CatapultState.READY) {
            HardwareAdapter.INTAKE_ARM.zero();
        }
        
        if (commands.armPosition != null) {
            switch (commands.armPosition) {
                case CDF_PROTECT:
                    HardwareAdapter.INTAKE_ARM.setPosition(IntakePosition.PROTECT);
                    break;
                case INTAKE:
                    HardwareAdapter.INTAKE_ARM.setPosition(IntakePosition.INTAKE);
                    break;
                case LOWBAR_PORTCULLIS:
                    HardwareAdapter.INTAKE_ARM.setPosition(IntakePosition.INTAKE);
                    break;
                case STORED:
                    if (HardwareAdapter.CATAPULT.getState() == CatapultState.READY) {
                        HardwareAdapter.INTAKE_ARM.setPosition(IntakePosition.STORED);
                    }
                    break;
                default:
                    break;
            }
        }
        
        if (commands.intakeSpeed != null && commands.intakeSpeed != HardwareAdapter.INTAKE_SPEED.getSpeed()) {
            HardwareAdapter.INTAKE_SPEED.setSpeed(commands.intakeSpeed);
        }
    }
    
    /**
     * Update the ActionArm subsystem with the latest commands.
     * 
     * @param commands The commands
     */
    private void actionArm(final Commands commands) {
        if (commands.zeroArms) {
            HardwareAdapter.ACTION_ARM.zero();
        }
        
        if (commands.armPosition != null) {
            switch (commands.armPosition) {
                case CDF_PROTECT:
                    HardwareAdapter.ACTION_ARM.setPosition(ActionArmPosition.CDF);
                    break;
                case INTAKE:
                    HardwareAdapter.ACTION_ARM.setPosition(ActionArmPosition.STORED);
                    break;
                case LOWBAR_PORTCULLIS:
                    HardwareAdapter.ACTION_ARM.setPosition(ActionArmPosition.PORTICULLIS);
                    break;
                case STORED:
                    HardwareAdapter.ACTION_ARM.setPosition(ActionArmPosition.STORED);
                    break;
                default:
                    break;
            }
        }
    }
    
    /**
     * Update the catapult subsystem with the latest commands.
     * 
     * @param commands The commands
     */
    private void catapult(final Commands commands) {
        if (commands.zeroCatapult && HardwareAdapter.INTAKE_ARM.getPosition() != IntakeArm.IntakePosition.STORED) {
            HardwareAdapter.CATAPULT.zero();
        }

        if (commands.fireCatapult && HardwareAdapter.CATAPULT.getState() != CatapultState.ZERO) {
            if (HardwareAdapter.INTAKE_ARM.getPosition() == IntakeArm.IntakePosition.STORED) {
                HardwareAdapter.INTAKE_ARM.setPosition(IntakeArm.IntakePosition.PROTECT);
            } else if (HardwareAdapter.INTAKE_ARM.isOnTarget()
                    && HardwareAdapter.BALL_CLAMP.getState() == BallClampState.OPEN) {
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
        actionArm(commands);
        ballClamp(commands);
        catapult(commands);
    }
}
