package com.sun.testboot.designpattern.observer;

public class ObserverA extends Observer{

    ObserverA(Subject subject){
        this.subject = subject;
    }
    @Override
    public void update() {
        System.out.println("状态改变A接收到了,状态变成了"+subject.getState());
    }
}
