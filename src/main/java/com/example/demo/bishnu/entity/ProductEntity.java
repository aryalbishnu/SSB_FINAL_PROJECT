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
@Table(name = "product_Entity")
public class ProductEntity {

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
  
  @Column(name = "product_Brand")
  private String productBrand;
  
  @Column(name = "product_Create_Date")
  private String productCreateDate;
  
  @Column(name = "product_Update_Date")
  private String productUpdateDate;
  
  @Column(name = "product_User_Id")
  private Integer productUserId;
  
  
}
