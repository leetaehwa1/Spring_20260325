package com.example.demo.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.Stu;

@Mapper
public interface StudentMapper {
	public List<Stu> selectStuList();
	
	public int deleteStudent(HashMap<String,Object> map);
}
