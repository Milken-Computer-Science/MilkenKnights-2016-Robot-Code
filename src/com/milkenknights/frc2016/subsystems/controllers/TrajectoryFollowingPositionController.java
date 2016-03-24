package com.milkenknights.frc2016.subsystems.controllers;

import com.milkenknights.util.Controller;
import com.milkenknights.util.trajectory.TrajectoryFollower;

public class TrajectoryFollowingPositionController extends Controller {
    private final TrajectoryFollower follower;
    private double goal;
    private double error;
    private final double onTargetDelta;
    private double result;

    /**
     * Create a new TrajectoryFollowingPositionController.
     * 
     * @param kp The proportional constant
     * @param ki The integral constant
     * @param kd The derivative constant
     * @param kv The velocity constant
     * @param ka The acceleration constants
     * @param onTargetDelta The distance to go
     * @param config Configuration for the controller
     */
    public TrajectoryFollowingPositionController(final double kp, final double ki, final double kd, final double kv,
            final double ka, final double onTargetDelta, final TrajectoryFollower.TrajectoryConfig config) {
        super();
        
        follower = new TrajectoryFollower();
        follower.configure(kp, ki, kd, kv, ka, config);
        this.onTargetDelta = onTargetDelta;
    }

    public void setGoal(final TrajectoryFollower.TrajectorySetpoint currentState, final double goal) {
        this.goal = goal;
        follower.setGoal(currentState, goal);
    }

    public double getGoal() {
        return follower.getGoal();
    }

    public void setConfig(final TrajectoryFollower.TrajectoryConfig config) {
        follower.setConfig(config);
    }

    public TrajectoryFollower.TrajectoryConfig getConfig() {
        return follower.getConfig();
    }

    public void update(final double position, final double velocity) {
        error = goal - position;
        result = follower.calculate(position, velocity);
    }

    public TrajectoryFollower.TrajectorySetpoint getSetpoint() {
        return follower.getCurrentSetpoint();
    }

    public double get() {
        return result;
    }

    public boolean isFinishedTrajectory() {
        return follower.isFinishedTrajectory();
    }
    
    public double getError() {
        return error;
    }

    @Override
    public boolean isOnTarget() {
        return follower.isFinishedTrajectory() && Math.abs(error) < onTargetDelta;
    }

}
