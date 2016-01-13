package com.milkenknights.util;

public abstract class Drive extends Subsystem implements Loopable {

    private MotorPairSignal signal = new MotorPairSignal(0, 0);
    private DriveController controller;

    public Drive(String name) {
        super(name);
    }

    public interface DriveController {
        MotorPairSignal update(Pose pose);

        Pose getCurrentSetpoint();

        public boolean isOnTarget();
    }

    public abstract void setOpenLoop(MotorPairSignal signal);

    public abstract void setDistanceSetpoint(double distance);

    public abstract void setDistanceSetpoint(double distance, double velocity);

    public abstract void setTurnSetpoint(double heading);

    public abstract void setTurnSetpoint(double heading, double velocity);

    public abstract void reset();

    public abstract Pose getPhysicalPose();

    public boolean controllerOnTarget() {
        return controller != null && controller.isOnTarget();
    }

}
