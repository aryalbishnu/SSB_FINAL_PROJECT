package com.example.demo.bishnu.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.example.demo.bishnu.validation.SeqValiInterface.Advance;
import com.example.demo.bishnu.validation.SeqValiInterface.Basic;

import lombok.Data;

@Data
public class LoginForm1 {

  @NotNull(message="{require_check}", groups = Basic.class)
  @NotBlank(message="{require_check}", groups = Basic.class)
  @Size(min = 4, max = 20, message = "{length_check}", groups = Advance.class)
  private String firstName;
  
  @NotNull(message="{require_check}", groups = Basic.class)
  @NotBlank(message="{require_check}", groups = Basic.class)
  @Size(min = 4, max = 20, message = "{length_check}", groups = Advance.class)
  private String lastName;
  
  @NotNull(message="{require_check}", groups = Basic.class)
  @NotBlank(message="{require_check}", groups = Basic.class)
  @Pattern(regexp="^[\\u30A0-\\u30FF]+$", message = "{katakana_check}", groups = Advance.class)
  private String kataFirstName;
  
  @NotNull(message="{require_check}", groups = Basic.class)
  @NotBlank(message="{require_check}", groups = Basic.class)
  @Pattern(regexp="^[\\u30A0-\\u30FF]+$", message = "{katakana_check}", groups = Advance.class)
  private String kataLastName;

  @NotNull(message="{select_check}", groups = Advance.class )
  @NotBlank(message="{require_check}", groups = Basic.class)
  private String gender;
  
  @NotNull(message="{select_check}", groups = Basic.class)
  @NotBlank(message="{require_check}", groups = Basic.class)
  private String dateofyear;
  
  @NotNull(message="{select_check}", groups = Basic.class)
  @NotBlank(message="{require_check}", groups = Basic.class)
  private String dateofmonth;
  
  @NotNull(message="{require_check}", groups = Basic.class)
  @NotBlank(message="{require_check}", groups = Basic.class)
  private String dateofday;
  
  @NotNull(message="{select_check}", groups = Advance.class)
  @NotBlank(message="{require_check}", groups = Basic.class)
  private String marriedStatus;
  
  @NotNull(message="{select_check}", groups = Advance.class)
  @NotBlank(message="{require_check}", groups = Basic.class)
  private String countryStatus;
 
  
  //date
  public static Map<String, String> getDay() {
    Map<String, String> day = new LinkedHashMap<>();
    for (int i = 1; i <= 31; i++) {
      day.put(String.valueOf(i), String.valueOf(i));
    }
    return day;
  }
  
  //month
  public static Map<String, String>getMonth(){
    Map<String, String>month = new LinkedHashMap<>();
    for(int i= 1; i<=12; i++) {
      month.put(String.valueOf(i), String.valueOf(i));
    }
    return month;
  }
  
  //year
  public static Map<String, String>yearMap(){
    Map<String, String>year = new LinkedHashMap<>();
    LocalDate date= LocalDate.now();
    int currentYear= date.getYear()-20;
   
    for(int i= 1970; i<=currentYear; i++) {
      year.put(String.valueOf(i), String.valueOf(i));
    }
    return year;
  }
  
  //gender
   public static Map<String, String>getByGender(){
     Map<String, String>sex = new HashMap<>();
     sex.put("Male", "Male");
     sex.put("Female", "Female");
    return sex;
   }
   
   //marriedStatus
   public static Map<String, String>getByMarriedStatus(){
     Map<String, String>marriedStatus = new HashMap<>();
     marriedStatus.put("UnMarried", "UnMarried");
     marriedStatus.put("Married", "Married");
     marriedStatus.put("Divorce", "Divorce");
     return marriedStatus;

   }
   
   //Country Name
   public static Map<String, String>getByCountryName(){
     Map<String, String>country = new HashMap<>();
     String[] countryCodes = Locale.getISOCountries();

     for (String countryCode : countryCodes) {

         Locale obj = new Locale("", countryCode);
         country.put(obj.getDisplayCountry(), obj.getDisplayCountry());
     }
     
     return country;
   }

}
