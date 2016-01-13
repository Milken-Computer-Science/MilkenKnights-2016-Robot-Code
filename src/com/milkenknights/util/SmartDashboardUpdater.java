package com.milkenknights.util;

import java.util.Vector;

public class SmartDashboardUpdater implements Loopable {
    private Looper looper;
    protected Vector<MkSendable> sendables = new Vector<MkSendable>();

    public SmartDashboardUpdater(double period) {
        this("SmartDashboardUpdater", period);
    }
    
    public SmartDashboardUpdater(String name, double period) {
        looper = new Looper(name, this, period);
    }
    /**
     * A function called by Looper or Multilooper.
     */
    public void update() {
        for (MkSendable sendable : sendables) {
            sendable.updateSmartDashboard();
        }
    }

    public void start() {
        looper.start();
    }

    public void stop() {
        looper.stop();
    }

    public void addSendable(MkSendable sendable) {
        sendables.addElement(sendable);
    }

}
