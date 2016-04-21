package com.milkenknights.frc2016.auto.actions;

import com.milkenknights.frc2016.HardwareAdapter;

/**
 * This Action waits for the Catapult to be on target.
 */
public class WaitForCatapultAction extends TimeoutAction {
    
    /**
     * Is finished when the drive passes a certain distance. 
     * 
     * @param timeout When to timeout
     */
    public WaitForCatapultAction(final double timeout) {
        super(timeout);
    }

    @Override
    public boolean isFinished() {
        return HardwareAdapter.CATAPULT.onTarget() || super.isFinished();
    }
    
}
