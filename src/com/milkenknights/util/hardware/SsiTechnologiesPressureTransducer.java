package com.milkenknights.util.hardware;

import com.milkenknights.util.MkPressureTransducer;
import com.milkenknights.util.MkSendable;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SsiTechnologiesPressureTransducer extends MkPressureTransducer implements MkSendable {
    
    public static final double SCALE_FACTOR = 50;
    public static final double OFFSET = -25;

    public SsiTechnologiesPressureTransducer(int arg0) {
        super(arg0);
    }
    
    /**
     * Returns the pressure reported by the transducer.
     */
    public double getPressure() {
        return getVoltage() * SCALE_FACTOR + OFFSET;
    }

    @Override
    public void updateSmartDashboard() {
        SmartDashboard.putNumber("Pressure Transducer: PSI", getPressure());
    }

}
