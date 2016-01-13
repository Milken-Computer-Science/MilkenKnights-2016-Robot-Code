package com.milkenknights.util;

/**
 * A Loopable interface for classes that can be added to a Looper or Multilooper.
 */
public interface Loopable {

    /**
     * A function called by Looper or Multilooper.
     */
    void update();

}
