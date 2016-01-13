package com.milkenknights.util;

import edu.wpi.first.wpilibj.Encoder;

public class MkEncoder extends Encoder {
    
    private double zeroOffset = 0;

    public MkEncoder(int channelA, int channelB) {
        super(channelA, channelB);
    }
    
    public double getDistance() {
        return this.getDistance();
    }
    
    public void setZeroOffset(double zeroOffset) {
        this.zeroOffset = zeroOffset;
    }

}
