package com.sun.testboot.designpattern.factory.abstractfactory;

public class FactoryB implements IFactory {
    @Override
    public Product createProduct(String type) {
        return new ProductB();
    }

    @Override
    public Gift createGift(String type) {
        return null;
    }
}
