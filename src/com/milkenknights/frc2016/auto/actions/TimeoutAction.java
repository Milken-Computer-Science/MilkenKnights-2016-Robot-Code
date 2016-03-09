package com.milkenknights.frc2016.auto.actions;

import edu.wpi.first.wpilibj.Notifier;

/**
 * This Action wait for a specified amount of time.
 */
public class TimeoutAction extends Action implements Runnable {
    
    private final Notifier notifier;
    private final double time;
    private boolean finished;
    
    /**
     * Create a new TimeoutAction.  Will wait until an amount of time has passed and then continue.
     * 
     * @param time The amount of time in seconds to wait
     */
    public TimeoutAction(final double time) {
        super();
        
        notifier = new Notifier(this);
        this.time = time;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void update() {
        // Override me!
    }

    @Override
    public void done() {
        // Override me!
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
