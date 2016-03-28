package com.milkenknights.frc2016.subsystems;

import com.milkenknights.util.Subsystem;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LightManager extends Subsystem {

    final Solenoid flashlight;
    final Solenoid greenLedRing;
    
    /**
     * Create a new light manager.
     */
    public LightManager(final String name, final Solenoid flashlight, final Solenoid greenLedRing) {
        super(name);
        this.flashlight = flashlight;
        this.greenLedRing = greenLedRing;
    }
    
    public void setFlashlight() {
        flashlight.set(true);
        greenLedRing.set(false);
    }
    
    public void setGreenLedRing() {
        flashlight.set(false);
        greenLedRing.set(true);
    }

    public void off() {
        flashlight.set(false);
        greenLedRing.set(false);
    }

    @Override
    public void updateSmartDashboard() {
        SmartDashboard.putBoolean("Flashlight State", flashlight.get());
        SmartDashboard.putBoolean("Green LED Ring State", greenLedRing.get());
    }
    
}
