package com.sun.testboot.batch.itemwriter.toMultipleFile;

import com.sun.testboot.batch.itemreader.fromDB.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemWriterToMultipleFileConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    @Qualifier("itemReaderFromDBReader")
    private ItemReader<Customer> itemReaderFromDBReader;

    @Autowired
    @Qualifier("itemWriterToMultipleFileWriter")
    private ItemWriter<Customer> itemWriterToMultipleFileWriter;

    @Autowired
    @Qualifier("xmlFileWriter")
    private ItemStreamWriter<Customer> xmlFileWriter;
    @Autowired
    @Qualifier("jsonFileWriter")
    private ItemStreamWriter<Customer> jsonFileWriter;

    @Bean
    public Job itemWriterToMultipleFileJob(){
        return jobBuilderFactory.get("itemWriterToMultipleFileJob")
                .start(itemWriterToMultipleFileStep())
                .build();
    }

    /**
     * 注意：用分类器classifier根据不同writer写入不同文件时，要把所有的writer以stream的形式传入到step中
     * @return
     */
    @Bean
    public Step itemWriterToMultipleFileStep() {
        return stepBuilderFactory.get("itemWriterToMultipleFileStep")
                .<Customer,Customer>chunk(10)
                .reader(itemReaderFromDBReader)
                .writer(itemWriterToMultipleFileWriter)
                .stream(xmlFileWriter)
                .stream(jsonFileWriter)
                .build();
    }
}
