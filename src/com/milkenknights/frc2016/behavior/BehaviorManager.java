package com.milkenknights.frc2016.behavior;

import com.milkenknights.frc2016.HardwareAdapter;
import com.milkenknights.frc2016.subsystems.Drive;
import com.milkenknights.frc2016.subsystems.Intake;
import com.milkenknights.frc2016.behavior.routines.ManualRoutine;
import com.milkenknights.frc2016.behavior.routines.Routine;

public class BehaviorManager {
    
    private Routine currentRoutine;
    private RobotSetpoints setpoints;
    
    private ManualRoutine manualRoutine = new ManualRoutine();
    
    public BehaviorManager() {
        setpoints = new RobotSetpoints();
        setpoints.reset();
    }
    
    public void update(Commands commands) {
        setpoints.reset();
        
        setpoints = manualRoutine.update(commands, setpoints);
        

        if (setpoints.intakeAction == RobotSetpoints.IntakeAction.OPEN || setpoints.intakeAction == RobotSetpoints.IntakeAction.PREFER_OPEN) {

        } else if (setpoints.intakeAction == RobotSetpoints.IntakeAction.CLOSE || setpoints.intakeAction == RobotSetpoints.IntakeAction.PREFER_CLOSE) {

        }
        
        
    }
}
