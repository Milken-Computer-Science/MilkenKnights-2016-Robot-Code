package com.milkenknights.util.hardware;

import com.milkenknights.util.MkJoystick;

public class ExtremeProJoystick extends MkJoystick {

    public static final int NUMBER_OF_BUTTONS = 12;
    
    /**
     * Create a new ExtremeProJoystick.
     */
    public ExtremeProJoystick(int port) {
        super(port);
    }

    @Override
    public int getNumberOfButtons() {
        return NUMBER_OF_BUTTONS;
    }

}
