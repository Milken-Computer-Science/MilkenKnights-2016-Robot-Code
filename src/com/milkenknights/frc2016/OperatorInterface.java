package com.milkenknights.frc2016;

import com.milkenknights.frc2016.behavior.Commands;
import com.milkenknights.frc2016.subsystems.Drive.DriveGear;
import com.milkenknights.frc2016.subsystems.Intake.IntakePosition;
import com.milkenknights.frc2016.subsystems.Intake.IntakeSpeed;

public class OperatorInterface {

    private Commands commands = new Commands();
    private boolean buttonSevenLast = false;
    private boolean buttonEightLast = false;
    
    public void reset() {
        commands = new Commands();
    }
    
    /**
     * Return the commands that the operator wants to execute.
     */
    public Commands getCommands() {
        reset();
        
        commands.driveSpeed = -HardwareAdapter.STICK.getY();
        commands.driveRotate = -HardwareAdapter.STICK.getTwist() * 1.5;
        
        if (HardwareAdapter.STICK.getRawButton(1) && HardwareAdapter.STICK.getRawButton(2)) {
            commands.fireCatapult = true;
        } else {
            commands.fireCatapult = false;
        }
        
        if (HardwareAdapter.STICK.getRawButton(3)) {
            commands.intakePosition = IntakePosition.PROTECT;
        } else if (HardwareAdapter.STICK.getRawButton(4)) {
            commands.intakePosition = IntakePosition.INTAKE;
        } else if (HardwareAdapter.STICK.getRawButton(5)) {
            commands.intakePosition = IntakePosition.STORED;
        }
        
        if (HardwareAdapter.STICK.getRawButton(4)) {
            commands.intakeSpeed = IntakeSpeed.INTAKE;
        } else if (HardwareAdapter.STICK.getRawButton(6)) {
            commands.intakeSpeed = IntakeSpeed.OUTPUT;
        } else {
            commands.intakeSpeed = IntakeSpeed.NEUTRAL;
        }
        
        
        if (!HardwareAdapter.STICK.getRawButton(7)) {
        	buttonSevenLast = true;
        } else if (buttonSevenLast && HardwareAdapter.STICK.getRawButton(7)) {
        	buttonSevenLast = false;
        	commands.driveGear = HardwareAdapter.DRIVE.getGear() == DriveGear.HIGH ? DriveGear.LOW : DriveGear.HIGH;
        }
        
        if (!HardwareAdapter.STICK.getRawButton(8)) {
        	buttonEightLast = true;
        } else if (buttonEightLast && HardwareAdapter.STICK.getRawButton(8)) {
        	buttonEightLast = false;
        	commands.reverseDrive = true;
        }
        
        return commands;
    }
}
