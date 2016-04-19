package com.milkenknights.util;

import com.kauailabs.navx.frc.AHRS;

public class MkGyro {

    private final AHRS navX;
    private double offset;

    public MkGyro(final AHRS navX) {
        this.navX = navX;
    }

    public void zeroYaw() {
        offset = -navX.getAngle();
    }

    public double getAngle() {
        return navX.getAngle() + offset;
    }

    public double getRate() {
        return navX.getRate();
    }
    
    public double getPitch() {
        return navX.getPitch();
    }
    
    public double getRoll() {
        return navX.getRoll();
    }
    
    public boolean isMoving() {
        return navX.isMoving();
    }
    
}
