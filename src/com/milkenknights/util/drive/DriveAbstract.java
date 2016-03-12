package com.milkenknights.util.drive;

import com.milkenknights.util.Loopable;
import com.milkenknights.util.Pose;
import com.milkenknights.util.Subsystem;

public abstract class DriveAbstract extends Subsystem implements Loopable {

    private DriveController controller;

    public DriveAbstract(final String name) {
        super(name);
    }

    public interface DriveController {
        MotorPairSignal update(Pose pose);

        Pose getCurrentSetpoint();

        public boolean isOnTarget();
        
        public double getError();
    }

    public abstract void setOpenLoop(MotorPairSignal signal);

    public abstract void setDistanceSetpoint(double distance);

    public abstract void setDistanceSetpoint(double distance, double velocity);

    public abstract void setTurnSetpoint(double heading);

    public abstract void setTurnSetpoint(double heading, double velocity);

    public abstract void reset();

    public abstract Pose getPhysicalPose();

    public final boolean controllerOnTarget() {
        return controller != null && controller.isOnTarget();
    }

}
