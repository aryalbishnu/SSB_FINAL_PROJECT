package com.example.demo.bishnu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.bishnu.dto.SaleDto;

@Mapper
public interface SaleMapper {

  List<SaleDto> totalSale(String saleDate);
  
  int countAddCart(int userId); 
  
  List<SaleDto> saleCountByProductId();
  
  // sale search by common multiple field
  List<SaleDto> saleCommonSearch(String query);
  
//sale search by sale Id
 List<SaleDto> saleSearchBySaleId(String query);
 
//sale search by sale Name
List<SaleDto> saleSearchBySaleName(String query);

//sale search by product brand
List<SaleDto> saleSearchByProductBrand(String query);

//sale search by sale Date
List<SaleDto> saleSearchBySaleDate(String query);
}
