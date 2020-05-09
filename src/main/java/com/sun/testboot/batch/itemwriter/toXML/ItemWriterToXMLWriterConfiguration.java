package com.sun.testboot.batch.itemwriter.toXML;

import com.sun.testboot.batch.itemreader.fromDB.Customer;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ItemWriterToXMLWriterConfiguration {

    @Bean
    public StaxEventItemWriter itemWriterToXMLWriter() throws Exception {
        XStreamMarshaller marshaller = new XStreamMarshaller();
        Map<String,Class> aliases = new HashMap<>();
        aliases.put("customer", Customer.class);
        marshaller.setAliases(aliases);

        StaxEventItemWriter writer = new StaxEventItemWriter();
        writer.setRootTagName("customers");
        writer.setMarshaller(marshaller);
        String path = File.createTempFile("customerinfo",".xml").getAbsolutePath();
        System.out.println("xml file generated in:"+path);
        writer.setResource(new FileSystemResource(path));
        return writer;
    }
}
