package com.example.demo.bishnu.dto;


import lombok.Data;

@Data
public class ProductCommentDto {
  
  private int commentid;
  private String first_name;
  private String image;
  private String last_name;
  private String comment;
  private int userid;
  private String time;
  private int productid;

}
