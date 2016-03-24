package com.milkenknights.frc2016.subsystems.controllers;

import com.milkenknights.frc2016.Constants;
import com.milkenknights.frc2016.subsystems.Drive;
import com.milkenknights.util.Pose;
import com.milkenknights.util.SynchronousPid;
import com.milkenknights.util.drive.MotorPairSignal;

/**
 * Controls the robot to turn in place.
 */
public class TurnInPlaceController implements Drive.DriveController {
    private final SynchronousPid pid;

    /**
     * Create a new TurnInPlaceController.  This will allow the program to calculate the percent to command the motors
     * to turn in place without translating. 
     * 
     * @param destHeading The final heading
     */
    public TurnInPlaceController(final double destHeading) {
        pid = new SynchronousPid();
        pid.setPid(Constants.Subsystems.Drive.TURN_KP,
                Constants.Subsystems.Drive.TURN_KI,
                Constants.Subsystems.Drive.TURN_KD);
        pid.setOutputRange(-Constants.Subsystems.Drive.MAXIMUM_SPEED, Constants.Subsystems.Drive.MAXIMUM_SPEED);
        pid.setSetpoint(destHeading);
    }

    @Override
    public MotorPairSignal update(final Pose pose) {
        final double turn = pid.calculate(pose.heading);
        return new MotorPairSignal(turn, -turn);
    }

    @Override
    public Pose getCurrentSetpoint() {
        return null;
    }

    @Override
    public boolean isOnTarget() {
        return pid.onTarget(Constants.Subsystems.Drive.TURN_ALLOWABLE_ERROR);
    }
    
    public double getError() {
        return pid.getError();
    }

    public double getHeadingGoal() {
        return pid.getSetpoint();
    }
}
