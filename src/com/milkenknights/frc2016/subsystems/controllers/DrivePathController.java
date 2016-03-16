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
public final class DrivePathController implements Drive.DriveController {
    
    protected Trajectory trajectory;
    protected LegacyTrajectoryFollower followerLeft = new LegacyTrajectoryFollower("left");
    protected LegacyTrajectoryFollower followerRight = new LegacyTrajectoryFollower("right");
    protected double direction;
    protected double heading;
    protected double turn = -Constants.kDrivePathHeadingFollowKp;

    public DrivePathController(final Path path) {
        init();
        loadProfile(path.getLeftWheelTrajectory(), path.getRightWheelTrajectory(), 1.0, 0.0);
    }

    public boolean isOnTarget() {
        return followerLeft.isFinishedTrajectory();
    }

    private void init() {
        followerLeft.configure(Constants.kDrivePositionKp, Constants.kDrivePositionKi, Constants.kDrivePositionKd,
                Constants.kDrivePositionKv, Constants.kDrivePositionKa);
        followerRight.configure(Constants.kDrivePositionKp, Constants.kDrivePositionKi, Constants.kDrivePositionKd,
                Constants.kDrivePositionKv, Constants.kDrivePositionKa);
    }

    private void loadProfile(final Trajectory leftProfile, final Trajectory rightProfile, final double direction,
            final double heading) {
        reset();
        followerLeft.setTrajectory(leftProfile);
        followerRight.setTrajectory(rightProfile);
        this.direction = direction;
        this.heading = heading;
    }

    public void loadProfileNoReset(final Trajectory leftProfile, final Trajectory rightProfile) {
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

    public void setTrajectory(final Trajectory trajectory) {
        this.trajectory = trajectory;
    }

    @Override
    public MotorPairSignal update(final Pose pose) {
        MotorPairSignal signal = MotorPairSignal.NEUTRAL;
        if (!isOnTarget()) {
            final double distanceL = direction * pose.getLeftDistance();
            final double distanceR = direction * pose.getRightDistance();

            final double speedLeft = direction * followerLeft.calculate(distanceL);
            final double speedRight = direction * followerRight.calculate(distanceR);

            final double goalHeading = followerLeft.getHeading();
            final double observedHeading = -pose.getHeading();

            final double angleDiffRads = MkMath.getDifferenceInAngleRadians(
                    observedHeading, goalHeading);
            final double angleDiff = Math.toDegrees(angleDiffRads);

            final double turn = this.turn * angleDiff;
            signal = new MotorPairSignal(speedLeft + turn, speedRight - turn);
        }
        return signal;
    }

    @Override
    public Pose getCurrentSetpoint() {
        return new Pose(followerLeft.getCurrentSegment().pos, 0, 0, 0, -followerLeft.getHeading(), 0);
    }

    @Override
    public double getError() {
        return 0;
    }
}
