package com.milkenknights.util.drive;

/**
 * This is a Drive Signal class that wraps 2 doubles for drive motors.
 *
 */
public class MotorPairSignal {

    public static final MotorPairSignal NEUTRAL = new MotorPairSignal(0.0, 0.0);
    
    public final double leftMotor;
    public final double rightMotor;
    
    /**
     * 
     * @param left Left motor power.
     * @param right Right motor power.
     */
    public MotorPairSignal(final double left, final double right) {
        leftMotor = left;
        rightMotor = right;
    }
    
    public String toString() {
        return "[" + leftMotor + ", " + rightMotor + "]";
    }
    
}
