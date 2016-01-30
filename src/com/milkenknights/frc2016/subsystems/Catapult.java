package com.milkenknights.frc2016.subsystems;

import com.milkenknights.util.Loopable;
import com.milkenknights.util.Subsystem;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Catapult extends Subsystem implements Loopable {
    
    private final double rotationPerPulse = 1028 * 50 / 14;
    
    public enum CatapultStates {
        MINIMUM, READY, FIRE
    }
    
    private CANTalon talon;
    private CatapultStates state;

    /**
     * Create a new catapult subsystem.
     * 
     * @param name The name of the subsystem
     * @param talon The talon to control the catapult
     */
    public Catapult(String name, CANTalon talon) {
        super(name);
        
        talon.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
        talon.changeControlMode(CANTalon.TalonControlMode.Position);
        talon.reverseSensor(true);;
        
        talon.setPosition(0);
        talon.set(0);
        
        talon.setPID(0.5, 0, 0);
        talon.setF(0);
        talon.configPeakOutputVoltage(15, -6);
        
        this.talon = talon;
        
        state = CatapultStates.READY;
    }
    
    public void fire() {
        state = CatapultStates.FIRE;
    }

    @Override
    public void updateSmartDashboard() {
        SmartDashboard.putString("Catapult State", state.toString());
        SmartDashboard.putNumber("Catapult Count", talon.get());
        SmartDashboard.putNumber("Catapult Error", talon.getError());
    }

    @Override
    public void update() {
        switch (state) {
            case MINIMUM:
                talon.setPosition(0);
                break;
            case READY:
                break;
            case FIRE:
                talon.set(50 / 14);
                if (Math.abs(talon.getError()) < .1) {
                    state = CatapultStates.MINIMUM;
                }
                break;
            default:
                break;
        }
        
    }

}
