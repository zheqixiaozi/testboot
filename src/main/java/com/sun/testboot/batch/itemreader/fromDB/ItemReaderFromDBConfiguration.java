package com.sun.testboot.batch.itemreader.fromDB;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ItemReaderFromDBConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    @Qualifier("ItemReaderFromDBWriter")
    private ItemWriter<Customer> itemReaderFromDBWriter;
    @Autowired
    private DataSource dataSource;

    @Bean
    public Job itemReaderFromDBJob(){
        return jobBuilderFactory.get("itemReaderFromDBJob")
                .start(itemReaderFromDBStep())
                .build();
    }

    @Bean
    public Step itemReaderFromDBStep() {
        return stepBuilderFactory.get("itemReaderFromDBStep")
                .<Customer,Customer>chunk(100)
                .reader(itemReaderFromDBReader())
                .writer(itemReaderFromDBWriter)
                .build();
    }
    @Bean
    public JdbcPagingItemReader<Customer> itemReaderFromDBReader() {
        JdbcPagingItemReader reader = new JdbcPagingItemReader();
        reader.setDataSource(this.dataSource);
        reader.setFetchSize(100);
        reader.setRowMapper((rs,rownum)->{
            return Customer.builder().id(rs.getInt("id"))
                    .firstName(rs.getString("first_name"))
                    .lastName(rs.getString("last_name"))
                    .birsthday(rs.getDate("birthday"))
                    .build();
        });
        MySqlPagingQueryProvider provider = new MySqlPagingQueryProvider();
        //模拟多表关联查询
        provider.setFromClause("from customer c left join BATCH_JOB_EXECUTION b on c.id = b.JOB_EXECUTION_ID");
        provider.setSelectClause("select c.id,c.first_name,c.last_name,c.birthday");
        Map<String, Order> sortKeys = new HashMap<String,Order>();
        sortKeys.put("c.id",Order.ASCENDING);
        sortKeys.put("c.birthday",Order.DESCENDING);
        provider.setSortKeys(sortKeys);
        reader.setQueryProvider(provider);
        return reader;
    }
}
