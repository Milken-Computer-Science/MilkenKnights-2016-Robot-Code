package com.milkenknights.frc2016;

import com.milkenknights.frc2016.behavior.Commands;
import com.milkenknights.frc2016.behavior.Commands.ArmPosition;
import com.milkenknights.frc2016.subsystems.Drive.DriveGear;
import com.milkenknights.frc2016.subsystems.IntakeSpeed.IntakeSpeedState;

public final class OperatorInterface {

    private Commands commands = new Commands();
    
    public void reset() {
        commands = new Commands();
    }
    
    /**
     * Return the commands that the operator wants to execute.
     */
    public Commands getCommands() {
        reset();
        getDriverCommands();
        getOperatorCommands();
        return commands;
    }
    
    private void getDriverCommands() {
        commands.driveSpeed = -HardwareAdapter.DRIVE_STICK.getY();
        commands.driveRotate = -HardwareAdapter.DRIVE_STICK.getTwist() * Constants.DriverStation.TWIST_MULTIPLIER;
        
        if (HardwareAdapter.DRIVE_STICK.getButton(1).isPressed()) {
            commands.alignRobot = Commands.AlignRobot.START;
        } else if (HardwareAdapter.DRIVE_STICK.getButton(1).isHeld()) {
            commands.alignRobot = Commands.AlignRobot.CONTINUE;
        } else {
            commands.alignRobot = Commands.AlignRobot.STOP;
        }
        
        if (HardwareAdapter.DRIVE_STICK.getButton(2).isPressed()) {
            commands.driveGear = HardwareAdapter.DRIVE.getGear() == DriveGear.HIGH ? DriveGear.LOW : DriveGear.HIGH;
        }
        
    }
    
    private void getOperatorCommands() {
        if (HardwareAdapter.OPERATOR_STICK.getButton(3).isPressed()) {
            commands.armPosition = ArmPosition.CDF_PROTECT;
        } else if (HardwareAdapter.OPERATOR_STICK.getButton(4).isPressed()) {
            commands.armPosition = ArmPosition.INTAKE;
        } else if (HardwareAdapter.OPERATOR_STICK.getButton(2).isPressed()) {
            commands.armPosition = ArmPosition.LOWBAR_PORTCULLIS;
        } else if (HardwareAdapter.OPERATOR_STICK.getButton(6).isPressed()) {
            commands.armPosition = ArmPosition.STORED;
        }
        
        if (HardwareAdapter.OPERATOR_STICK.getButton(4).isHeld()) {
            commands.intakeSpeed = IntakeSpeedState.INTAKE;
        } else if (HardwareAdapter.OPERATOR_STICK.getButton(5).isHeld()) {
            commands.intakeSpeed = IntakeSpeedState.OUTPUT;
        } else {
            commands.intakeSpeed = IntakeSpeedState.NEUTRAL;
        }
        
        if (HardwareAdapter.OPERATOR_STICK.getButton(1).isPressed()
                || HardwareAdapter.OPERATOR_STICK.getButton(7).isPressed()) {
            commands.fireCatapult = true;
        }
        
        if (HardwareAdapter.OPERATOR_STICK.getButton(8).isPressed()) {
            commands.toggleBallClamp = true; 
        }
        
        if (HardwareAdapter.OPERATOR_STICK.getButton(10).isPressed()) {
            commands.zeroCatapult = true;
        }
        
        if (HardwareAdapter.OPERATOR_STICK.getButton(11).isPressed()) {
            commands.zeroArms = true;
        }
    }
}
