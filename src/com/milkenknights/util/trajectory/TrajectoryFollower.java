package com.milkenknights.util.trajectory;

import edu.wpi.first.wpilibj.Timer;

/**
 * PID + Feedforward controller for following a Trajectory.
 *
 * @author Jared341
 */
public class TrajectoryFollower {
    
    private double kp;
    private double ki;
    private double kd;
    private double kv;
    private double ka;
    private double lastError;
    private double errorSum;
    private boolean reset = true;
    private double lastTimestamp;
    private TrajectorySetpoint nextState = new TrajectorySetpoint();

    private TrajectoryConfig config = new TrajectoryConfig();
    private double goalPosition;
    private TrajectorySetpoint setpoint = new TrajectorySetpoint();
    
    public static class TrajectoryConfig {
        public double dt;
        public double maxAcc;
        public double maxVel;

        @Override
        public String toString() {
            return "dt: " + dt + ", max_acc: " + maxAcc + ", max_vel: "
                    + maxVel;
        }
    }

    public static class TrajectorySetpoint {
        public double pos;
        public double vel;
        public double acc;

        @Override
        public String toString() {
            return "pos: " + pos + ", vel: " + vel + ", acc: " + acc;
        }
    }

    /**
     * Configure the constants for this trajectory.
     */
    public void configure(final double kp, final double ki, final double kd, final double kv, final double ka,
            final TrajectoryConfig config) {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.kv = kv;
        this.ka = ka;
        this.config = config;
    }

    /**
     * Set the goal this trajectory to reach.
     */
    public void setGoal(final TrajectorySetpoint currentState, final double goalPosition) {
        this.goalPosition = goalPosition;
        setpoint = currentState;
        reset = true;
        errorSum = 0.0;
    }

    public double getGoal() {
        return goalPosition;
    }

    public TrajectoryConfig getConfig() {
        return config;
    }

    public void setConfig(final TrajectoryConfig config) {
        this.config = config;
    }

    /**
     * Calculate the PID result for this trajectory.
     */
    public double calculate(final double position, final double velocity) {
        double dt = config.dt;
        if (!reset) {
            final double now = Timer.getFPGATimestamp();
            dt = now - lastTimestamp;
            lastTimestamp = now;
        } else {
            lastTimestamp = Timer.getFPGATimestamp();
        }

        if (isFinishedTrajectory()) {
            setpoint.pos = goalPosition;
            setpoint.vel = 0;
            setpoint.acc = 0;
        } else {
            // Compute the new commanded position, velocity, and acceleration.
            double distanceToGo = goalPosition - setpoint.pos;
            double currentVelocity = setpoint.vel;
            final double cur_vel2 = currentVelocity * currentVelocity;
            boolean inverted = false;
            if (distanceToGo < 0) {
                inverted = true;
                distanceToGo *= -1;
                currentVelocity *= -1;
            }
            // Compute discriminants of the minimum and maximum reachable
            // velocities over the remaining distance.
            final double max_reachable_velocity_disc = cur_vel2 / 2.0 + config.maxAcc * distanceToGo;
            final double min_reachable_velocity_disc = cur_vel2 / 2.0 - config.maxAcc * distanceToGo;
            double cruiseVelocity = currentVelocity;
            if (min_reachable_velocity_disc < 0 || cruiseVelocity < 0) {
                cruiseVelocity = Math.min(config.maxVel,
                        Math.sqrt(max_reachable_velocity_disc));
            }
            final double t_start = (cruiseVelocity - currentVelocity) / config.maxAcc; // Accelerate
            // to
            // cruise_vel
            final double x_start = currentVelocity * t_start + .5 * config.maxAcc * t_start * t_start;
            final double t_end = Math.abs(cruiseVelocity / config.maxAcc); // Decelerate
            // to zero
            // vel.
            final double x_end = cruiseVelocity * t_end - .5 * config.maxAcc * t_end * t_end;
            final double x_cruise = Math.max(0, distanceToGo - x_start - x_end);
            final double t_cruise = Math.abs(x_cruise / cruiseVelocity);
            // Figure out where we should be one dt along this trajectory.
            if (t_start >= dt) {
                nextState.pos = currentVelocity * dt + .5 * config.maxAcc
                        * dt * dt;
                nextState.vel = currentVelocity + config.maxAcc * dt;
                nextState.acc = config.maxAcc;
            } else if (t_start + t_cruise >= dt) {
                nextState.pos = x_start + cruiseVelocity * (dt - t_start);
                nextState.vel = cruiseVelocity;
                nextState.acc = 0;
            } else if (t_start + t_cruise + t_end >= dt) {
                final double delta_t = dt - t_start - t_cruise;
                nextState.pos = x_start + x_cruise + cruiseVelocity * delta_t - .5
                        * config.maxAcc * delta_t * delta_t;
                nextState.vel = cruiseVelocity - config.maxAcc * delta_t;
                nextState.acc = -config.maxAcc;
            } else {
                // Trajectory ends this cycle.
                nextState.pos = distanceToGo;
                nextState.vel = 0;
                nextState.acc = 0;
            }
            if (inverted) {
                nextState.pos *= -1;
                nextState.vel *= -1;
                nextState.acc *= -1;
            }
            setpoint.pos += nextState.pos;
            setpoint.vel = nextState.vel;
            setpoint.acc = nextState.acc;

        }
        final double error = setpoint.pos - position;
        if (reset) {
            // Prevent jump in derivative term when we have been reset.
            reset = false;
            lastError = error;
            errorSum = 0;
        }
        double output = kp * error + kd * ((error - lastError) / dt - setpoint.vel)
                + (kv * setpoint.vel + ka * setpoint.acc);
        if (output < 1.0 && output > -1.0) {
            // Only integrate error if the output isn't already saturated.
            errorSum += error * dt;
        }
        output += ki * errorSum;

        lastError = error;
        return output;
    }

    public boolean isFinishedTrajectory() {
        return Math.abs(setpoint.pos - goalPosition) < 1E-3
                && Math.abs(setpoint.vel) < 1E-2;
    }

    public TrajectorySetpoint getCurrentSetpoint() {
        return setpoint;
    }
}
