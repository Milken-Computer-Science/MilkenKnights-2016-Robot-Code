package com.milkenknights.util.trajectory;

import com.milkenknights.util.trajectory.Trajectory.Segment;

/**
 * Base class for an autonomous path.
 *
 * @author Jared341
 */
public class Path {
    protected Trajectory.Pair go_left_pair_;
    protected String name_;
    protected boolean go_left_;

    public Path(final String name, final Trajectory.Pair go_left_pair) {
        name_ = name;
        go_left_pair_ = go_left_pair;
        go_left_ = true;
    }

    public String getName() {
        return name_;
    }

    public void goLeft() {
        go_left_ = true;
        go_left_pair_.left.setInvertedY(false);
        go_left_pair_.right.setInvertedY(false);
    }

    public void goRight() {
        go_left_ = false;
        go_left_pair_.left.setInvertedY(true);
        go_left_pair_.right.setInvertedY(true);
    }

    public Trajectory getLeftWheelTrajectory() {
        return (go_left_ ? go_left_pair_.left : go_left_pair_.right);
    }

    public Trajectory getRightWheelTrajectory() {
        return (go_left_ ? go_left_pair_.right : go_left_pair_.left);
    }

    public boolean canFlip(final int segmentNum) {
        final Segment a = go_left_pair_.right.getSegment(segmentNum);
        final Segment b = go_left_pair_.left.getSegment(segmentNum);
        return (a.pos == b.pos) && (a.vel == b.vel);
    }

    public double getEndHeading() {
        final int numSegments = getLeftWheelTrajectory().getNumSegments();
        final Segment lastSegment = getLeftWheelTrajectory().getSegment(numSegments - 1);
        return lastSegment.heading;
    }
}
