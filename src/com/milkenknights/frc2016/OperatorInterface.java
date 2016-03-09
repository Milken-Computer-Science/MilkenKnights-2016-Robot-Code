package com.milkenknights.frc2016;

import com.milkenknights.frc2016.behavior.Commands;
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
        
        commands.driveSpeed = -HardwareAdapter.STICK.getY();
        commands.driveRotate = -HardwareAdapter.STICK.getTwist();
        
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
        
        return commands;
    }
}
