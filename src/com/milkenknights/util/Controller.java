package com.milkenknights.util;

public abstract class Controller {
    protected boolean enabled;

    public abstract void reset();

    public abstract boolean isOnTarget();
}
