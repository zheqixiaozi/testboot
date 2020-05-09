package com.sun.testboot.batch.itemwriter.toFile;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.io.IOException;

@Configuration
public class ItemWriterToFileWriterConfiguration {
    @Bean
    public FlatFileItemWriter itemWriterToFileWriter() throws Exception {
        FlatFileItemWriter writer = new FlatFileItemWriter();
        String path = File.createTempFile("customerInfo",".data").getAbsolutePath();
        System.out.println("file is created in:" + path);
        writer.setResource(new FileSystemResource(path));
        writer.setLineAggregator(new MyLineAggregator());
        writer.afterPropertiesSet();
        return writer;
    }
}
