package com.example.demo.bishnu.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "liked")
public class Liked {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int likeid;
  
  private int productid;
  
  private int userid;

}
