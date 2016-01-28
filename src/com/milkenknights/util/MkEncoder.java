package com.milkenknights.util;

import edu.wpi.first.wpilibj.Encoder;

public abstract class MkEncoder extends Encoder {

    public MkEncoder(int channelA, int channelB) {
        super(channelA, channelB);
    }
    
    public abstract int getPulsesPerRevolution();
    
}
