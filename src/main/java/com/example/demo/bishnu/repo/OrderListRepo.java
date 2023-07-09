package com.example.demo.bishnu.repo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.bishnu.entity.OrderListEntity;

public interface OrderListRepo extends JpaRepository<OrderListEntity, Integer> {

  @Query("select u from OrderListEntity u where u.userId =:userId")
  public List<OrderListEntity> getOrderListEntityByUserId(@Param("userId") int userId);
}
