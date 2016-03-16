package com.milkenknights.frc2016.auto.actions;

import com.milkenknights.frc2016.HardwareAdapter;
import com.milkenknights.util.drive.MotorPairSignal;

public class TimedDriveAction extends TimeoutAction {

    private final double speed;

    public TimedDriveAction(final double time, final double speed) {
        super(time);
        this.speed = speed;
    }

    @Override
    public void update() {
        HardwareAdapter.DRIVE.setOpenLoop(new MotorPairSignal(speed, speed));
    }

    @Override
    public void done() {
        HardwareAdapter.DRIVE.setOpenLoop(MotorPairSignal.NEUTRAL);
    }
}
