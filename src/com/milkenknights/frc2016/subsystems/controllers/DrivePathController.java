package com.milkenknights.frc2016.subsystems.controllers;

import com.milkenknights.frc2016.Constants;
import com.milkenknights.frc2016.subsystems.Drive;
import com.milkenknights.util.MkMath;
import com.milkenknights.util.Pose;
import com.milkenknights.util.drive.MotorPairSignal;
import com.milkenknights.util.trajectory.LegacyTrajectoryFollower;
import com.milkenknights.util.trajectory.Path;
import com.milkenknights.util.trajectory.Trajectory;

/**
 * DrivePathController.java This controller drives the robot along a specified
 * trajectory.
 *
 * @author Tom Bottiglieri
 */
public class DrivePathController implements Drive.DriveController {
    
    protected Trajectory trajectory;
    protected LegacyTrajectoryFollower followerLeft = new LegacyTrajectoryFollower("left");
    protected LegacyTrajectoryFollower followerRight = new LegacyTrajectoryFollower("right");
    protected double direction;
    protected double heading;
    protected double turn = -Constants.kDrivePathHeadingFollowKp;

    public DrivePathController(Path path) {
        init();
        loadProfile(path.getLeftWheelTrajectory(), path.getRightWheelTrajectory(), 1.0, 0.0);
    }

    public boolean isOnTarget() {
        return followerLeft.isFinishedTrajectory();
    }

    private void init() {
        followerLeft.configure(Constants.kDrivePositionKp,
                Constants.kDrivePositionKi, Constants.kDrivePositionKd,
                Constants.kDrivePositionKv, Constants.kDrivePositionKa);
        followerRight.configure(Constants.kDrivePositionKp,
                Constants.kDrivePositionKi, Constants.kDrivePositionKd,
                Constants.kDrivePositionKv, Constants.kDrivePositionKa);
    }

    private void loadProfile(Trajectory leftProfile, Trajectory rightProfile,
                             double direction, double heading) {
        reset();
        followerLeft.setTrajectory(leftProfile);
        followerRight.setTrajectory(rightProfile);
        this.direction = direction;
        this.heading = heading;
    }

    public void loadProfileNoReset(Trajectory leftProfile,
                                   Trajectory rightProfile) {
        followerLeft.setTrajectory(leftProfile);
        followerRight.setTrajectory(rightProfile);
    }

    public void reset() {
        followerLeft.reset();
        followerRight.reset();
    }

    public int getFollowerCurrentSegmentNumber() {
        return followerLeft.getCurrentSegmentNumber();
    }

    public int getNumSegments() {
        return followerLeft.getNumSegments();
    }

    public void setTrajectory(Trajectory t) {
        this.trajectory = t;
    }

    public double getGoal() {
        return 0;
    }

    @Override
    public MotorPairSignal update(Pose pose) {
        if (isOnTarget()) {
            return new MotorPairSignal(0, 0);
        } else {
            double distanceL = direction * pose.getLeftDistance();
            double distanceR = direction * pose.getRightDistance();

            double speedLeft = direction * followerLeft.calculate(distanceL);
            double speedRight = direction * followerRight.calculate(distanceR);

            double goalHeading = followerLeft.getHeading();
            double observedHeading = -pose.getHeading();

            double angleDiffRads = MkMath.getDifferenceInAngleRadians(
                    observedHeading, goalHeading);
            double angleDiff = Math.toDegrees(angleDiffRads);

            double turn = this.turn * angleDiff;
            return new MotorPairSignal(speedLeft + turn, speedRight - turn);
        }
    }

    @Override
    public Pose getCurrentSetpoint() {
        return new Pose(followerLeft.getCurrentSegment().pos, 0, 0, 0, -followerLeft.getHeading(), 0);
    }
}
