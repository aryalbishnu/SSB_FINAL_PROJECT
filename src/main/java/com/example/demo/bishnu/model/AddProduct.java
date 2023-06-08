package com.example.demo.bishnu.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data

public class AddProduct {

  @NotNull
  private String productName;
  
  @NotNull
  private int productPrice;
  
  @NotNull
  private int productQuantity;
  
  private String productImage;
  
  @NotNull
  private String productBrandName;
  
  private String productCreateDate;
  
  

  
}
