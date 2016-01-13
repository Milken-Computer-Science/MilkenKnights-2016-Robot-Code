package com.milkenknights.util;

import java.util.Vector;

/**
 * Runs several Loopables simultaneously with one Looper.
 * Useful for running a bunch of control loops
 * with only one Thread worth of overhead.
 *
 * @author Tom Bottiglieri
 */
public class MultiLooper implements Loopable {
    private Looper looper;
    private Vector<Loopable> loopables = new Vector<Loopable>();

    public MultiLooper(String name, double period) {
        looper = new Looper(name, this, period);
    }

    public void update() {
        for (Loopable loopable : loopables) {
            loopable.update();
        }
    }

    public void start() {
        looper.start();
    }

    public void stop() {
        looper.stop();
    }

    public void addLoopable(Loopable c) {
        loopables.addElement(c);
    }
}
