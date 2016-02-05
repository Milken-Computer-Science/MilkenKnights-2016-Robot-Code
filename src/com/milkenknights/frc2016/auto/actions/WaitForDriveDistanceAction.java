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
    public WaitForDriveDistanceAction(double distance, boolean positive, double timeout) {
        super(timeout);
        this.distance = distance;
        this.positive = positive;
    }

    @Override
    public boolean isFinished() {
        Pose pose = HardwareAdapter.DRIVE.getPhysicalPose();
        double avg = (pose.getLeftDistance() + pose.getRightDistance()) / 2.0;
        return (positive ? avg >= distance : avg <= distance) || super.isFinished();
    }
    
}
