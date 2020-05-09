package com.sun.testboot.batch.itemprocessor;

import com.sun.testboot.batch.itemreader.fromDB.Customer;
import org.springframework.batch.core.Job;
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
public class ItemProcessorToFileConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    @Qualifier("itemReaderFromDBReader")
    private ItemReader<Customer> itemReaderFromDBReader;
    @Autowired
    @Qualifier("itemWriterToFileWriter")
    private ItemWriter<Customer> itemWriterToFileWriter;
    @Autowired
    @Qualifier("itemProcessorForFilter")
    private ItemProcessor<Customer,Customer> itemProcessorForFilter;

    @Bean
    public Job itemProcessorToFileJob(){
        return jobBuilderFactory.get("itemProcessorToFileJob")
                .start(itemProcessorToFileStep())
                .build();
    }

    @Bean
    public Step itemProcessorToFileStep() {
        return stepBuilderFactory.get("itemProcessorToFileStep")
                .<Customer,Customer>chunk(50)
                .reader(itemReaderFromDBReader)
                .processor(itemProcessorForFilter)
                .writer(itemWriterToFileWriter)
                .build();
    }
}
