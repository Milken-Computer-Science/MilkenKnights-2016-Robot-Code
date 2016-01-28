package com.milkenknights.frc2016;

import com.milkenknights.frc2016.behavior.Commands;
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

        if (HardwareAdapter.OPERATOR_STICK.getRawButton(1)) {
            commands.intakeSpeed = IntakeSpeed.INTAKE;
        }
        
        return commands;
    }
}
