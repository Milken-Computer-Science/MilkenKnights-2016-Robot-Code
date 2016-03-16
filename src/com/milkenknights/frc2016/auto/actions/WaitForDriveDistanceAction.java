package com.milkenknights.frc2016.auto.actions;

import com.milkenknights.frc2016.HardwareAdapter;
import com.milkenknights.util.Pose;

/**
 * This Action waits for the Drive to reach a specified distance.
 */
public class WaitForDriveDistanceAction extends TimeoutAction {

    public double distance;
    public boolean positive;

    /**
     * Is finished when the drive passes a certain distance. 
     * 
     * @param distance The distance to pass
     * @param positive If the robot is moving forward or backward
     * @param timeout When to timeout
     */
    public WaitForDriveDistanceAction(final double distance, final boolean positive, final double timeout) {
        super(timeout);
        this.distance = distance;
        this.positive = positive;
    }

    @Override
    public boolean isFinished() {
        final Pose pose = HardwareAdapter.DRIVE.getPhysicalPose();
        final double avg = (pose.getLeftDistance() + pose.getRightDistance()) / 2.0;
        return positive ? avg >= distance : avg <= distance || super.isFinished();
    }
    
}
