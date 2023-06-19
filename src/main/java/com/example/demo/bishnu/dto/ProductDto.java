package com.example.demo.bishnu.dto;

import lombok.Data;

@Data
public class ProductDto {
  
  private int id;

  private String product_name;

  private int product_price;

  private int product_quantity;

  private String product_image;

  private String product_brand;

  private String product_create_date;

  private String product_update_date;

  private int product_user_id;
  
  private int likeid;

}
