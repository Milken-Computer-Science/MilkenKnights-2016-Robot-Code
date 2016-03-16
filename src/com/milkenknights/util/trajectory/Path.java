package com.milkenknights.util.trajectory;

import com.milkenknights.util.trajectory.Trajectory.Segment;

/**
 * Base class for an autonomous path.
 *
 * @author Jared341
 */
public class Path {
    protected final Trajectory.Pair goLeftPair;
    protected final String name;
    protected boolean isGoLeft;

    /**
     * Create a new path.
     */
    public Path(final String name, final Trajectory.Pair goLeftPair) {
        this.name = name;
        this.goLeftPair = goLeftPair;
        this.isGoLeft = true;
    }

    public String getName() {
        return name;
    }

    /**
     * Command the Trajectory to go left.
     */
    public void goLeft() {
        isGoLeft = true;
        goLeftPair.left.setInvertedY(false);
        goLeftPair.right.setInvertedY(false);
    }

    /**
     * Command the Trajectory to go right.
     */
    public void goRight() {
        isGoLeft = false;
        goLeftPair.left.setInvertedY(true);
        goLeftPair.right.setInvertedY(true);
    }

    public Trajectory getLeftWheelTrajectory() {
        return isGoLeft ? goLeftPair.left : goLeftPair.right;
    }

    public Trajectory getRightWheelTrajectory() {
        return isGoLeft ? goLeftPair.right : goLeftPair.left;
    }

    /**
     * Returns if a segment can be fliped.
     */
    public boolean canFlip(final int segmentNum) {
        final Segment a = goLeftPair.right.getSegment(segmentNum);
        final Segment b = goLeftPair.left.getSegment(segmentNum);
        return a.pos == b.pos && a.vel == b.vel;
    }

    /**
     * Get the final heading.
     */
    public double getEndHeading() {
        final int numSegments = getLeftWheelTrajectory().getNumSegments();
        final Segment lastSegment = getLeftWheelTrajectory().getSegment(numSegments - 1);
        return lastSegment.heading;
    }
}
