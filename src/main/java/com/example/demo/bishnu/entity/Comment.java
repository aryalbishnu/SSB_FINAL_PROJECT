package com.example.demo.bishnu.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "comment")
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int commentid;
  
  private String comment;
  
  private String time;
  
  private int productid;
  
  private int userid;
  
  private String updatetime;
  
  
  
}
