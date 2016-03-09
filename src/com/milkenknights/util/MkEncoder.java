package com.milkenknights.util;

import edu.wpi.first.wpilibj.Encoder;

public abstract class MkEncoder extends Encoder {

    public MkEncoder(final int channelA, final int channelB) {
        super(channelA, channelB);
    }
    
    public abstract double getPulsesPerRevolution();
    
}
