package com.milkenknights.frc2016.auto;

import com.milkenknights.frc2016.auto.actions.TimeoutAction;
import com.milkenknights.frc2016.auto.actions.WaitForDriveAction;
import com.milkenknights.frc2016.auto.actions.WaitForDriveDistanceAction;
import com.milkenknights.frc2016.auto.actions.WaitForIntakeArmAction;

public abstract class AutoMode extends AutoModeBase {

    /**
     * Run a new TimeoutAction.
     */
    public void waitTime(final double seconds) throws AutoModeEndedException {
        runAction(new TimeoutAction(seconds));
    }
    
    /**
     * Run a new WaitForDriveAction.
     */
    public void waitForDrive(final double seconds) throws AutoModeEndedException {
        runAction(new WaitForDriveAction(seconds));
    }
    
    /**
     * Run a new WaitForDriveDistanceAction.
     */
    public void waitForDriveDistance(final double distance, final boolean positive,
            final double seconds) throws AutoModeEndedException {
        runAction(new WaitForDriveDistanceAction(distance, positive, seconds));
    }
    
    /**
     * Run a new WaitForIntakeArmAction.
     */
    public void waitForIntake(final double seconds) throws AutoModeEndedException {
        runAction(new WaitForIntakeArmAction(seconds));
    }
    
}
