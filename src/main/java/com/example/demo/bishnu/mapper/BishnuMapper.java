package com.example.demo.bishnu.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.bishnu.model.Login;
@Mapper
public interface BishnuMapper {
  
  int dologing(Login login);
 

}
