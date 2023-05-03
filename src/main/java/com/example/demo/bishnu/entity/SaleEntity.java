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
@Table(name = "sale_Entity")
public class SaleEntity {
  
  @Id
  @Column(name = "sale_Id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int SaleId;
  
  @Column(name = "sale_Name")
  private String SaleName;
  
  @Column(name = "sale_Quantity")
  private int SaleQuantity;
  
  @Column(name = "sale_Price")
  private int SalePrice;
  
  @Column(name = "sale_Date")
  private String SaleDate;
  
  

}
