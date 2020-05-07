package com.sun.testboot.batch.itemreader.simpleview;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class ItemReaderSimpleViewConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job simpleViewJob(){
        return jobBuilderFactory.get("simpleViewJob")
                .start(simpleViewStep())
                .build();
    }

    @Bean
    public Step simpleViewStep() {
        return stepBuilderFactory.get("simpleViewStep")
                .<String,String>chunk(2)
                .reader(simpleViewItemReader())
                .writer(list -> {
                    for (String str:list){
                        System.out.println("current item is :"+str);
                    }
                }).build();
    }

    /**
     * reader和writer一定要定义成bean
     * @return
     */
    @Bean
    public SimpleViewItemReader simpleViewItemReader() {
        List<String> data = Arrays.asList("aaa","bbb","ccc","ddd","eee","fff");
        return new SimpleViewItemReader(data);
    }
}
