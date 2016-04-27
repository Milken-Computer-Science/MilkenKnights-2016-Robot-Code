package com.milkenknights.frc2016.auto.actions;

import com.milkenknights.frc2016.HardwareAdapter;

/**
 * This Action waits for the Drive to stop moving.
 */
public class WaitForDriveNotMovingAction extends TimeoutAction {

    /**
     * Is finished when the drive stops moving. 
     *
     * @param timeout When to timeout
     */
    public WaitForDriveNotMovingAction(final double timeout) {
        super(timeout);
    }

    @Override
    public boolean isFinished() {
        return HardwareAdapter.DRIVE.isMoving() || super.isFinished();
    }
    
}
