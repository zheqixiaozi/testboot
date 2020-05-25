package com.sun.testboot.designpattern.observer;

public class ObserverB extends Observer{
    ObserverB(Subject subject){
        this.subject = subject;
    }
    @Override
    public void update() {
        System.out.println("状态改变B接收到了,状态变成了"+subject.getState());
    }
}
