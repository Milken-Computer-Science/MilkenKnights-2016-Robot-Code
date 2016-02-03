package com.milkenknights.frc2016.auto;

import com.milkenknights.frc2016.auto.actions.TimeoutAction;
import com.milkenknights.frc2016.auto.actions.WaitForDriveAction;
import com.milkenknights.frc2016.auto.actions.WaitForDriveDistanceAction;
import com.milkenknights.frc2016.auto.actions.WaitForIntakeArmAction;

public abstract class AutoMode extends AutoModeBase {

    public void waitTime(double seconds) throws AutoModeEndedException {
        runAction(new TimeoutAction(seconds));
    }
    
    public void waitForDrive(double seconds) throws AutoModeEndedException {
        runAction(new WaitForDriveAction(seconds));
    }
    
    public void waitForDriveDistance(double distance, boolean positive, double seconds) throws AutoModeEndedException {
        runAction(new WaitForDriveDistanceAction(distance, positive, seconds));
    }
    
    public void waitForIntake(double seconds) throws AutoModeEndedException {
        runAction(new WaitForIntakeArmAction(seconds));
    }
    
}
