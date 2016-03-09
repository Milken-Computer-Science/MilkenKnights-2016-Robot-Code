package com.milkenknights.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Runs several Loopables simultaneously with one Looper.
 * Useful for running a bunch of control loops
 * with only one Thread worth of overhead.
 *
 * @author Tom Bottiglieri
 */
public class MultiLooper implements Loopable {
    private final Looper looper;
    private final List<Loopable> loopables = new ArrayList<Loopable>();

    public MultiLooper(final String name, final double period) {
        looper = new Looper(name, this, period);
    }

    /**
     * Method to be called every period.
     */
    public void update() {
        for (final Loopable loopable : loopables) {
            loopable.update();
        }
    }

    public void start() {
        looper.start();
    }

    public void stop() {
        looper.stop();
    }

    public void addLoopable(final Loopable loopable) {
        loopables.add(loopable);
    }
}
