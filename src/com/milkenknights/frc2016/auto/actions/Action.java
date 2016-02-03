package com.milkenknights.frc2016.auto.actions;

public abstract class Action {

    public abstract boolean isFinished();

    public abstract void update();

    public abstract void done();

    public abstract void start();
}
