package com.sun.testboot.designpattern.strategy;

/**
 * 策略模式测试
 * 根据参数的不同调用不同的结果集（构造方法中的参数，给了具体的实例）
 */
public class Demo {
    public static void main(String[] args) {
        Context context = new Context(new OperationAdd());
        int result = context.executeStrategy(10, 20);
        System.out.println("result:"+result);
    }
}
