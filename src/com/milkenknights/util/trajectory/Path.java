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
    protected boolean goLeft;

    public Path(final String name, final Trajectory.Pair goLeftPair) {
        this.name = name;
        this.goLeftPair = goLeftPair;
        this.goLeft = true;
    }

    public String getName() {
        return name;
    }

    public void goLeft() {
        goLeft = true;
        goLeftPair.left.setInvertedY(false);
        goLeftPair.right.setInvertedY(false);
    }

    public void goRight() {
        goLeft = false;
        goLeftPair.left.setInvertedY(true);
        goLeftPair.right.setInvertedY(true);
    }

    public Trajectory getLeftWheelTrajectory() {
        return goLeft ? goLeftPair.left : goLeftPair.right;
    }

    public Trajectory getRightWheelTrajectory() {
        return goLeft ? goLeftPair.right : goLeftPair.left;
    }

    public boolean canFlip(final int segmentNum) {
        final Segment a = goLeftPair.right.getSegment(segmentNum);
        final Segment b = goLeftPair.left.getSegment(segmentNum);
        return a.pos == b.pos && a.vel == b.vel;
    }

    public double getEndHeading() {
        final int numSegments = getLeftWheelTrajectory().getNumSegments();
        final Segment lastSegment = getLeftWheelTrajectory().getSegment(numSegments - 1);
        return lastSegment.heading;
    }
}
