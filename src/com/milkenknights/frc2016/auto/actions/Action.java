package com.milkenknights.frc2016.auto.actions;

public abstract class Action {

    /**
     * Get if the Action has finished.
     */
    public abstract boolean isFinished();

    /**
     * Called at a specified rate while waiting for the Action to complete.
     */
    public abstract void update();

    /**
     * Called after the Action has completed.
     */
    public abstract void done();

    /**
     * Called when the Action is starting.
     */
    public abstract void start();
}
