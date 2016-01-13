package com.milkenknights.util;

/**
 * This is a Drive Signal class that wraps 2 doubles for drive motors.
 *
 */
public class MotorPairSignal {

    public double leftMotor;
    public double rightMotor;
    
    public static final MotorPairSignal NEUTRAL = new MotorPairSignal(0, 0);
    
    /**
     * 
     * @param left Left motor power.
     * @param right Right motor power.
     */
    public MotorPairSignal(double left, double right) {
        leftMotor = left;
        rightMotor = right;
    }
    
    public MotorPairSignal() {
        this(0.0, 0.0);
    }
    
}
