package com.sun.testboot.batch.itemwriter.toDB;

import com.sun.testboot.batch.itemreader.fromDB.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemWriterToDBConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("itemReaderFromFileReader")
    public ItemReader<Customer> itemReaderFromFileReader;
    @Autowired
    @Qualifier("itemWritertoDBWriter")
    private ItemWriter<Customer> itemWritertoDBWriter;

    @Bean
    public Job itemWriterToDBJob(){
        return jobBuilderFactory.get("itemWriterToDBJob")
                .start(itemWriterToDBStep())
                .build();
    }

    @Bean
    public Step itemWriterToDBStep() {
        return stepBuilderFactory.get("itemWriterToDBStep")
                .<Customer,Customer>chunk(10)
                .reader(itemReaderFromFileReader)
                .writer(itemWritertoDBWriter)
                .build();
    }
}
