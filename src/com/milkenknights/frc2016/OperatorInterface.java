package com.milkenknights.frc2016;

import com.milkenknights.frc2016.behavior.Commands;
import com.milkenknights.frc2016.subsystems.Drive.DriveGear;
import com.milkenknights.frc2016.subsystems.Intake.IntakePosition;
import com.milkenknights.frc2016.subsystems.Intake.IntakeSpeed;

public class OperatorInterface {

    private Commands commands = new Commands();
    private boolean buttonSevenLast;
    private boolean buttonEightLast;
    
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
        
        if (HardwareAdapter.DRIVE_STICK.getRawButton(1) && HardwareAdapter.DRIVE_STICK.getRawButton(2)) {
            commands.fireCatapult = true;
        } else {
            commands.fireCatapult = false;
        }
        
        if (!HardwareAdapter.DRIVE_STICK.getRawButton(7)) {
            buttonSevenLast = true;
        } else if (buttonSevenLast && HardwareAdapter.DRIVE_STICK.getRawButton(7)) {
            buttonSevenLast = false;
            commands.driveGear = HardwareAdapter.DRIVE.getGear() == DriveGear.HIGH ? DriveGear.LOW : DriveGear.HIGH;
        }
        
        if (!HardwareAdapter.DRIVE_STICK.getRawButton(8)) {
            buttonEightLast = true;
        } else if (buttonEightLast && HardwareAdapter.DRIVE_STICK.getRawButton(8)) {
            buttonEightLast = false;
            commands.reverseDrive = true;
        }
        
        
        if (HardwareAdapter.OPERATOR_STICK.getRawButton(7)) {
            commands.intakePosition = IntakePosition.PROTECT;
        } else if (HardwareAdapter.OPERATOR_STICK.getRawButton(6)) {
            commands.intakePosition = IntakePosition.INTAKE;
        } else if (HardwareAdapter.OPERATOR_STICK.getRawButton(10)) {
            commands.intakePosition = IntakePosition.STORED;
        }
        
        if (HardwareAdapter.OPERATOR_STICK.getRawButton(2)) {
            commands.intakeSpeed = IntakeSpeed.INTAKE;
        } else if (HardwareAdapter.OPERATOR_STICK.getRawButton(3)) {
            commands.intakeSpeed = IntakeSpeed.OUTPUT;
        } else {
            commands.intakeSpeed = IntakeSpeed.NEUTRAL;
        }
        
        return commands;
    }
}
