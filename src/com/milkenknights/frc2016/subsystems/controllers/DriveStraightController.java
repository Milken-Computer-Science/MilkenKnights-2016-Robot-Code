package com.milkenknights.frc2016.subsystems.controllers;

import com.milkenknights.frc2016.Constants;
import com.milkenknights.frc2016.subsystems.Drive;
import com.milkenknights.util.Pose;
import com.milkenknights.util.SynchronousPid;
import com.milkenknights.util.drive.MotorPairSignal;
import com.milkenknights.util.trajectory.TrajectoryFollower;
import com.milkenknights.util.trajectory.TrajectoryFollower.TrajectorySetpoint;

public final class DriveStraightController implements Drive.DriveController {

    private final TrajectoryFollowingPositionController distanceController;
    private final SynchronousPid turnPid;
    private final Pose setpointRelativePose;

    /**
     * Create a new DriveStraightController.  This will allow the program to calculate the PID for driving the robot in
     * a straight line.  Calculates error by taking the average distance from both sides and using a gyro.
     * 
     * @param priorSetpoint The previous pose to continue from
     * @param goalSetpoint The distance setpoint
     * @param maxVelocity The maximum velocity to travel at
     */
    public DriveStraightController(final Pose priorSetpoint, final double goalSetpoint, final double maxVelocity) {
        final TrajectoryFollower.TrajectoryConfig config = new TrajectoryFollower.TrajectoryConfig();
        config.dt = Constants.ControlLoops.CONTROLLERS_PERIOD;
        config.maxAcc = Constants.Subsystems.Drive.MAXIMUM_ACCEL;
        config.maxVel = maxVelocity;

        distanceController = new TrajectoryFollowingPositionController(
                Constants.Subsystems.Drive.POSITION_KP,
                Constants.Subsystems.Drive.POSITION_KI,
                Constants.Subsystems.Drive.POSITION_KD,
                Constants.Subsystems.Drive.POSITION_KV,
                Constants.Subsystems.Drive.POSITION_KA,
                Constants.Subsystems.Drive.POSITION_ALLOWABLE_ERROR,
                config);

        final TrajectorySetpoint initialSetpoint = new TrajectorySetpoint();
        initialSetpoint.pos = encoderDistance(priorSetpoint);
        initialSetpoint.vel = encoderVelocity(priorSetpoint);
        distanceController.setGoal(initialSetpoint, goalSetpoint);

        turnPid = new SynchronousPid();
        turnPid.setPid(
                Constants.Subsystems.Drive.STRAIGHT_KP,
                Constants.Subsystems.Drive.STRAIGHT_KI,
                Constants.Subsystems.Drive.STRAIGHT_KD);
        turnPid.setSetpoint(priorSetpoint.getHeading());
        setpointRelativePose = new Pose(
                priorSetpoint.getLeftDistance(),
                priorSetpoint.getRightDistance(),
                0,
                0,
                priorSetpoint.getHeading(),
                priorSetpoint.getHeadingVelocity());
    }

    @Override
    public MotorPairSignal update(final Pose currentPose) {
        distanceController.update(
                (currentPose.getLeftDistance() + currentPose.getRightDistance()) / 2.0,
                (currentPose.getLeftVelocity() + currentPose.getRightVelocity()) / 2.0);
        final double throttle = distanceController.get();
        final double turn = turnPid.calculate(currentPose.getHeading());
        System.out.println(throttle);
        return new MotorPairSignal(throttle + turn, throttle - turn);
    }

    @Override
    public Pose getCurrentSetpoint() {
        final TrajectorySetpoint trajectorySetpoint = distanceController.getSetpoint();
        final double dist = trajectorySetpoint.pos;
        final double velocity = trajectorySetpoint.vel;
        return new Pose(
                setpointRelativePose.getLeftDistance() + dist,
                setpointRelativePose.getRightDistance() + dist,
                setpointRelativePose.getLeftVelocity() + velocity,
                setpointRelativePose.getRightVelocity() + velocity,
                setpointRelativePose.getHeading(),
                setpointRelativePose.getHeadingVelocity());
    }

    public double encoderVelocity(final Pose pose) {
        return (pose.getLeftVelocity() + pose.getRightVelocity()) / 2.0;
    }

    public double encoderDistance(final Pose pose) {
        return (pose.getLeftDistance() + pose.getRightDistance()) / 2.0;
    }

    @Override
    public boolean isOnTarget() {
        return distanceController.isOnTarget();
    }

    @Override
    public double getError() {
        return distanceController.get();
    }

}
