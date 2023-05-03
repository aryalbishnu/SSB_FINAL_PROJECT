package com.example.demo.bishnu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "cardEntity")
public class CardEntity {
  
  @Id
  @Column(name = "card_Id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int cardId;
  
  @Column(name = "card_Number")
  private String cardNumber;
  
  @Column(name = "pin_Number")
  private String pinNumber;
  
  @Column(name = "balance")
  private int balance;

  @OneToOne
  @JoinColumn(name = "id", referencedColumnName = "id")
  private BishnuEntity bishnuEntity;

}
