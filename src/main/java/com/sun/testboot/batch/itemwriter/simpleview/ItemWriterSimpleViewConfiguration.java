package com.sun.testboot.batch.itemwriter.simpleview;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ItemWriterSimpleViewConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("simpleViewItemWriter")
    private ItemWriter<String> simpleViewItemWriter;

    @Bean
    public Job simpleViewWriterJob(){
        return jobBuilderFactory.get("simpleViewWriterJob")
                .start(simpleViewWriterStep())
                .build();
    }

    @Bean
    public Step simpleViewWriterStep() {
        return stepBuilderFactory.get("simpleViewWriterStep")
                .<String,String>chunk(10)
                .reader(simpleViewWriterStepReader())
                .writer(simpleViewItemWriter)
                .build();
    }

    @Bean
    public ListItemReader<String> simpleViewWriterStepReader() {
        List<String> items = new ArrayList<String>();
        for (int i = 0;i<100;i++){
            items.add(i+"");
        }
        return new ListItemReader<>(items);
    }
}
