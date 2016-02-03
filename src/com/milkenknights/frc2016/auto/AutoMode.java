package com.milkenknights.frc2016.auto;

import com.milkenknights.frc2016.auto.actions.TimeoutAction;

public abstract class AutoMode extends AutoModeBase {

    public void waitTime(double seconds) throws AutoModeEndedException {
        runAction(new TimeoutAction(seconds));
    }
    
}
