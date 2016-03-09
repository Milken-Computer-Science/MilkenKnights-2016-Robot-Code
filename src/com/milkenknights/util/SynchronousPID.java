package com.milkenknights.util;

import edu.wpi.first.wpilibj.util.BoundaryException;

/**
 * Class implements a PID Control Loop.
 * <p>
 * Does all computation synchronously (i.e. the calculate() function must be
 * called by the user from his own thread)
 */
public class SynchronousPid {
    private double proportionalCoefficient; // factor for "proportional" control
    private double integralCoefficient;     // factor for "integral" control
    private double derivativeCoefficient;   // factor for "derivative" control
    private double maximumOutput = 1.0;     // |maximum output|
    private double minimumOutput = -1.0;    // |minimum output|
    private double maximumInput;      // maximum input - limit setpoint to this
    private double minimumInput;      // minimum input - limit setpoint to this
    private boolean continuous;     // do the endpoints wrap around? eg. Absolute encoder
    private double prevError;         // the prior sensor input (used to compute velocity)
    private double totalError;        //the sum of the errors for use in the integral calc
    private double setpoint;
    private double error;
    private double result;
    private double lastInput = Double.NaN;

    /**
     * Allocate a PID object with the given constants for P, I, D.
     *
     * @param proportionalCoefficient the proportional coefficient
     * @param integralCoefficient the integral coefficient
     * @param derivativeCoefficient the derivative coefficient
     */
    public SynchronousPid(final double proportionalCoefficient, final double integralCoefficient,
            final double derivativeCoefficient) {
        this.proportionalCoefficient = proportionalCoefficient;
        this.integralCoefficient = integralCoefficient;
        this.derivativeCoefficient = derivativeCoefficient;
    }
    
    public SynchronousPid() {
        // Default constructor
    }

    /**
     * Read the input, calculate the output accordingly, and write to the output.
     * This should be called at a constant rate by the user (ex. in a timed thread)
     *
     * @param input the input
     */
    public double calculate(final double input) {
        lastInput = input;
        error = setpoint - input;
        if (continuous && Math.abs(error) > (maximumInput - minimumInput) / 2) {
            if (error > 0) {
                error = error - maximumInput + minimumInput;
            } else {
                error = error + maximumInput - minimumInput;
            }
        }

        if ((error * proportionalCoefficient < maximumOutput) && (error * proportionalCoefficient > minimumOutput)) {
            totalError += error;
        } else {
            totalError = 0;
        }

        result = (proportionalCoefficient * error 
                + integralCoefficient * totalError 
                + derivativeCoefficient * (error - prevError));
        prevError = error;

        if (result > maximumOutput) {
            result = maximumOutput;
        } else if (result < minimumOutput) {
            result = minimumOutput;
        }
        return result;
    }

    /**
     * Set the PID controller gain parameters.
     * Set the proportional, integral, and differential coefficients.
     *
     * @param proportionalCoefficient Proportional coefficient
     * @param integralCoefficient Integral coefficient
     * @param derivativeCoefficient Differential coefficient
     */
    public void setPid(final double proportionalCoefficient, final double integralCoefficient,
            final double derivativeCoefficient) {
        this.proportionalCoefficient = proportionalCoefficient;
        this.integralCoefficient = integralCoefficient;
        this.derivativeCoefficient = derivativeCoefficient;
    }

    /**
     * Get the Proportional coefficient.
     *
     * @return proportional coefficient
     */
    public double getP() {
        return proportionalCoefficient;
    }

    /**
     * Get the Integral coefficient.
     *
     * @return integral coefficient
     */
    public double getI() {
        return integralCoefficient;
    }

    /**
     * Get the Differential coefficient.
     *
     * @return differential coefficient
     */
    public double getD() {
        return derivativeCoefficient;
    }

    /**
     * Return the current PID result.  This is always centered on zero and constrained the the max and min outs.
     *
     * @return the latest calculated output
     */
    public double get() {
        return result;
    }

    /**
     * Set the PID controller to consider the input to be continuous,
     * Rather then using the max and min in as constraints, it considers them to
     * be the same point and automatically calculates the shortest route to
     * the setpoint.
     *
     * @param continuous Set to true turns on continuous, false turns off continuous
     */
    public void setContinuous(final boolean continuous) {
        this.continuous = continuous;
    }

    /**
     * Set the PID controller to consider the input to be continuous,
     * Rather then using the max and min in as constraints, it considers them to
     * be the same point and automatically calculates the shortest route to
     * the setpoint.
     */
    public void setContinuous() {
        this.setContinuous(true);
    }

    /**
     * Sets the maximum and minimum values expected from the input.
     *
     * @param minimumInput the minimum value expected from the input
     * @param maximumInput the maximum value expected from the output
     */
    public void setInputRange(final double minimumInput, final double maximumInput) {
        if (minimumInput > maximumInput) {
            throw new BoundaryException("Lower bound is greater than upper bound");
        }
        this.minimumInput = minimumInput;
        this.maximumInput = maximumInput;
        setSetpoint(setpoint);
    }

    /**
     * Sets the minimum and maximum values to write.
     *
     * @param minimumOutput the minimum value to write to the output
     * @param maximumOutput the maximum value to write to the output
     */
    public void setOutputRange(final double minimumOutput, final double maximumOutput) {
        if (minimumOutput > maximumOutput) {
            throw new BoundaryException("Lower bound is greater than upper bound");
        }
        this.minimumOutput = minimumOutput;
        this.maximumOutput = maximumOutput;
    }

    /**
     * Set the setpoint for the PID controller.
     *
     * @param setpoint the desired setpoint
     */
    public void setSetpoint(final double setpoint) {
        if (maximumInput > minimumInput) {
            if (setpoint > maximumInput) {
                this.setpoint = maximumInput;
            } else if (setpoint < minimumInput) {
                this.setpoint = minimumInput;
            } else {
                this.setpoint = setpoint;
            }
        } else {
            this.setpoint = setpoint;
        }
    }

    /**
     * Returns the current setpoint of the PID controller.
     *
     * @return the current setpoint
     */
    public double getSetpoint() {
        return setpoint;
    }

    /**
     * Returns the current difference of the input from the setpoint.
     *
     * @return the current error
     */
    public double getError() {
        return error;
    }

    /**
     * Return true if the error is within the tolerance.
     *
     * @return true if the error is less than the tolerance
     */
    public boolean onTarget(final double tolerance) {
        return lastInput != Double.NaN && Math.abs(lastInput - setpoint) < tolerance;
    }

    /**
     * Reset all internal terms.
     */
    public void reset() {
        lastInput = Double.NaN;
        prevError = 0;
        totalError = 0;
        result = 0;
        setpoint = 0;
    }

    public void resetIntegrator() {
        totalError = 0;
    }

}
