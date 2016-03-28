package com.milkenknights.util;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MkCompressor extends Compressor implements MkSendable, Loopable {
    
    private final MkPressureTransducer pressureTransducer;
    
    @SuppressWarnings("unused")
    private MkCompressor() {
        pressureTransducer = null;
    }

    /**
     * Create a new compressor.
     */
    public MkCompressor(int id, MkPressureTransducer pressureTransducer) {
        super(id);
        this.pressureTransducer = pressureTransducer;
    }
    
    public double getStoredPressure() {
        return pressureTransducer.getPressure();
    }

    @Override
    public void updateSmartDashboard() {
        SmartDashboard.putBoolean("Compressor Enabled", enabled());
        SmartDashboard.putBoolean("Compressor Switch", getPressureSwitchValue());
        SmartDashboard.putNumber("Pneumatic Pressure", getStoredPressure());
    }

    @Override
    public void update() {
        if (getStoredPressure() > 115.0) {
            this.stop();
        } else if (getStoredPressure() < 70.0) {
            this.start();
        }
    }

}
