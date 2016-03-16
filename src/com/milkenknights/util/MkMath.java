package com.milkenknights.util;

/**
 * This class holds a bunch of static methods and variables needed for
 * mathematics.
 */
public final class MkMath {
    
    public static final double DEGREES_IN_A_CIRCLE = 360.0;
    
    private MkMath() {
        // This is a utility class
    }
    
    /**
     * Get the difference in angle between two angles.
     *
     * @param from The first angle
     * @param to   The second angle
     * @return The change in angle from the first argument necessary to line up
     *      with the second. Always between -Pi and Pi
     */
    public static double getDifferenceInAngleRadians(final double from, final double to) {
        return boundAngleNegPiToPiRadians(to - from);
    }

    /**
     * Get the difference in angle between two angles.
     *
     * @param from The first angle
     * @param to   The second angle
     * @return The change in angle from the first argument necessary to line up
     *      with the second. Always between -180 and 180
     */
    public static double getDifferenceInAngleDegrees(final double from, final double to) {
        return boundAngleNeg180to180Degrees(to - from);
    }

    /**
     * Bounds an angle to between 0 and 360 degrees.
     * @param angle The angle in degrees
     */
    public static double boundAngle0to360Degrees(final double angle) {
        double boundedAngle = angle;
        // Naive algorithm
        while (boundedAngle >= DEGREES_IN_A_CIRCLE) {
            boundedAngle -= DEGREES_IN_A_CIRCLE;
        }
        while (boundedAngle < 0.0) {
            boundedAngle += DEGREES_IN_A_CIRCLE;
        }
        return boundedAngle;
    }

    /**
     * Bounds an angle to between -180 and 180 degrees.
     * @param angle The angle in degrees
     */
    public static double boundAngleNeg180to180Degrees(final double angle) {
        double boundedAngle = angle;
        // Naive algorithm
        while (boundedAngle >= DEGREES_IN_A_CIRCLE / 2.0) {
            boundedAngle -= DEGREES_IN_A_CIRCLE;
        }
        while (boundedAngle < -DEGREES_IN_A_CIRCLE / 2.0) {
            boundedAngle += DEGREES_IN_A_CIRCLE;
        }
        return boundedAngle;
    }

    /**
     * Bounds an angle to between 0pi and 2pi radians.
     * @param angle The angle in radians
     */
    public static double boundAngle0to2PiRadians(final double angle) {
        double boundedAngle = angle;
        // Naive algorithm
        while (boundedAngle >= 2.0 * Math.PI) {
            boundedAngle -= 2.0 * Math.PI;
        }
        while (boundedAngle < 0.0) {
            boundedAngle += 2.0 * Math.PI;
        }
        return boundedAngle;
    }

    /**
     * Bounds an angle to between -pi and pi radians.
     * @param angle The angle in radians
     */
    public static double boundAngleNegPiToPiRadians(final double angle) {
        double boundedAngle = angle;
        // Naive algorithm
        while (boundedAngle >= Math.PI) {
            boundedAngle -= 2.0 * Math.PI;
        }
        while (boundedAngle < -Math.PI) {
            boundedAngle += 2.0 * Math.PI;
        }
        return boundedAngle;
    }

}
