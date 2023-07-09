package com.example.demo.bishnu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.bishnu.entity.OrderListEntity;
import com.example.demo.bishnu.repo.OrderListRepo;
import com.example.demo.bishnu.service.OrderListService;

@Service
public class OrderListServiceImpl implements OrderListService{
  @Autowired
  private OrderListRepo orderListRepo;

  @Override
  public List<OrderListEntity> getOrderEntityByUserId(int userId) { 
    return this.orderListRepo.getOrderListEntityByUserId(userId);
  }

}
