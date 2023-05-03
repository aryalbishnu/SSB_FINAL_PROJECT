package com.example.demo.bishnu.model;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.demo.bishnu.validation.SeqValiInterface.Advance;
import com.example.demo.bishnu.validation.SeqValiInterface.Basic;

import lombok.Data;

@Data
public class LoginForm3 {
  
  @NotBlank(message="{require_check}", groups = Basic.class)
  @Size(max = 7, min = 7, message="{length_post_check}", groups = Advance.class)
  private String zipCode;
  
  @NotNull(message="{require_check}", groups = Basic.class)
  @NotBlank(message="{require_check}", groups = Basic.class)
  private String address1;
  
  @NotNull(message="{require_check}", groups = Basic.class)
  @NotBlank(message="{require_check}", groups = Basic.class)
  private String address2;
  
  @NotNull(message="{require_check}", groups = Basic.class)
  @NotBlank(message="{require_check}", groups = Basic.class)
  private String address3;
  
  @NotNull(message="{select_check}", groups = Basic.class)
  @NotBlank(message="{require_check}", groups = Basic.class)
  private String livingSituation;
  
  @NotBlank(message="{require_check}", groups = Basic.class)
  private String mortageLoan;
  
  @NotBlank(message="{require_check}", groups = Basic.class)
  private String drivingLicense;

 // @NotBlank(message="{require_check}", groups = Basic.class)
  private String licenseNumber;

  
  //living Condition
  public static Map<String, String>getLivingCondition(){
    Map<String, String>living = new HashMap<>();
    living.put("Self/Family Owned", "Self/Family Owned");
    living.put("Company housing", "Company housing");
    living.put("Rent", "Rent");
    living.put("Dormitory/boarding house", "Dormitory/boarding house");
    living.put("Others", "Others");
    return living;
  }
  
  //mortgae loan
  public static Map<String, String>getByMortgageLoan(){
    Map<String, String>loan = new HashMap<>();
    loan.put("Yes", "Yes");
    loan.put("No", "No");
    return loan;
  }
  
  //Role of User
  public static Map<String, String>getByRole(){
    Map<String, String>role = new HashMap<>();
    role.put("ROLE_ADMIN", "ADMIN");
    role.put("ROLE_NORMAL", "NORMAL");
    return role;
  }
  
  //driving license
  public static Map<String, String>gerByLicense(){
    Map<String, String>license = new HashMap<>();
    license.put("Yes", "Yes");
    license.put("No", "No");
    return license;
  }
  
}
