package com.milkenknights.frc2016.auto.actions;

import com.milkenknights.frc2016.HardwareAdapter;

/**
 * This Action waits for the intake arm to be on target.
 */
public class WaitForIntakeArmAction extends TimeoutAction {

    public WaitForIntakeArmAction(final double time) {
        super(time);
    }
    
    @Override
    public boolean isFinished() {
        return HardwareAdapter.INTAKE_ARM.isOnTarget() || super.isFinished();
    }

}
