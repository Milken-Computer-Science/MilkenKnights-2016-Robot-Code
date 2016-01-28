package com.milkenknights.frc2016;

import com.milkenknights.frc2016.behavior.Commands;
import com.milkenknights.frc2016.subsystems.Intake.IntakeSpeed;

import edu.wpi.first.wpilibj.Joystick;

public class OperatorInterface {

    private Commands commands = new Commands();
    
    Joystick leftStick = HardwareAdapter.LEFT_STICK;
    Joystick rightStick = HardwareAdapter.RIGHT_STICK;
    Joystick operatorStick = HardwareAdapter.OPERATOR_STICK;
    
    public void reset() {
        commands = new Commands();
    }
    
    /**
     * Return the commands that the operator wants to execute.
     */
    public Commands getCommands() {
        if (operatorStick.getRawButton(1)) {
            commands.intakeSpeed = IntakeSpeed.INTAKE;
        }
        
        return commands;
    }
}
