package com.example.demo.bishnu.dto;

import lombok.Data;

@Data
public class SaleDto {
 private int sale_Id;

  private String sale_Name;

  private int sale_Price;
  
  private int sale_Quantity;

  private String sale_Date;

  private int user_Id;

  private int product_Id;
  
  private String product_image;
}
