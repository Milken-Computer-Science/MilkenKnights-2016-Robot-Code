package com.milkenknights.frc2016.auto.actions;

import com.milkenknights.frc2016.HardwareAdapter;
import com.milkenknights.frc2016.subsystems.Catapult.CatapultState;

/**
 * This Action waits for the Catapult to enter a certain state.
 */
public class WaitForCatapultAction extends TimeoutAction {

    private final CatapultState catapultState;
    
    /**
     * Is finished when the drive passes a certain distance. 
     * 
     * @param firstPitch The pitch to start at
     * @param secondPitch The pitch to end at
     * @param timeout When to timeout
     */
    public WaitForCatapultAction(final CatapultState catapultState, final double timeout) {
        super(timeout);
        this.catapultState = catapultState;
    }

    @Override
    public boolean isFinished() {
        return HardwareAdapter.CATAPULT.getState() == catapultState;
    }
    
}
