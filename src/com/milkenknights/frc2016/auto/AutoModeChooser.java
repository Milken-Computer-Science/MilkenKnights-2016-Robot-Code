package com.milkenknights.frc2016.auto;

import com.milkenknights.frc2016.auto.modes.DoNothingAutoMode;
import com.milkenknights.frc2016.auto.modes.LowBarAutoMode;
import com.milkenknights.frc2016.auto.modes.ReachAutoMode;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoModeChooser {
    
    SendableChooser sendableChooser;

    /**
     * Create a new AutoModeChooser.
     */
    public AutoModeChooser() {
        sendableChooser = new SendableChooser();
        
        sendableChooser.addDefault("Do Nothing", new DoNothingAutoMode());
        sendableChooser.addObject("Reach", new ReachAutoMode());
        sendableChooser.addObject("(1) Low Bar", new LowBarAutoMode());
    }
    
    public void start() {
        SmartDashboard.putData("AutoMode Chooser", sendableChooser);
    }
    
    public AutoMode getSelected() {
        return (AutoMode) sendableChooser.getSelected();
    }    
    
}
