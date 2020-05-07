package com.sun.testboot.batch.itemreader.fromXML;

import com.sun.testboot.batch.itemreader.fromDB.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * spring-oxm\stream都要引入
 * 注意设置fieldAliases的前后顺序，xml字段在后面
 */
@Configuration
public class ItemReaderFromXMLConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    @Qualifier("ItemReaderFromXMLWriter")
    private ItemWriter<Customer> itemReaderFromXMLWriter;
    @Autowired
    private DataSource dataSource;

    @Bean
    public Job itemReaderFromXMLJob(){
        return jobBuilderFactory.get("itemReaderFromXMLJob")
                .start(itemReaderFromXMLStep())
                .build();
    }

    @Bean
    public Step itemReaderFromXMLStep() {
        return stepBuilderFactory.get("itemReaderFromXMLStep")
                .<Customer,Customer>chunk(100)
                .reader(itemReaderFromXMLReader())
                .writer(itemReaderFromXMLWriter)
                .build();
    }
    @Bean
    @StepScope
    public StaxEventItemReader<Customer> itemReaderFromXMLReader() {
        StaxEventItemReader<Customer> reader = new StaxEventItemReader<>();
        reader.setResource(new ClassPathResource("customer.xml"));
        reader.setFragmentRootElementName("RECORD");

        XStreamMarshaller unMarshaller = new XStreamMarshaller();
        Map<String,Class> map = new HashMap<String,Class>();
        map.put("RECORD",Customer.class);
        unMarshaller.setAliases(map);
        Map<String,String> fieldMap = new HashMap<String,String>();
        fieldMap.put(Customer.class.getName()+".id","id");
        fieldMap.put(Customer.class.getName()+".firstName","first_name");
        fieldMap.put(Customer.class.getName()+".lastName","last_name");
        fieldMap.put(Customer.class.getName()+".birthday","birthday");
        unMarshaller.setFieldAliases(fieldMap);
        reader.setUnmarshaller(unMarshaller);
        return reader;
    }
}
