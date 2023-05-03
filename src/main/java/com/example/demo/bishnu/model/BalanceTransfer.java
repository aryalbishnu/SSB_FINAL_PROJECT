package com.example.demo.bishnu.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class BalanceTransfer {

  @NotNull
  @Size(min = 12)
  private String toCardNumber;
  
  @NotNull
  @Size(min = 12)
  private String fromCardNumber;
  
  @NotNull
  @Size(min = 4)
  private String pinNumber;
  
  @NotNull
  private int amount;
}
