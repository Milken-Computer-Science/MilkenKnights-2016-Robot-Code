package com.milkenknights.util.trajectory;

/**
 * PID + Feedforward controller for following a Trajectory.
 *
 * @author Jared341
 */
public class LegacyTrajectoryFollower {

    public final String name;
    
    private double kp;
    private double kd;
    private double kv;
    private double ka;
    private double lastError;
    private double currentHeading;
    private int currentSegment;
    private Trajectory profile;

    public LegacyTrajectoryFollower(final String name) {
        this.name = name;
    }

    /**
     * Configure the constants for the Trajectory.
     */
    public void configure(final double kp, final double kd, final double kv, final double ka) {
        this.kp = kp;
        this.kd = kd;
        this.kv = kv;
        this.ka = ka;
    }

    public void reset() {
        lastError = 0.0;
        currentSegment = 0;
    }

    public void setTrajectory(final Trajectory profile) {
        this.profile = profile;
    }

    /**
     * Calculate controller result.
     */
    public double calculate(final double distanceSoFar) {
        double output = 0;
        if (currentSegment < profile.getNumSegments()) {
            final Trajectory.Segment segment = profile.getSegment(currentSegment);
            final double error = segment.pos - distanceSoFar;
            output = kp * error + kd * (error - lastError) / segment.dt - segment.vel + (kv * segment.vel
                    + ka * segment.acc);

            lastError = error;
            currentHeading = segment.heading;
            currentSegment++;
        }
        return output;
    }

    public double getHeading() {
        return currentHeading;
    }

    public boolean isFinishedTrajectory() {
        return currentSegment >= profile.getNumSegments();
    }

    public Trajectory.Segment getCurrentSegment() {
        return profile.getSegment(currentSegment);
    }

    public int getCurrentSegmentNumber() {
        return currentSegment;
    }

    public int getNumSegments() {
        return profile.getNumSegments();
    }
}
