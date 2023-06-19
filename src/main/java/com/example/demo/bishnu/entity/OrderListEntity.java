package com.example.demo.bishnu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "order_List")
public class OrderListEntity {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  
  @Column(name = "product_Name")
  private String productName;
  
  @Column(name = "product_Price")
  private int productPrice;
  
  @Column(name = "product_Quantity")
  private int productQuantity;
  
  @Column(name = "product_Image")
  private String productImage;
  
  @Column(name = "product_Id")
  private int productId;

}
