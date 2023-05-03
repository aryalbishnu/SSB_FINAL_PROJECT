package com.example.demo.bishnu.model;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.example.demo.bishnu.validation.SeqValiInterface.Advance;
import com.example.demo.bishnu.validation.SeqValiInterface.Basic;

import lombok.Data;

@Data
public class LoginForm2 {
  
  @Pattern(regexp="^([\\w\\-\\.]+)@((\\[([0-9]{1,3}\\.){3}[0-9]{1,3}\\])|(([\\w\\-]+\\.)+)([a-zA-Z]{2,4}))$", message = "{email_check}", groups = Advance.class)
  @NotBlank(message = "{require_check}", groups = Basic.class)
  private String email;
  
  @NotNull(message="{require_check}", groups = Basic.class)
  @NotBlank(message = "{require_check}", groups = Basic.class)
  @Size(max = 3, min = 3, message = "{length_check_number}", groups = Advance.class)
  private String mobile1;
  
  @NotNull(message="{require_check}", groups = Basic.class)
  @NotBlank(message = "{require_check}", groups = Basic.class)
  @Size(max = 4, min = 4, message = "{length_check_number}", groups = Advance.class)
  private String mobile2;
  
  @NotNull(message="{require_check}", groups = Basic.class)
  @NotBlank(message = "{require_check}", groups = Basic.class)
  @Size(max = 4, min = 4, message = "{length_check_number}", groups = Advance.class)
  private String mobile3;
  
  @NotNull(message="{require_check}", groups = Basic.class)
  @NotBlank(message = "{require_check}", groups = Basic.class)
  @Size(max = 2, min = 2, message = "{length_check_number}", groups = Advance.class)
  private String tel1;
  
  @NotNull(message="{require_check}", groups = Basic.class)
  @NotBlank(message = "{require_check}", groups = Basic.class) 
  @Size(max = 4, min = 4, message = "{length_check_number}", groups = Advance.class)
  private String tel2;
  
  @NotNull(message="{require_check}", groups = Basic.class)
  @NotBlank(message = "{require_check}", groups = Basic.class)
  @Size(max = 4, min = 4, message = "{length_check_number}", groups = Advance.class)
  private String tel3;
  
  

}
