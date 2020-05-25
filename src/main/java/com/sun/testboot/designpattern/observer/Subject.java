package com.sun.testboot.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 观察者模式
 * 当状态改变的时候通知观察者们
 * 关键点：该类中存放了所有的观察者的list
 */
public class Subject {

    private List<Observer> list = new ArrayList<>();
    private int state;

    public int getState() {
        return state;
    }

    //改变状态的时候通知所有的观察者
    public void setState(int state) {
        this.state = state;
        notifyAllObservers();
    }

    public void attach(Observer observer){
        list.add(observer);
    }
    public void notifyAllObservers(){
        for (Observer observer:list){
            observer.update();
        }
    }
}
