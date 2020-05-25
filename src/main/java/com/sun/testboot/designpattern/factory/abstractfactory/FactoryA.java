package com.sun.testboot.designpattern.factory.abstractfactory;

public class FactoryA implements IFactory {
    @Override
    public Product createProduct(String type) {
        return new ProductA();
    }

    @Override
    public Gift createGift(String type) {
        return null;
    }
}
