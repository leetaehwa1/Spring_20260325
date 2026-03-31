package com.example.demo.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.Dept;
import com.example.demo.model.Professor;
import com.example.demo.model.Student;

@Mapper
public interface SchoolMapper {
	public List<Professor>selectProfList(HashMap<String, Object> map);
	public List<Student>selectStuList(HashMap<String, Object> map);
	public List<Dept>selectDeptList(HashMap<String, Object> map);
	public int insertStu(HashMap<String, Object> map);
	public int insertProf(HashMap<String, Object> map);
	public Student selectStu(HashMap<String, Object> map);
	public int deleteStu(HashMap<String, Object> map);
	public int deleteProf(HashMap<String, Object> map);


}
