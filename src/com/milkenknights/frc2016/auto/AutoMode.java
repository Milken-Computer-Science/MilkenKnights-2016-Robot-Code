package com.milkenknights.frc2016.auto;

import com.milkenknights.frc2016.auto.actions.TimeoutAction;
import com.milkenknights.frc2016.auto.actions.WaitForActionArmAction;
import com.milkenknights.frc2016.auto.actions.WaitForCatapultAction;
import com.milkenknights.frc2016.auto.actions.WaitForCatapultStateAction;
import com.milkenknights.frc2016.auto.actions.WaitForDriveAction;
import com.milkenknights.frc2016.auto.actions.WaitForDriveDistanceAction;
import com.milkenknights.frc2016.auto.actions.WaitForDriveNotMovingAction;
import com.milkenknights.frc2016.auto.actions.WaitForIntakeArmAction;
import com.milkenknights.frc2016.subsystems.Catapult.CatapultState;

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
     * Run a new WaitForDriveNotMovingAction.
     */
    public void waitForDriveNotMoving(final double seconds) throws AutoModeEndedException {
        runAction(new WaitForDriveNotMovingAction(seconds));
    }
    
    /**
     * Run a new WaitForIntakeArmAction.
     */
    public void waitForIntake(final double seconds) throws AutoModeEndedException {
        runAction(new WaitForIntakeArmAction(seconds));
    }
    
    /**
     * Run a new WaitForActionArmAction.
     */
    public void waitForActionArm(final double seconds) throws AutoModeEndedException {
        runAction(new WaitForActionArmAction(seconds));
    }
    
    /**
     * Run a new WaitForCatapultAction.
     */
    public void waitForCatapult(final double seconds) throws AutoModeEndedException {
        runAction(new WaitForCatapultAction(seconds));
    }
    
    /**
     * Run a new WaitForCatapultStateAction.
     */
    public void waitForCatapult(final CatapultState catapultState,
            final double seconds) throws AutoModeEndedException {
        runAction(new WaitForCatapultStateAction(catapultState, seconds));
    }
    
}
