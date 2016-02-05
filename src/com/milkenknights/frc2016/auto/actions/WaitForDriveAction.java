package com.milkenknights.frc2016.auto.actions;

import com.milkenknights.frc2016.HardwareAdapter;

/**
 * This Action waits for the Drive to be on target.
 */
public class WaitForDriveAction extends TimeoutAction {

    public WaitForDriveAction(double timeout) {
        super(timeout);
    }

    @Override
    public boolean isFinished() {
        return HardwareAdapter.DRIVE.controllerOnTarget() || super.isFinished();
    }
    
}
