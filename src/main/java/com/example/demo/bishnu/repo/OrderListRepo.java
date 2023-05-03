package com.example.demo.bishnu.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.bishnu.entity.OrderListEntity;

public interface OrderListRepo extends JpaRepository<OrderListEntity, Integer> {

}
