package com.milkenknights.util;

import com.milkenknights.frc2016.Constants;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MkCompressor extends Compressor implements MkSendable, Loopable {
    
    private final MkPressureTransducer pressureTransducer;
    
    @SuppressWarnings("unused")
    private MkCompressor() {
        super();
        
        pressureTransducer = null;
    }

    /**
     * Create a new compressor.
     */
    public MkCompressor(final int id, final MkPressureTransducer pressureTransducer) {
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
        if (getStoredPressure() > Constants.Pcm.HIGH_PRESSURE) {
            this.stop();
        } else if (getStoredPressure() < Constants.Pcm.LOW_PRESSURE) {
            this.start();
        }
    }

}
