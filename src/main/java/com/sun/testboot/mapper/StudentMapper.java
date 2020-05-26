package com.sun.testboot.mapper;

import com.sun.testboot.mapper.entity.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper {

    Student findById(int id);

}
