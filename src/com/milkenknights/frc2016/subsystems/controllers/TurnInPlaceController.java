package com.milkenknights.frc2016.subsystems.controllers;

import com.milkenknights.frc2016.Constants;
import com.milkenknights.frc2016.subsystems.Drive;
import com.milkenknights.util.Pose;
import com.milkenknights.util.drive.MotorPairSignal;
import com.milkenknights.util.trajectory.TrajectoryFollower;

/**
 * Controls the robot to turn in place.
 */
public class TurnInPlaceController implements Drive.DriveController {
    private final TrajectoryFollowingPositionController controller;
    private final Pose setpointRelativePose;

    /**
     * Create a new TurnInPlaceController.  This will allow the program to calculate the percent to command the motors
     * to turn in place without translating. 
     * 
     * @param poseToContinueFrom The pose to continue from
     * @param destHeading The final heading
     * @param velocity The maximum velocity to travel at in rads/s
     */
    public TurnInPlaceController(final Pose poseToContinueFrom, final double destHeading, final double velocity) {
        final TrajectoryFollower.TrajectoryConfig config = new TrajectoryFollower.TrajectoryConfig();
        config.dt = Constants.kControlLoopsDt;
        config.max_acc = Constants.kTurnMaxAccelRadsPerSec2;
        config.max_vel = velocity;
        controller = new TrajectoryFollowingPositionController(
                Constants.kTurnKp,
                Constants.kTurnKi,
                Constants.kTurnKd,
                Constants.kTurnKv,
                Constants.kTurnKa,
                Constants.kTurnOnTargetError,
                config);
        final TrajectoryFollower.TrajectorySetpoint initialSetpoint = new TrajectoryFollower.TrajectorySetpoint();
        initialSetpoint.pos = poseToContinueFrom.getHeading();
        initialSetpoint.vel = poseToContinueFrom.getHeadingVelocity();
        controller.setGoal(initialSetpoint, destHeading);

        setpointRelativePose = poseToContinueFrom;
    }

    @Override
    public MotorPairSignal update(final Pose pose) {
        controller.update(pose.getHeading(), pose.getHeadingVelocity());
        final double turn = controller.get();
        return new MotorPairSignal(turn, -turn);
    }

    @Override
    public Pose getCurrentSetpoint() {
        final TrajectoryFollower.TrajectorySetpoint setpoint = controller.getSetpoint();
        return new Pose(
                setpointRelativePose.getLeftDistance(),
                setpointRelativePose.getRightDistance(),
                setpointRelativePose.getLeftVelocity(),
                setpointRelativePose.getRightVelocity(),
                setpoint.pos,
                setpoint.vel);
    }

    @Override
    public boolean isOnTarget() {
        return controller.isOnTarget();
    }
    
    public double getError() {
        return controller.get();
    }

    public double getHeadingGoal() {
        return controller.getGoal();
    }
}
