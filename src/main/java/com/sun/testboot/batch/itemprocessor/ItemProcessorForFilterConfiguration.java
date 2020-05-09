package com.sun.testboot.batch.itemprocessor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ItemProcessorForFilterConfiguration {
    @Autowired
    @Qualifier("firstNameToUpperProcessor")
    private ItemProcessor firstNameToUpperProcessor;

    @Autowired
    @Qualifier("idFilterProcessor")
    private ItemProcessor idFilterProcessor;
    @Bean
    public CompositeItemProcessor itemProcessorForFilter() throws Exception{
        CompositeItemProcessor processor = new CompositeItemProcessor();
        List<ItemProcessor> lists = new ArrayList<ItemProcessor>();
        lists.add(firstNameToUpperProcessor);
        lists.add(idFilterProcessor);
        processor.setDelegates(lists);
        processor.afterPropertiesSet();
        return processor;
    }


}
