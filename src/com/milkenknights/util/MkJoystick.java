package com.milkenknights.util;

import edu.wpi.first.wpilibj.Joystick;

import java.util.ArrayList;

public abstract class MkJoystick extends Joystick {
    
    private final ArrayList<Button> buttons;
    
    /**
     * Create a new MkJoystick.
     */
    public MkJoystick(int port) {
        super(port);
        
        buttons = new ArrayList<Button>();
        for (int i = 1; i <= getNumberOfButtons(); i++) {
            buttons.add(new Button(this, i));
        }
        buttons.trimToSize();
    }
    
    public Button getButton(final int button) {
        return buttons.get(button);
    }
    
    public abstract int getNumberOfButtons();

}
