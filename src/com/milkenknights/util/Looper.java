package com.milkenknights.util;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A Looper is an easy way to create a timed task the gets
 * called periodically.
 *
 * @author Tom Bottiglieri
 */
public class Looper {

    private double period = 1.0 / 100.0;
    protected Loopable loopable;
    private Timer looperUpdater;
    protected String name;

    /**
     * Create a new Looper.  
     * 
     * @param name The name of this Looper
     * @param loopable The Loopable object to loop with
     * @param period The period that the update method should be called
     */
    public Looper(String name, Loopable loopable, double period) {
        this.period = period;
        this.loopable = loopable;
        this.name = name;
    }

    private class UpdaterTask extends TimerTask {

        private Looper looper;

        public UpdaterTask(Looper looper) {
            if (looper == null) {
                throw new NullPointerException("Given Looper was null");
            }
            this.looper = looper;
        }

        public void run() {
            looper.update();
        }
    }

    /**
     * Start this Looper.
     */
    public void start() {
        if (looperUpdater == null) {
            looperUpdater = new Timer("Looper - " + this.name);
            looperUpdater.schedule(new UpdaterTask(this), 0L, (long) (this.period * 1000));
        }
    }

    /**
     * Stop this Looper.
     */
    public void stop() {
        if (looperUpdater != null) {
            looperUpdater.cancel();
            looperUpdater = null;
        }
    }

    private void update() {
        loopable.update();
    }
}
