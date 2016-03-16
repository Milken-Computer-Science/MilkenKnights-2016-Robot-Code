package com.milkenknights.frc2016.auto;

import com.milkenknights.frc2016.Constants;
import com.milkenknights.frc2016.auto.actions.Action;

import edu.wpi.first.wpilibj.Timer;

import java.util.concurrent.TimeUnit;


/**
 * Base class for an AutoMode.
 */
public abstract class AutoModeBase implements Runnable {
    
    private Thread thread;
    private final Timer timer = new Timer();
    private boolean active;

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
    
    /**
     * Start running the auto routine.
     */
    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Stop the auto routine.
     */
    public void stop() {
        active = false;
    }

    /**
     * Get if the auto routine is currently running.
     */
    public boolean isActive() {
        return active || thread.isAlive();
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

    /**
     * Run an Action and check at the update rate if it has finished.
     */
    protected void runAction(final Action action) throws AutoModeEndedException {
        isActiveWithThrow();
        action.start();
        while (isActiveWithThrow() && !action.isFinished()) {
            action.update();
            try {
                Thread.sleep((long) (Constants.Auto.ACTION_UPDATE_PERIOD * TimeUnit.SECONDS.toMillis(1)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        action.done();
    }
}
