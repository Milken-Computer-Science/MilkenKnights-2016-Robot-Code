package com.milkenknights.util;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * MKSpeedController is a wrapper of SpeedController to make inverting the motor easier and 
 * also allow for multiple motors to be linked.
 * 
 * @author austinshalit
 *
 */
public class MkCanTalon implements SpeedController {

    private CANTalon[] controllers = {};
    private boolean invert;
    private boolean print;
    
    /**
     * A constructor for a single MKSpeedController object.
     * 
     * @param controller A CANTalon object.
     */
    public MkCanTalon(final CANTalon controller) {
        this(new CANTalon[] {controller});
    }
    
    /**
     * A constructor for multiple SpeedController objects.
     * 
     * @param controllers The SpeedController objects.
     */
    public MkCanTalon(final CANTalon[] controllers) {
        this.controllers = controllers;
        enableBrakeMode(false);
    }
    
    /**
     * Set if the output of this SpeedController should be inverted.
     * 
     * @param inverted If the output of this speed controller should be inverted
     */
    public void setInverted(final boolean inverted) {
        invert = inverted;
    }
    
    /**
     * Get if the output of this SpeedController is being inverted.
     * 
     * @return If the output of this SpeedController is being inverted.
     */
    public boolean getInverted() {
        return invert;
    }
    
    /**
     * Gets if the motor is inverted as either a positive or negative value.
     */
    private double sign() {
        return invert ? -1.0 : 1.0;
    }
    
    /**
     * Gets the current draw of this speed controller.
     * 
     * @return The current in amps
     */
    public double getCurrent() {
        double current = 0.0;
        for (final CANTalon t : controllers) {
            current += t.getOutputCurrent();
        }
        return current;
    }
    
    @Override
    public void pidWrite(final double output) {
        for (final CANTalon controller : controllers) {
            controller.pidWrite(output * sign());
        }
    }

    @Override
    public double get() {
        return controllers[0].get() * sign();
    }

    @Override
    public void set(final double speed, final byte syncGroup) {
        for (final CANTalon controller : controllers) {
            controller.set(speed * sign(), syncGroup);
        }
    }

    @Override
    public final void set(final double speed) {
        if (print) {
            System.out.println(speed);
        }
        
        for (final CANTalon controller : controllers) {
            controller.set(speed * sign());
        }
    }

    @Override
    public final void disable() {
        for (final CANTalon controller : controllers) {
            controller.disable();
        }
    }
    
    /**
     * Sets the internal brake mode of the CANTalons.
     * 
     * @param brake If the controller should brake
     */
    public final void enableBrakeMode(final boolean brake) {
        for (final CANTalon controller : controllers) {
            controller.enableBrakeMode(brake);
        }
    }
    
    public final void print(final boolean print) {
        this.print = print;
    }
    
    public void stopMotor() {
        // Deprecated
    }

}
