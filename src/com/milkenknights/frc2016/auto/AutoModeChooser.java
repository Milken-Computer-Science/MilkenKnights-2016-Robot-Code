package com.milkenknights.frc2016.auto;

import com.milkenknights.frc2016.auto.modes.DoNothingAutoMode;
import com.milkenknights.frc2016.auto.modes.LowBarAutoMode;
import com.milkenknights.frc2016.auto.modes.Position4AutoMode;
import com.milkenknights.frc2016.auto.modes.ReachAutoMode;
import com.milkenknights.util.Button;
import com.milkenknights.util.Loopable;
import com.milkenknights.util.MkSendable;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.ArrayList;

public final class AutoModeChooser implements MkSendable, Loopable {
    
    private final ArrayList<Class<? extends AutoMode>> autoModes;
    private final Button button;
    private int count;
    
    /**
     * Create a new AutoModeChooser.
     */
    public AutoModeChooser(final Button button) {
        autoModes = new ArrayList<Class<? extends AutoMode>>();
        this.button = button;

        autoModes.add(Position4AutoMode.class);
        autoModes.add(LowBarAutoMode.class);
        autoModes.add(DoNothingAutoMode.class);
        autoModes.add(ReachAutoMode.class);
    }
    
    /**
     * Get the selected AutoMode.
     */
    public AutoMode getAutoMode() {
        AutoMode autoMode;
        try {
            autoMode = autoModes.get(count % autoModes.size()).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            autoMode = new DoNothingAutoMode();
        }
        return autoMode;
    }

    @Override
    public void updateSmartDashboard() {
        SmartDashboard.putString("AutoMode:", autoModes.get(count % autoModes.size()).getSimpleName());
    }

    @Override
    public void update() {
        if (button.isPressed()) {
            count++;
        }
    }

}
