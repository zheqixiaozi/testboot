package com.sun.testboot.batch.itemreader.multipleFile;

import com.sun.testboot.batch.itemreader.fromDB.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

@Configuration
public class ItemReaderMultipleFileConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    @Qualifier("ItemReaderFromFileWriter")
    private ItemWriter<Customer> itemReaderFromFileWriter;
    @Autowired
    private DataSource dataSource;
    @Value("classpath*:/file*.csv")
    private Resource[] inputFiles;

    @Bean
    public Job itemReaderMultipleFileJob(){
        return jobBuilderFactory.get("itemReaderMultipleFileJob")
                .start(itemReaderMultipleFileStep())
                .build();
    }

    @Bean
    public Step itemReaderMultipleFileStep() {
        return stepBuilderFactory.get("itemReaderMultipleFileStep")
                .<Customer,Customer>chunk(30)
                .reader(itemReaderMultipleFileReader())
                .writer(itemReaderFromFileWriter)
                .build();
    }

    @Bean
    public MultiResourceItemReader itemReaderMultipleFileReader() {
        MultiResourceItemReader reader = new MultiResourceItemReader();
        reader.setDelegate(flatFileReader());

        reader.setResources(inputFiles);
        return reader;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Customer> flatFileReader() {
        FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();
        //注意要修改pom文件编译打包.csv文件
        ClassPathResource resource = new ClassPathResource("customer.csv");
        reader.setResource(resource);
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
