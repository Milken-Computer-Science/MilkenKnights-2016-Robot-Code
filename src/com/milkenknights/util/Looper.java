package com.milkenknights.util;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * A Looper is an easy way to create a timed task the gets
 * called periodically.
 *
 * @author Tom Bottiglieri
 */
public class Looper {

    private final double period;
    private final Loopable loopable;
    private final String name;
    private Timer looperUpdater;

    /**
     * Create a new Looper.  
     * 
     * @param name The name of this Looper
     * @param loopable The Loopable object to loop with
     * @param period The period that the update method should be called
     */
    public Looper(final String name, final Loopable loopable, final double period) {
        this.period = period;
        this.loopable = loopable;
        this.name = name;
    }

    private class UpdaterTask extends TimerTask {

        private final Looper looper;

        public UpdaterTask(final Looper looper) {
            super();
            
            if (!(looper instanceof Looper)) {
                throw new IllegalArgumentException("Given Looper was not a looper");
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
            looperUpdater.schedule(new UpdaterTask(this), 0L, (long) (this.period * TimeUnit.SECONDS.toMillis(1)));
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

    /**
     * The method called every period.
     */
    private void update() {
        loopable.update();
    }
}
