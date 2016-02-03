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

        if (HardwareAdapter.OPERATOR_STICK.getRawButton(1)) {
            commands.fireCatapult = true;
        } else {
            commands.fireCatapult = false;
        }
        
        if (HardwareAdapter.OPERATOR_STICK.getRawButton(2)) {
            commands.intakePosition = IntakePosition.INTAKE;
        } else if (HardwareAdapter.OPERATOR_STICK.getRawButton(3)) {
            commands.intakePosition = IntakePosition.PROTECT;
        } else if (HardwareAdapter.OPERATOR_STICK.getRawButton(4)) {
            commands.intakePosition = IntakePosition.STORED;
        }
        
        if (HardwareAdapter.OPERATOR_STICK.getRawButton(5)) {
            commands.intakeSpeed = IntakeSpeed.INTAKE;
        } else {
            commands.intakeSpeed = IntakeSpeed.STOP;
        }
        
        return commands;
    }
}
