package com.sun.testboot.batch.exception;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemExceptionConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    @Qualifier("itemExeptionReader")
    private ItemReader<String> itemExeptionReader;

    @Autowired
    @Qualifier("itemExeptionWriter")
    private ItemWriter<String> itemExeptionWriter;
    @Autowired
    @Qualifier("itemExeptionProcessor")
    private ItemProcessor<String,String> itemExeptionProcessor;
    @Autowired
    @Qualifier("mySkipListener")
    private SkipListener<String,String> mySkipListener;

    @Bean
    public Job itemExceptionJob(){
        return jobBuilderFactory.get("itemExceptionJob")
                .start(itemExceptionStep())
                .build();
    }

    @Bean
    public Step itemExceptionStep() {
        return stepBuilderFactory.get("itemExceptionStep")
                .<String,String>chunk(10)
                .reader(itemExeptionReader)
                .processor(itemExeptionProcessor)
                .writer(itemExeptionWriter)
                .faultTolerant()
                .retry(MyException.class)
                .retryLimit(10)
                .build();
    }

    @Bean
    public Job itemExceptionSkipJob1(){
        return jobBuilderFactory.get("itemExceptionSkipJob1")
                .start(itemExceptionSkipStep1())
                .build();
    }

    @Bean
    public Step itemExceptionSkipStep1() {
        return stepBuilderFactory.get("itemExceptionSkipStep1")
                .<String,String>chunk(10)
                .reader(itemExeptionReader)
                .processor(itemExeptionProcessor)
                .writer(itemExeptionWriter)
                .faultTolerant()
                .skip(MyException.class)
                .skipLimit(10)
                .listener(mySkipListener)
                .build();
    }
}
