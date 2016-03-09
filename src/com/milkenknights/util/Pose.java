package com.milkenknights.util;

public class Pose {

    public double leftDistance;
    public double rightDistance;
    public double leftVelocity;
    public double rightVelocity;
    public double heading;
    public double headingVelocity;
    
    /**
     * Inputs double variables for Distance, Velocity, headingVelocity for both sides of the Robot.
     * 
     * @param leftDistance
     * The distance the left encoder has traveled 
     * 
     * @param rightDistance
     * The distance the left encoder has traveled.
     * 
     * @param leftVelocity
     * The velocity of the left encoder.
     * 
     * @param rightVelocity
     * The velocity of the right encoder.
     * 
     * @param heading
     * The heading of the robot.
     * 
     * @param headingVelocity
     * The rate at which the robot is turning.
     */
    public Pose(final double leftDistance, final double rightDistance, final double leftVelocity,
            final double rightVelocity, final double heading, final double headingVelocity) {
        this.leftDistance = leftDistance;
        this.rightDistance = rightDistance;
        this.leftVelocity = leftVelocity;
        this.rightVelocity = rightVelocity;
        this.heading = heading;
        this.headingVelocity = headingVelocity;
    }
    
    /**
     * Inputs double variables for Distance, Velocity, headingVelocity for both sides of the Robot.
     * 
     * @param leftDistance
     * The distance the left encoder has traveled.
     * 
     * @param rightDistance
     * The distance the left encoder has traveled.
     * 
     * @param leftVelocity
     * The velocity of the left encoder.
     * 
     * @param rightVelocity
     * The velocity of the right encoder.
     * 
     * @param heading
     * The heading of the robot.
     * 
     * @param headingVelocity
     * The rate at which the robot is turning.
     */
    public void reset(final double leftDistance, final double rightDistance, final double leftVelocity,
            final double rightVelocity, final double heading, final double headingVelocity) {
        this.leftDistance = leftDistance;
        this.rightDistance = rightDistance;
        this.leftVelocity = leftVelocity;
        this.rightVelocity = rightVelocity;
        this.heading = heading;
        this.headingVelocity = headingVelocity;
    }
    
    /**
     * Returns the distance of the left side of the Robot.
     * 
     * @return the leftDistance.
     */
    public double getLeftDistance() {
        return leftDistance;
    }
    
    /**
     * Returns the distance of the right side of the Robot.
     * 
     * @return the rightDistance.
     */
    public double getRightDistance() {
        return rightDistance;
    }
    
    /**
     * Returns the velocity of the left side of the Robot.
     * 
     * @return the leftVelocity.
     */
    public double getLeftVelocity() {
        return leftVelocity;
    }
    
    /**
     * Return the velocity of the right side of the Robot.
     * 
     * @return the rightVelocity
     */
    public double getRightVelocity() {
        return rightVelocity;
    }
    
    /**
     * Returns the heading(Direction) of the Robot.
     * 
     * @return the heading
     */
    public double getHeading() {
        return heading;
    }
    
    /**
     * Return headingVelocity.
     * @return the headingVelocity
     */
    public double getHeadingVelocity() {
        return headingVelocity;
    }
    
    /**
     * Get the relative pose compared to another pose.
     * 
     * @param pose the pose
     * @return a pose
     */
    public Pose getRelativePose(final Pose pose) {
        return new Pose(
                this.getLeftDistance() - pose.getLeftDistance(),
                this.getRightDistance() - pose.getRightDistance(),
                this.getLeftVelocity() - pose.getLeftVelocity(),
                this.getRightVelocity() - pose.getRightVelocity(),
                this.heading - pose.heading,
                this.getHeadingVelocity() - pose.getHeadingVelocity()
                );
    }
    
}
