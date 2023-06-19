package com.example.demo.bishnu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.bishnu.dto.SaleDto;

@Mapper
public interface SaleMapper {

  List<SaleDto> totalSale(String saleDate);
}
