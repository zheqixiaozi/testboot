package com.sun.testboot.designpattern.factory.method;

public class FactoryA implements IFactory{
    @Override
    public Product createProduct(String type) {
        return new ProductA();
    }
}
