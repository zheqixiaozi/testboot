package com.sun.testboot;

import com.sun.testboot.batch.itemreader.fromDB.Customer;
import com.sun.testboot.mapper.CustomerMapper;
import com.sun.testboot.mapper.StudentMapper;
import com.sun.testboot.mapper.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class RedisTest {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisTemplate studentRedisTemplate;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private StudentMapper studentMapper;



    @Test
    public void test(){
        //stringRedisTemplate.opsForValue().append("aa","zhangsan");
//        String aa = stringRedisTemplate.opsForValue().get("aa");
//        System.out.println("aa:"+aa);
        Customer customer = customerMapper.findById(1);
        System.out.println("customer:"+customer.toString());
        Student student = studentMapper.findById(1);
        System.out.println("student:"+student.toString());
        //json的形式保存对象数据
        studentRedisTemplate.opsForValue().set("stu-1",student);
        redisTemplate.opsForValue().set("aaa",111);
        stringRedisTemplate.opsForValue().set("bbb","222");
        stringRedisTemplate.opsForValue().set("ccc","333");
    }
}
