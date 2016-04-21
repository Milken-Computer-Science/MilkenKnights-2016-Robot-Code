package com.milkenknights.frc2016.auto.actions;

import com.milkenknights.frc2016.HardwareAdapter;
import com.milkenknights.frc2016.subsystems.Catapult.CatapultState;

/**
 * This Action waits for the Catapult to enter a certain state.
 */
public class WaitForCatapultStateAction extends TimeoutAction {

    private final CatapultState catapultState;
    
    /**
     * Is finished when the drive passes a certain distance. 
     * 
     * @param catapultState The state the catapult should enter.
     * @param timeout When to timeout
     */
    public WaitForCatapultStateAction(final CatapultState catapultState, final double timeout) {
        super(timeout);
        this.catapultState = catapultState;
    }

    @Override
    public boolean isFinished() {
        return HardwareAdapter.CATAPULT.getState() == catapultState || super.isFinished();
    }
    
}
