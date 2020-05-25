package com.sun.testboot.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

/**

 */
public abstract class Observer {
    protected Subject subject;
    public abstract void update();
}
