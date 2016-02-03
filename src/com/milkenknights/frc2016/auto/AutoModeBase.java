package com.milkenknights.frc2016.auto;

import com.milkenknights.frc2016.auto.actions.Action;

import edu.wpi.first.wpilibj.Timer;

public abstract class AutoModeBase implements Runnable {
    
    public static final double UPDATE_RATE = 1.0 / 50.0;
    
    private Thread thread;
    private Timer timer = new Timer();
    private boolean active = false;

    protected abstract void routine() throws AutoModeEndedException;

    /**
     * Run the auto routine.
     */
    public void run() {
        active = true;
        try {
            timer.reset();
            timer.start();
            routine();
        } catch (AutoModeEndedException e) {
            System.out.println("Auto mode done, ended early");
            return;
        }
        System.out.println("Auto mode done");
        System.out.println("Auto time: " + timer.get());
        timer.stop();
    }
    
    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        active = false;
    }

    public boolean isActive() {
        return active;
    }
    
    /**
     * Checks to see if the routine is still running.
     * @return If the routine is active
     * @throws AutoModeEndedException If the routine has ended
     */
    public boolean isActiveWithThrow() throws AutoModeEndedException {
        if (!isActive()) {
            throw new AutoModeEndedException();
        }
        return isActive();
    }

    protected void runAction(Action action) throws AutoModeEndedException {
        isActiveWithThrow();
        action.start();
        while (isActiveWithThrow() && !action.isFinished()) {
            action.update();
            try {
                Thread.sleep((long) (UPDATE_RATE * 1000.0));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        action.done();
    }
}
