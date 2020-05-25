package com.sun.testboot.designpattern.factory.simple;

/**
 * 简单工厂模式
 * 一个接口，多个实现类，一个生产工厂类
 */
public interface Product {

    //价格
    int price();

    //产品名
    String getName();
}
