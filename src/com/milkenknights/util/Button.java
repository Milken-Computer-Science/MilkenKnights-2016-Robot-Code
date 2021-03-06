package com.milkenknights.util;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

public class Button {
    
    private final MkJoystick joystick;
    private final int rawButton;
    private boolean lastState;

    public Button(final MkJoystick joystick, final int rawButton) {
        this.joystick = joystick;
        this.rawButton = rawButton;
    }
    
    /**
     * Returns true if the button is pressed and this is the first time it is being run.
     */
    public boolean isPressed() {
        final boolean isPressed =  !lastState && joystick.getRawButton(rawButton);
        update();
        if (isPressed) {
            System.out.println(DriverStation.getInstance().getMatchTime() + "\t[Joystick] " + joystick.getPort()
                + "\t[Button]" + rawButton + "\t Pressed");
        }
        return isPressed;
    }
    
    /**
     * Returns true when the button is released.
     */
    public boolean isReleased() {
        final boolean isPressed = lastState && joystick.getRawButton(rawButton);
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
    
    public int getRawButton() {
        return rawButton;
    }
    
    public Joystick getJoystick() {
        return joystick;
    }
    
    public boolean isJoystickConnected() {
        return joystick.getButtonCount() > 0;
    }
    
    /**
     * Update the last state of the button.
     */
    private void update() {
        lastState = joystick.getRawButton(rawButton);
    }
    
}
