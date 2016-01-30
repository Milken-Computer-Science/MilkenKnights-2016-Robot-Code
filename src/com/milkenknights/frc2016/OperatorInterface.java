package com.milkenknights.frc2016;

import com.milkenknights.frc2016.behavior.Commands;

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
        }
        
        return commands;
    }
}
