package com.sun.testboot.batch.itemwriter.toXML;

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
public class ItemWriterToXMLConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("itemReaderFromDBReader")
    private ItemReader<Customer> itemReaderFromDBReader;
    @Autowired
    @Qualifier("itemWriterToXMLWriter")
    private ItemWriter<Customer> itemWriterToXMLWriter;

    @Bean
    public Job itemWriterToXMLJob(){
        return jobBuilderFactory.get("itemWriterToXMLJob")
                .start(itemWriterToXMLStep())
                .build();
    }

    @Bean
    public Step itemWriterToXMLStep() {
        return stepBuilderFactory.get("itemWriterToFileStep")
                .<Customer,Customer>chunk(50)
                .reader(itemReaderFromDBReader)
                .writer(itemWriterToXMLWriter)
                .build();
    }
}
