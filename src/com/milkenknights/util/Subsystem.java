package com.milkenknights.util;

/**
 * This is an abstract class extended by Subsystems.
 */
public abstract class Subsystem implements MkSendable {

    private String name;
    
    /**
     * 
     * @param name The name of the subsystem.
     */
    public Subsystem(String name) {
        this.name = name;
    }
    
    /**
     * Returns name of the subsystem.
     */
    public String getName() {
        return name;
    }
    
}
