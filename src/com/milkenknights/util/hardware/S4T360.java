package com.milkenknights.util.hardware;

import com.milkenknights.util.MkEncoder;

public class S4T360 extends MkEncoder {
    
    private static final int PULSES_PER_REVOLUTION = 360;

    public S4T360(int channelA, int channelB) {
        super(channelA, channelB);
    }

    @Override
    public int getPulsesPerRevolution() {
        return PULSES_PER_REVOLUTION;
    }

}
