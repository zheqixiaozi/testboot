package com.sun.testboot.designpattern.factory.abstractfactory;

/**
 * 抽象工厂模式
 * 工厂方法模式的接口中包含 多个 方法就是抽象工厂模式
 */
public interface IFactory {
    Product createProduct(String type);
    Gift createGift(String type);
}
