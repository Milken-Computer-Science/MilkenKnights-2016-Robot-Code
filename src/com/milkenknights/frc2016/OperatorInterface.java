package com.milkenknights.frc2016;

import com.milkenknights.frc2016.behavior.Commands;
import com.milkenknights.frc2016.subsystems.Drive.DriveGear;
import com.milkenknights.frc2016.subsystems.Intake.IntakePosition;
import com.milkenknights.frc2016.subsystems.Intake.IntakeSpeed;

public class OperatorInterface {

    private Commands commands = new Commands();
    
    public void reset() {
        commands = new Commands();
    }
    
    /**
     * Return the commands that the operator wants to execute.
     */
    public Commands getCommands() {
        reset();
        
        commands.driveSpeed = -HardwareAdapter.DRIVE_STICK.getY();
        commands.driveRotate = -HardwareAdapter.DRIVE_STICK.getTwist() * 1.5;
        
        if (HardwareAdapter.DRIVE_STICK.getButton(1).isHeld() && HardwareAdapter.DRIVE_STICK.getButton(2).isHeld()) {
            commands.fireCatapult = true;
        } else {
            commands.fireCatapult = false;
        }
        
        if (HardwareAdapter.DRIVE_STICK.getButton(7).isPressed()) {
            commands.driveGear = HardwareAdapter.DRIVE.getGear() == DriveGear.HIGH ? DriveGear.LOW : DriveGear.HIGH;
        }
        
        if (HardwareAdapter.DRIVE_STICK.getButton(8).isPressed()) {
            commands.reverseDrive = true;
        }
        
        if (HardwareAdapter.OPERATOR_STICK.getButton(3).isPressed()) {
            commands.intakePosition = IntakePosition.PROTECT;
        } else if (HardwareAdapter.OPERATOR_STICK.getButton(2).isPressed()) {
            commands.intakePosition = IntakePosition.INTAKE;
        } else if (HardwareAdapter.OPERATOR_STICK.getButton(6).isPressed()) {
            commands.intakePosition = IntakePosition.STORED;
        }
        
        if (HardwareAdapter.OPERATOR_STICK.getButton(4).isHeld()) {
            commands.intakeSpeed = IntakeSpeed.INTAKE;
        } else if (HardwareAdapter.OPERATOR_STICK.getButton(5).isHeld()) {
            commands.intakeSpeed = IntakeSpeed.OUTPUT;
        } else {
            commands.intakeSpeed = IntakeSpeed.NEUTRAL;
        }
        
        return commands;
    }
}
