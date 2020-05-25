package com.sun.testboot.designpattern.factory.method;

/**
 * 工厂方法模式
 * 简单工厂违反开闭原则（因为每增加一种产品就会手动修改Factory中获取产品的代码）
 */
public interface IFactory {
    Product createProduct(String type);
}
