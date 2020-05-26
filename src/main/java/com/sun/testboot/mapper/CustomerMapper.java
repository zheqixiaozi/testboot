package com.sun.testboot.mapper;

import com.sun.testboot.batch.itemreader.fromDB.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CustomerMapper {

    @Select("select * from customer where id = #{id}")
    Customer findById(int id);

}
