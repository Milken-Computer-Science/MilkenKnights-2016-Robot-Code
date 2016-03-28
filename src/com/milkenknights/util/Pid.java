package com.milkenknights.util;

/**
 * A wrapper to hold PID values.  
 * 
 * @author austinshalit
 *
 */
public class Pid {
    
    public final double proportionalCoefficient;
    public final double integralCoefficient;
    public final double derivativeCoefficient;
    
    /**
     * Make a new immutable PID wrapper.
     */
    public Pid(final double proportionalCoefficient, final double integralCoefficient,
            final double derivativeCoefficient) {
        this.proportionalCoefficient = proportionalCoefficient;
        this.integralCoefficient = integralCoefficient;
        this.derivativeCoefficient = derivativeCoefficient;
    }
    
    public String toString() {
        return "[" + proportionalCoefficient + ", " + integralCoefficient + ", " + derivativeCoefficient + "]";
    }
}
