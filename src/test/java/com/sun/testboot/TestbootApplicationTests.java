package com.sun.testboot;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

@SpringBootTest
class TestbootApplicationTests {
    @Autowired
    public JdbcTemplate jdbcTemplate;

    /**
     * 批量入库
     */
    @Test
    void contextLoads() {
        String sql = "insert into customer(first_name,last_name,birthday)values(?,?,?)";
        for (int i = 0;i<200;i++){
            jdbcTemplate.update(sql,"rose"+i,"lang"+i,new Date());
        }
    }

}
