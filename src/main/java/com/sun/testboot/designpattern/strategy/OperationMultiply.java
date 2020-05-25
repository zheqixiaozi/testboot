package com.sun.testboot.designpattern.strategy;

/**
 * 相乘
 */
public class OperationMultiply implements Strategy{
    @Override
    public int doOperation(int num1, int num2) {
        return num1 * num2;
    }
}
