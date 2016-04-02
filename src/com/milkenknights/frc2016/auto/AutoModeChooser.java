package com.milkenknights.frc2016.auto;

import com.milkenknights.frc2016.auto.modes.DoNothingAutoMode;
import com.milkenknights.frc2016.auto.modes.LowBarAutoMode;
import com.milkenknights.frc2016.auto.modes.ReachAutoMode;
import com.milkenknights.util.Button;
import com.milkenknights.util.MkSendable;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.ArrayList;

public final class AutoModeChooser implements MkSendable {
    
    private final ArrayList<Class<? extends AutoMode>> autoModes;
    private final Button button;
    private int count;
    
    /**
     * Create a new AutoModeChooser.
     */
    public AutoModeChooser(Button button) {
        autoModes = new ArrayList<Class<? extends AutoMode>>();
        this.button = button;
        
        autoModes.add(DoNothingAutoMode.class);
        autoModes.add(ReachAutoMode.class);
        autoModes.add(LowBarAutoMode.class);
    }
    
    /**
     * Get the selected AutoMode.
     */
    public AutoMode getAutoMode() {
        try {
            return autoModes.get(count % autoModes.size()).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return new DoNothingAutoMode();
    }

    @Override
    public void updateSmartDashboard() {
        if (button.isPressed()) {
            count++;
        }
        SmartDashboard.putString("AutoMode:", autoModes.get(count % autoModes.size()).getSimpleName());
    }

}
