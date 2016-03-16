package com.milkenknights.util;

import edu.wpi.first.wpilibj.Joystick;

public class Button {
    
    private final Joystick joystick;
    private final int rawButton;
    private boolean lastState;

    public Button(final Joystick joystick, final int rawButton) {
        this.joystick = joystick;
        this.rawButton = rawButton;
    }
    
    /**
     * Returns true if the button is pressed and this is the first time it is being run.
     */
    public boolean isPressed() {
        boolean isPressed =  !lastState && joystick.getRawButton(rawButton);
        update();
        return isPressed;
    }
    
    /**
     * Returns true when the button is released.
     */
    public boolean isReleased() {
        boolean isPressed = lastState && joystick.getRawButton(rawButton);
        update();
        return isPressed;
    }
    
    /**
     * Returns true when the button is being held.
     */
    public boolean isHeld() {
        update();
        return joystick.getRawButton(rawButton);
    }
    
    /**
     * Update the last state of the button.
     */
    private void update() {
        lastState = joystick.getRawButton(rawButton);
    }
    
}
