package com.sun.testboot.batch.exception;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("itemExeptionWriter")
public class ItemExceptionWriterConfiguration implements ItemWriter<String> {

    @Override
    public void write(List<? extends String> list) throws Exception {
        for (String string:list){
            System.out.println(string);
        }
    }
}
