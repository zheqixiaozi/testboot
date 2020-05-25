package com.sun.testboot.designpattern.factory.simple;

public class ProductA implements Product{

    @Override
    public int price() {
        return 100;
    }

    @Override
    public String getName() {
        return "苹果";
    }
}
