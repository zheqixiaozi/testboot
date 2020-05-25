package com.sun.testboot.designpattern;

/**
 * 单例模式：饱汉式，饿汉式
 * 双检锁/双重校验锁
 * 三步骤：1、私有的该类型的静态变量 2、私有的构造方法  3、公有的获取该类型的静态方法
 */
public class Singleton {

    private volatile static Singleton singleton;
    private Singleton(){}
    public static Singleton getSingleton(){
        if (singleton == null){
            synchronized (Singleton.class){
                if (singleton == null){
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
