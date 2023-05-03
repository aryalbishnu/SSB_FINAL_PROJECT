package com.example.demo.bishnu.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PaymentModel {
  
  @NotNull
  private String cardNumber;
  
  @NotNull
  private String pinNumber;

  private int amount;

}
