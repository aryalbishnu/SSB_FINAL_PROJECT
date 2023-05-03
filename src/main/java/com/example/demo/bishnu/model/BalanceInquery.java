package com.example.demo.bishnu.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class BalanceInquery {
  
  @NotNull
  @Size(min = 12)
  private String cardNumber;
  
  @NotNull
  @Size(min = 4)
  private String pinNumber;

}
