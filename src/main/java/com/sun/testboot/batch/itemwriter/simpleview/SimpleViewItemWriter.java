package com.sun.testboot.batch.itemwriter.simpleview;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("simpleViewItemWriter")
public class SimpleViewItemWriter implements ItemWriter<String> {
    @Override
    public void write(List<? extends String> list) throws Exception {
        System.out.println("chunk sizes: "+ list.size());
        for (String str:list){
            System.out.println(str);
        }
    }
}
