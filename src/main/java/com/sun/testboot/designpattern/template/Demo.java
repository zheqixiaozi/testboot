package com.sun.testboot.designpattern.template;

/**
 * 模板模式：
 * 父类已经定义好了所有的方法，子类只要实现就好了，另外还可以扩展自己的方法
 */
public class Demo {
    public static void main(String[] args) {
        Game game = new Cricket();
        game.play();
        System.out.println("--------------");
        game = new Football();
        game.play();
    }
}
