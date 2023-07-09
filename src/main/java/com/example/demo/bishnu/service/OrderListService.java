package com.example.demo.bishnu.service;

import java.util.List;

import com.example.demo.bishnu.entity.OrderListEntity;

public interface OrderListService {
  
//find product by product id
 public List<OrderListEntity> getOrderEntityByUserId(int userId);

}
