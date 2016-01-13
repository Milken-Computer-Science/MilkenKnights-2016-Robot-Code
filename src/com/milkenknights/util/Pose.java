package com.milkenknights.util;

public class Pose {

    public double leftDistance;
    public double rightDistance;
    public double leftVelocity;
    public double rightVelocity;
    public double heading;
    public double headingVelocity;
    
    public Pose(double leftDistance, double rightDistance, double leftVelocity, 
            double rightVelocity, double heading, double headingVelocity) {
        this.leftDistance = leftDistance;
        this.rightDistance = rightDistance;
        this.leftVelocity = leftVelocity;
        this.rightVelocity = rightVelocity;
        this.heading = heading;
        this.headingVelocity = headingVelocity;
    }
    
    public void reset(double leftDistance, double rightDistance, double leftVelocity, 
            double rightVelocity, double heading, double headingVelocity) {
        this.leftDistance = leftDistance;
        this.rightDistance = rightDistance;
        this.leftVelocity = leftVelocity;
        this.rightVelocity = rightVelocity;
        this.heading = heading;
        this.headingVelocity = headingVelocity;
    }
    
    /**
     * @return the leftDistance
     */
    public double getLeftDistance() {
        return leftDistance;
    }
    /**
     * @return the rightDistance
     */
    public double getRightDistance() {
        return rightDistance;
    }
    /**
     * @return the leftVelocity
     */
    public double getLeftVelocity() {
        return leftVelocity;
    }
    /**
     * @return the rightVelocity
     */
    public double getRightVelocity() {
        return rightVelocity;
    }
    /**
     * @return the heading
     */
    public double getHeading() {
        return heading;
    }
    /**
     * @return the headingVelocity
     */
    public double getHeadingVelocity() {
        return headingVelocity;
    }
    
    /**
     * Get the relative pose compared to another pose
     * 
     * @param pose the pose
     * @return a pose
     */
    public Pose getRelativePose(Pose pose) {
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
