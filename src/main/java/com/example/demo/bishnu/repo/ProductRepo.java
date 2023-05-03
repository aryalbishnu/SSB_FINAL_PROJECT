package com.example.demo.bishnu.repo;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.bishnu.entity.ProductEntity;

public interface ProductRepo extends JpaRepository<ProductEntity, Integer>{

  @Query("select u from ProductEntity u where u.productName =:productName")
  public ProductEntity getProductEntityByProductName(@Param("productName") String productName);
}
