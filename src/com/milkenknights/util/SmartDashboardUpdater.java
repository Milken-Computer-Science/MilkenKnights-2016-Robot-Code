package com.milkenknights.util;

import java.util.ArrayList;
import java.util.List;

public class SmartDashboardUpdater implements Loopable {
    private final Looper looper;
    private final List<MkSendable> sendables = new ArrayList<MkSendable>();

    public SmartDashboardUpdater(final double period) {
        this("SmartDashboardUpdater", period);
    }
    
    public SmartDashboardUpdater(final String name, final double period) {
        looper = new Looper(name, this, period);
    }
    
    /**
     * A function called by Looper or Multilooper.
     */
    public void update() {
        for (final MkSendable sendable : sendables) {
            sendable.updateSmartDashboard();
        }
    }

    public void start() {
        looper.start();
    }

    public void stop() {
        looper.stop();
    }

    public void addSendable(final MkSendable sendable) {
        sendables.add(sendable);
    }

}
