package com.sun.testboot.designpattern.strategy;

/**
 * 相加
 */
public class OperationAdd implements Strategy{

    @Override
    public int doOperation(int num1, int num2) {
        return num1 + num2;
    }
}
