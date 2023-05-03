package com.example.demo.bishnu.model;

import java.security.SecureRandom;
import java.util.Random;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.demo.bishnu.validation.SeqValiInterface.Advance;
import com.example.demo.bishnu.validation.SeqValiInterface.Basic;

import lombok.Data;

@Data
public class LoginForm4 {
  
  
  @NotNull(message="{require_check}", groups = Basic.class)
  @NotBlank(message="{require_check}", groups = Basic.class)
  private String password;
  
  @NotNull(message="{require_check}", groups = Basic.class)
  @NotBlank(message="{require_check}", groups = Basic.class)
  @Size(min = 6, max = 20, message = "{length_check}", groups = Advance.class)
  private String re_password;
  
  public String cardNumber() {
  
    Random random = new SecureRandom();
    int rand = random.nextInt();
    String string1=String.valueOf(rand).substring(1, 7);
    int int1= Integer.parseInt(string1);
    
    Random random1 = new SecureRandom();
    int rand1 = random1.nextInt();
    String string2=String.valueOf(rand1).substring(1, 7);
    int int2= Integer.parseInt(string2);
  
   
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(int1);
    stringBuffer.append(int2);

    String s = stringBuffer.toString();
    return s;

    
  }

}
