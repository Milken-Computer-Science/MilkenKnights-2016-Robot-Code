package com.milkenknights.frc2016.subsystems;

import com.milkenknights.util.MkCanTalon;
import com.milkenknights.util.Subsystem;

public class Intake extends Subsystem {
    
    public enum IntakeSpeed {
        STOP(0), INTAKE(1), OUTPUT(-1);
        
        public final double speed;
        private IntakeSpeed(double speed) {
            this.speed = speed;
        }
    }
    
    private MkCanTalon talon;
    
    /**
     * Create a new intake subsystem.
     * 
     * @param name The name of the subsystem
     * @param talon The CanTalon used to control the intake
     */
    public Intake(String name, MkCanTalon talon) {
        super(name);
        
        this.talon = talon;
    }

    public void setSpeed(IntakeSpeed speed) {
        talon.set(speed.speed);
    }

    @Override
    public void updateSmartDashboard() {
        // TODO Auto-generated method stub

    }

}
