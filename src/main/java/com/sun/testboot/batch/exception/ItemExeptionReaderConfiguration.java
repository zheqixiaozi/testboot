package com.sun.testboot.batch.exception;

import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.item.support.ListItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ItemExeptionReaderConfiguration {

    @Bean
    public ListItemReader itemExeptionReader(){
        List<String> list = new ArrayList<String>();
        for (int i = 0;i<40;i++){
            list.add(i + "");
        }
        return new ListItemReader(list);
    }
}
