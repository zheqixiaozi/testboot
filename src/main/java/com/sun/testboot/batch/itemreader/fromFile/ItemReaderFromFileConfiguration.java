package com.sun.testboot.batch.itemreader.fromFile;

import com.sun.testboot.batch.itemreader.fromDB.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ItemReaderFromFileConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    @Qualifier("ItemReaderFromFileWriter")
    private ItemWriter<Customer> itemReaderFromFileWriter;
    @Autowired
    private DataSource dataSource;

    @Bean
    public Job itemReaderFromFileJob(){
        return jobBuilderFactory.get("itemReaderFromFileJob")
                .start(itemReaderFromFileStep())
                .build();
    }

    @Bean
    public Step itemReaderFromFileStep() {
        return stepBuilderFactory.get("itemReaderFromFileStep")
                .<Customer,Customer>chunk(100)
                .reader(itemReaderFromFileReader())
                .writer(itemReaderFromFileWriter)
                .build();
    }
    @Bean
    @StepScope
    public FlatFileItemReader<Customer> itemReaderFromFileReader() {
        FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();
        //注意要修改pom文件编译打包.csv文件
        ClassPathResource resource = new ClassPathResource("customer.csv");
        reader.setResource(resource);
        reader.setLinesToSkip(1);
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("id","firstName","lastName","birthday");
        DefaultLineMapper<Customer> mapper = new DefaultLineMapper<>();
        mapper.setLineTokenizer(tokenizer);
        mapper.setFieldSetMapper(fieldSet -> {
            return Customer.builder().id(fieldSet.readInt("id"))
                    .firstName(fieldSet.readString("firstName"))
                    .lastName(fieldSet.readString("lastName"))
                    .birthday(fieldSet.readString("birthday"))
                    .build();
        });
        reader.setLineMapper(mapper);
        return reader;
    }
}
