package com.sun.testboot.batch.itemreader.restartDemo;

import com.sun.testboot.batch.itemreader.fromDB.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
public class ItemReaderRestartConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    @Qualifier("ItemReaderFromFileWriter")
    private ItemWriter<Customer> itemReaderFromFileWriter;
    @Autowired
    private DataSource dataSource;
    @Autowired
    @Qualifier("restartDemoReader")
    private ItemReader<Customer> restartDemoReader;

    @Bean
    public Job restartDemoJob(){
        return jobBuilderFactory.get("restartDemoJob")
                .start(itemReaderRestartStep())
                .build();
    }

    @Bean
    public Step itemReaderRestartStep() {
        return stepBuilderFactory.get("itemReaderRestartStep")
                .<Customer,Customer>chunk(10)
                .reader(restartDemoReader)
                .writer(itemReaderFromFileWriter)
                .build();
    }

}
