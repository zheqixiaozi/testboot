package com.sun.testboot.batch.itemwriter.toDB;

import com.sun.testboot.batch.itemreader.fromDB.Customer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class ItemWritertoDBWriterConfiguration {

    @Autowired
    public DataSource dataSource;

    @Bean
    public JdbcBatchItemWriter<Customer> itemWritertoDBWriter(){
        JdbcBatchItemWriter writer = new JdbcBatchItemWriter();
        writer.setDataSource(dataSource);
        writer.setSql("insert into customer(first_name,last_name,birthday) values " +
                "(:firstName,:lastName,:birthday)");
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
        return writer;
    }

}
