package com.example.demo.bishnu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.bishnu.dto.ProductCommentDto;

@Mapper
public interface ProductCommentMapper {
  
 List<ProductCommentDto> productCommentDtos(int productid);
 
 void deleteCommentProduct(int commentid, int userid);

}
