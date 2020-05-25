package com.sun.testboot.designpattern.factory.method;

public class ProductB implements Product {
    @Override
    public int price() {
        return 120;
    }

    @Override
    public String getName() {
        return "香蕉";
    }
}
