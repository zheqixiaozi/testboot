package com.sun.testboot.designpattern.factory.simple;

/**
 * 简单工厂类
 */
public class SimpleFactory {
    /**
     * 根据生产类型生产对应的产品
     * @param type
     * @return
     */
    public static Product createProduct(String type){
        Product product = null;
        switch (type){
            case "A":
                product = new ProductA();
                break;
            case "B":
                product = new ProductB();
                break;
        }
        return product;
    }
}
