package com.example.demo.model;

import lombok.Data;

@Data
public class User {
	String userId;
	String userName;
	String pwd;
	String gender;
// 컬럼이름은 대소문자는 구분하지 않고 철자는 똑같아야함.
	
}
