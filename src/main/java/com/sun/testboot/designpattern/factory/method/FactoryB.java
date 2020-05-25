package com.sun.testboot.designpattern.factory.method;

public class FactoryB implements IFactory {
    @Override
    public Product createProduct(String type) {
        return new ProductB();
    }
}
