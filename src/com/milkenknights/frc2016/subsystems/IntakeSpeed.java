package com.milkenknights.frc2016.subsystems;

import com.milkenknights.frc2016.Constants;
import com.milkenknights.util.MkCanTalon;
import com.milkenknights.util.Subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeSpeed extends Subsystem {
    
    private MkCanTalon talon;
    private IntakeSpeedState speed = IntakeSpeedState.NEUTRAL;
    
    public enum IntakeSpeedState {
        NEUTRAL(0), INTAKE(Constants.Subsystems.Intake.Speed.INTAKE), OUTPUT(Constants.Subsystems.Intake.Speed.OUTPUT);
        
        public final double speed;
        private IntakeSpeedState(final double speed) {
            this.speed = speed;
        }
    }
    
    /**
     * Create a new IntakeSpeed.
     */
    public IntakeSpeed(final String name, final MkCanTalon talon) {
        super(name);
        
        this.talon = talon;
    }
    
    /**
     * Get the speed of the intake.
     */
    public IntakeSpeedState getSpeed() {
        return speed;
    }
    
    /**
     * Set the speed of the intake.
     */
    public void setSpeed(final IntakeSpeedState speed) {
        talon.set(speed.speed);
        this.speed = speed;
    }

    @Override
    public void updateSmartDashboard() {
        SmartDashboard.putString("Intake Speed State", speed.toString());
    }

}
