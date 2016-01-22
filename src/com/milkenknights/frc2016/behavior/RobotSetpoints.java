package com.milkenknights.frc2016.behavior;

public class RobotSetpoints {
    
    public enum IntakeAction {
        NONE, OPEN, CLOSE, PREFER_OPEN, PREFER_CLOSE
    }
    
    public enum IntakeWheelAction {
        NONE, INTAKE, EXHAUST, STOP, INTAKE_CAN
    }
    
    public IntakeAction intakeAction;
    public IntakeWheelAction intakeWheelAction;

    public void reset() {
        intakeAction = IntakeAction.NONE;
        intakeWheelAction = IntakeWheelAction.NONE;
    }
}
