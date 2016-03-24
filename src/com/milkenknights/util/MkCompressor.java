package com.milkenknights.util;

import edu.wpi.first.wpilibj.Compressor;

public class MkCompressor extends Compressor {
    
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

}
