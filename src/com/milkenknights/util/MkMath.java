package com.milkenknights.util;

/**
 * This class holds a bunch of static methods and variables needed for
 * mathematics.
 */
public class MkMath {
    /**
     * Get the difference in angle between two angles.
     *
     * @param from The first angle
     * @param to   The second angle
     * @return The change in angle from the first argument necessary to line up
     *      with the second. Always between -Pi and Pi
     */
    public static double getDifferenceInAngleRadians(double from, double to) {
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
    public static double getDifferenceInAngleDegrees(double from, double to) {
        return boundAngleNeg180to180Degrees(to - from);
    }

    /**
     * Bounds an angle to between 0 and 360 degrees.
     * @param angle The angle in degrees
     */
    public static double boundAngle0to360Degrees(double angle) {
        // Naive algorithm
        while (angle >= 360.0) {
            angle -= 360.0;
        }
        while (angle < 0.0) {
            angle += 360.0;
        }
        return angle;
    }

    /**
     * Bounds an angle to between -180 and 180 degrees.
     * @param angle The angle in degrees
     */
    public static double boundAngleNeg180to180Degrees(double angle) {
        // Naive algorithm
        while (angle >= 180.0) {
            angle -= 360.0;
        }
        while (angle < -180.0) {
            angle += 360.0;
        }
        return angle;
    }

    /**
     * Bounds an angle to between 0pi and 2pi radians.
     * @param angle The angle in radians
     */
    public static double boundAngle0to2PiRadians(double angle) {
        // Naive algorithm
        while (angle >= 2.0 * Math.PI) {
            angle -= 2.0 * Math.PI;
        }
        while (angle < 0.0) {
            angle += 2.0 * Math.PI;
        }
        return angle;
    }

    /**
     * Bounds an angle to between -pi and pi radians.
     * @param angle The angle in radians
     */
    public static double boundAngleNegPiToPiRadians(double angle) {
        // Naive algorithm
        while (angle >= Math.PI) {
            angle -= 2.0 * Math.PI;
        }
        while (angle < -Math.PI) {
            angle += 2.0 * Math.PI;
        }
        return angle;
    }

    public MkMath() {
        
    }
}
