package com.milkenknights.util.hardware;

import com.milkenknights.util.MkEncoder;

public class S4T360 extends MkEncoder {
    
    private static final double PULSES_PER_REVOLUTION = 360;

    public S4T360(final int channelA, final int channelB) {
        super(channelA, channelB);
    }

    @Override
    public double getPulsesPerRevolution() {
        return PULSES_PER_REVOLUTION;
    }

}
