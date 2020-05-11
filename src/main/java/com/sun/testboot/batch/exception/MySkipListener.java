package com.sun.testboot.batch.exception;

import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

@Component("mySkipListener")
public class MySkipListener implements SkipListener<String,String> {

    @Override
    public void onSkipInRead(Throwable throwable) {

    }

    @Override
    public void onSkipInWrite(String s, Throwable throwable) {

    }

    @Override
    public void onSkipInProcess(String s, Throwable throwable) {
        System.out.println(s +" got exception,please pay attation to it.["+throwable+"]");
    }
}
