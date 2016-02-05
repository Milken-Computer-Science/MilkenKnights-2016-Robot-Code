package com.milkenknights.frc2016.auto.actions;

import edu.wpi.first.wpilibj.Notifier;

/**
 * This Action wait for a specified amount of time.
 */
public class TimeoutAction extends Action implements Runnable {
    
    private Notifier notifier;
    private double time;
    private boolean finished = false;
    
    public TimeoutAction(double time) {
        notifier = new Notifier(this);
        this.time = time;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void update() {

    }

    @Override
    public void done() {

    }

    @Override
    public void start() {
        notifier.startSingle(time);
    }

    @Override
    public void run() {
        finished = true;
    }

}
