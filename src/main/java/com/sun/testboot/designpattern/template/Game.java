package com.sun.testboot.designpattern.template;

/**
 * 模板模式：定义一个抽象类，其中定义公共的方法，子类必须实现这些方法
 */
public abstract class Game {
    abstract void initialize();
    abstract void startPlay();
    abstract void endPlay();
    //模板
    public final void play(){

        //初始化游戏
        initialize();

        //开始游戏
        startPlay();

        //结束游戏
        endPlay();
    }

}
