package com.milkenknights.frc2016.auto.actions;

import com.milkenknights.frc2016.HardwareAdapter;

/**
 * This Action waits for the ActionArm to be on target.
 */
public class WaitForActionArmAction extends TimeoutAction {

    public WaitForActionArmAction(final double time) {
        super(time);
    }
    
    @Override
    public boolean isFinished() {
        return HardwareAdapter.ACTION_ARM.isOnTarget() || super.isFinished();
    }

}
