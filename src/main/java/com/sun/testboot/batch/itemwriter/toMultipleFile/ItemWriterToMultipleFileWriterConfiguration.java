package com.sun.testboot.batch.itemwriter.toMultipleFile;

import com.sun.testboot.batch.itemreader.fromDB.Customer;
import com.sun.testboot.batch.itemwriter.toFile.MyLineAggregator;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ItemWriterToMultipleFileWriterConfiguration {

    @Bean
    public StaxEventItemWriter<Customer> xmlFileWriter() throws Exception {
        XStreamMarshaller marshaller = new XStreamMarshaller();
        Map<String,Class> aliases = new HashMap<String,Class>();
        aliases.put("customer",Customer.class);
        marshaller.setAliases(aliases);
        marshaller.afterPropertiesSet();
        StaxEventItemWriter writer = new StaxEventItemWriter();
        String path = File.createTempFile
                ("multiinfo",".xml").getAbsolutePath();
        System.out.println(">> xml file path:"+path);
        writer.setResource(new FileSystemResource(path));
        writer.setMarshaller(marshaller);
        writer.setRootTagName("customers");
        writer.afterPropertiesSet();
        return writer;
    }

    @Bean
    public FlatFileItemWriter<Customer> jsonFileWriter() throws Exception{
        FlatFileItemWriter writer = new FlatFileItemWriter();
        String path = File.createTempFile("multiinfo",".json").getAbsolutePath();
        System.out.println(">> json file path:"+path);
        writer.setResource(new FileSystemResource(path));
        writer.setLineAggregator(new MyLineAggregator());
        writer.afterPropertiesSet();
        return writer;
    }

    /*@Bean
    public CompositeItemWriter itemWriterToMultipleFileWriter() throws Exception {
        CompositeItemWriter writer = new CompositeItemWriter();
        writer.setDelegates(Arrays.asList(xmlFileWriter(),
                jsonFileWriter()));
        writer.afterPropertiesSet();
        return writer;
    }*/

    @Bean
    public ClassifierCompositeItemWriter itemWriterToMultipleFileWriter()throws Exception{
        ClassifierCompositeItemWriter writer = new ClassifierCompositeItemWriter();
        writer.setClassifier(new MyClassifier(xmlFileWriter(),
                jsonFileWriter()));
        return writer;
    }

}
