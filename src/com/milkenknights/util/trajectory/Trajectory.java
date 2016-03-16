package com.milkenknights.util.trajectory;

import com.milkenknights.util.MkMath;

/**
 * Implementation of a Trajectory using arrays as the underlying storage
 * mechanism.
 *
 * @author Jared341
 */
public class Trajectory {
    
    private static final String TAB = "\t";
    private static final String NEWLINE = "\n";
    
    private Segment[] segments;
    private boolean invertedY;

    public static class Pair {

        public Trajectory left;
        public Trajectory right;
        
        public Pair(final Trajectory left, final Trajectory right) {
            this.left = left;
            this.right = right;
        }
    }

    public static class Segment {

        public double pos;
        public double vel;
        public double acc;
        public double jerk;
        public double heading;
        public double dt;
        public double posX;
        public double posY;

        /**
         * Create a new path segment.
         */
        public Segment(final double pos, final double vel, final double acc, final double jerk, final double heading,
                final double dt, final double posX, final double posY) {
            this.pos = pos;
            this.vel = vel;
            this.acc = acc;
            this.jerk = jerk;
            this.heading = heading;
            this.dt = dt;
            this.posX = posX;
            this.posY = posY;
        }

        /**
         * Copy an existing segment.
         */
        public Segment(final Segment toCopy) {
            pos = toCopy.pos;
            vel = toCopy.vel;
            acc = toCopy.acc;
            jerk = toCopy.jerk;
            heading = toCopy.heading;
            dt = toCopy.dt;
            posX = toCopy.posX;
            posY = toCopy.posY;
        }
        
        public Segment() {
            // Default
        }

        public String toString() {
            return "pos: " + pos + "; vel: " + vel + "; acc: " + acc + "; jerk: "
                    + jerk + "; heading: " + heading;
        }
    }

    /**
     * Create a new Trajectory.
     */
    public Trajectory(final int length) {
        segments = new Segment[length];
        for (int i = 0; i < length; i++) {
            segments[i] = new Segment();
        }
    }

    public Trajectory(final Segment[] segments) {
        this.segments = segments;
    }

    public void setInvertedY(final boolean inverted) {
        invertedY = inverted;
    }

    public int getNumSegments() {
        return segments.length;
    }

    /**
     * Get the segment at the provided index.
     */
    public Segment getSegment(final int index) {
        Segment segment;
        if (index < getNumSegments()) {
            if (!invertedY) {
                segment = segments[index];
            } else {
                Segment seg = new Segment(segments[index]);
                seg.posY *= -1.0;
                seg.heading = MkMath.boundAngle0to2PiRadians(2 * Math.PI - seg.heading);
                segment = seg;
            }
        } else {
            segment = new Segment();
        }
        return segment;
    }

    /**
     * Set a segment in the array of segments. 
     */
    public void setSegment(final int index, final Segment segment) {
        if (index < getNumSegments()) {
            segments[index] = segment;
        }
    }

    /**
     * Scale the fields of every segment.
     */
    public void scale(final double scalingFactor) {
        for (int i = 0; i < getNumSegments(); i++) {
            segments[i].pos *= scalingFactor;
            segments[i].vel *= scalingFactor;
            segments[i].acc *= scalingFactor;
            segments[i].jerk *= scalingFactor;
        }
    }

    /**
     * Append another Trajectory to this Trajectory. 
     */
    public void append(final Trajectory toAppend) {
        Segment[] temp = new Segment[getNumSegments() + toAppend.getNumSegments()];

        for (int i = 0; i < getNumSegments(); i++) {
            temp[i] = new Segment(segments[i]);
        }
        
        for (int i = 0; i < toAppend.getNumSegments(); i++) {
            temp[i + getNumSegments()] = new Segment(toAppend.getSegment(i));
        }

        this.segments = temp;
    }

    /**
     * Copy this Trajectory.
     */
    public Trajectory copy() {
        final Trajectory cloned = new Trajectory(getNumSegments());
        cloned.segments = copySegments(this.segments);
        return cloned;
    }

    private Segment[] copySegments(final Segment[] toCopy) {
        Segment[] copied = new Segment[toCopy.length];
        for (int i = 0; i < toCopy.length; i++) {
            copied[i] = new Segment(toCopy[i]);
        }
        return copied;
    }

    @Override
    public String toString() {
        String str = "Segment\tPos\tVel\tAcc\tJerk\tHeading\n";
        for (int i = 0; i < getNumSegments(); i++) {
            final Trajectory.Segment segment = getSegment(i);
            str += i + TAB;
            str += segment.pos + TAB;
            str += segment.vel + TAB;
            str += segment.acc + TAB;
            str += segment.jerk + TAB;
            str += segment.heading + TAB;
            str += NEWLINE;
        }

        return str;
    }

    public String toStringProfile() {
        return toString();
    }

    /**
     * The toString method but in Euclidean.
     */
    public String toStringEuclidean() {
        String str = "Segment\tx\ty\tHeading\n";
        for (int i = 0; i < getNumSegments(); i++) {
            final Trajectory.Segment segment = getSegment(i);
            str += i + TAB;
            str += segment.posX + TAB;
            str += segment.posY + TAB;
            str += segment.heading + TAB;
            str += NEWLINE;
        }

        return str;
    }
}
