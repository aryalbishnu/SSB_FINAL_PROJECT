package com.example.demo.bishnu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.bishnu.dto.ProductDto;

@Mapper
public interface ProductDtoMapper {
  
  List<ProductDto> productDtoList();
  
  boolean booleanCheck(int productid, int userid);
  
  int productDtoLiked(int productid, int userid);

}
