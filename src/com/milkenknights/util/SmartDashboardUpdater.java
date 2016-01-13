package com.milkenknights.util;

import java.util.Vector;

public class SmartDashboardUpdater implements Loopable {
	private Looper looper;
    protected Vector<MKSendable> sendables = new Vector<MKSendable>();

    public SmartDashboardUpdater(double period) {
    	this("SmartDashboardUpdater", period);
    }
    
    public SmartDashboardUpdater(String name, double period) {
        looper = new Looper(name, this, period);
    }

    public void update() {
        for (MKSendable sendable : sendables) {
            sendable.updateSmartDashboard();
        }
    }

    public void start() {
        looper.start();
    }

    public void stop() {
        looper.stop();
    }

    public void addSendable(MKSendable c) {
    	sendables.addElement(c);
    }

}
