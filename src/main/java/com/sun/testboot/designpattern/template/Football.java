package com.sun.testboot.designpattern.template;

public class Football extends Game{
    @Override
    void initialize() {
        System.out.println("Football Game initialize!");
    }

    @Override
    void startPlay() {
        System.out.println("Football Game startPlay!");
    }

    @Override
    void endPlay() {
        System.out.println("Football Game endPlay!");
    }
}
