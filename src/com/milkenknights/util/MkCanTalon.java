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
    private boolean invert = false;
    
    /**
     * A constructor for a single MKSpeedController object.
     * 
     * @param controller A CANTalon object.
     */
    public MkCanTalon(CANTalon controller) {
        this(new CANTalon[] {controller});
    }
    
    /**
     * A constructor for multiple SpeedController objects.
     * 
     * @param controllers The SpeedController objects.
     */
    public MkCanTalon(CANTalon[] controllers) {
        this.controllers = controllers;
        enableBrakeMode(false);
    }
    
    /**
     * Set if the output of this SpeedController should be inverted.
     * 
     * @param inverted If the output of this speed controller should be inverted
     */
    public void setInverted(boolean inverted) {
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
        return (invert ? -1.0 : 1.0);
    }
    
    /**
     * Gets the current draw of this speed controller.
     * 
     * @return The current in amps
     */
    public double getCurrent() {
        double current = 0.0;
        for (CANTalon t : controllers) {
            current += t.getOutputCurrent();
        }
        return current;
    }
    
    @Override
    public void pidWrite(double output) {
        for (CANTalon controller : controllers) {
            controller.pidWrite(output * sign());
        }
    }

    @Override
    public double get() {
        return controllers[0].get() * sign();
    }

    @Override
    public void set(double speed, byte syncGroup) {
        for (CANTalon controller : controllers) {
            controller.set(speed * sign(), syncGroup);
        }
    }

    @Override
    public void set(double speed) {
        for (CANTalon controller : controllers) {
            controller.set(speed * sign());
        }
    }

    @Override
    public void disable() {
        for (CANTalon controller : controllers) {
            controller.disable();
        }
    }
    
    /**
     * Sets the internal brake mode of the CANTalons.
     * 
     * @param brake If the controller should brake
     */
    public void enableBrakeMode(boolean brake) {
        for (CANTalon controller : controllers) {
            controller.enableBrakeMode(brake);
        }
    }
    
    public void stopMotor() {
        
    }

}
