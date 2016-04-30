package com.milkenknights.util;

import edu.wpi.first.wpilibj.Joystick;

import java.util.HashMap;

public class MkJoystick extends Joystick {
    
    private final int port;
    private final HashMap<Integer, Button> buttons;
    
    /**
     * Create a new MkJoystick.
     */
    public MkJoystick(final int port) {
        super(port);
        this.port = port;
        
        buttons = new HashMap<Integer, Button>();
    }
    
    /**
     * Gets a button of the joystick.  Creates a new Button object if one did not already exist. 
     * 
     * @param button The raw button number of the button to get
     * @return The button
     */
    public Button getButton(final int button) {
        if (!buttons.containsKey(button)) {
            buttons.put(button, new Button(this, button));
        }
        return buttons.get(button);
    }
    
    public int getPort() {
        return port;
    }

}
