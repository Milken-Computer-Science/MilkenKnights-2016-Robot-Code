package com.milkenknights.util;

import edu.wpi.first.wpilibj.AnalogInput;

public abstract class MkPressureTransducer extends AnalogInput {

    public MkPressureTransducer(final int arg0) {
        super(arg0);
    }

    public abstract double getPressure();
    
}
