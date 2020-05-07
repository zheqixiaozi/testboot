package com.sun.testboot.batch.listenerDemo;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;

public class MyChunkListener{
    @BeforeChunk
    public void beforeChunk() throws Exception {
        System.out.println("MyChunkListener....beforeChunk....");
    }
    @AfterChunk
    public void afterChunk() throws Exception {
        System.out.println("MyChunkListener....afterChunk....");
    }
}
