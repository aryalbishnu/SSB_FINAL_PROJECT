package com.example.demo.bishnu.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "createUser")
public class BishnuEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  
  @Column(name = "card_Type")
  private String cardType;
  
  @Column(name = "first_Name")
  private String firstName;
  
  @Column(name = "last_Name")
  private String lastName;
  
  @Column(name = "kata_First_Name")
  private String kataFirstName;
  
  @Column(name = "kata_Last_Name")
  private String kataLastName;
  
  @Column(name = "gender")
  private String gender;
  
  @Column(name = "date_of_year")
  private String dateofyear;
  
  @Column(name = "date_of_month")
  private String dateofmonth;
  
  @Column(name = "date_of_day")
  private String dateofday;
  
  @Column(name = "married_Status")
  private String marriedStatus;
  
  @Column(name = "country_Status")
  private String countryStatus;
  
  @Column(name = "email")
  private String email;
  
  @Column(name = "mobile1")
  private String mobile1;
  
  @Column(name = "mobile2")
  private String mobile2;
  
  @Column(name = "mobile3")
  private String mobile3;
  
  @Column(name = "tel1")
  private String tel1;
  
  @Column(name = "tel2")
  private String tel2;
  
  @Column(name = "tel3")
  private String tel3;
  
  @Column(name = "zip_Code")
  private String zipCode;
  
  @Column(name = "address1")
  private String address1;
  
  @Column(name = "address2")
  private String address2;
  
  @Column(name = "address3")
  private String address3;
  
  @Column(name = "living_Situation")
  private String livingSituation;
  
  @Column(name = "mortage_Loan")
  private String mortageLoan;
  
  @Column(name = "driving_License")
  private String drivingLicense;
  
  @Column(name = "license_Number")
  private String licenseNumber;
  
  @Column(name = "card_Number")
  private String cardNumber;
  
  @Column(name = "password")
  private String password;
  
  @Column(name = "creat_Time")
  private String creatTime;
  
  @Column(name = "update_Time")
  private String updateTime;
  
  @Column(name = "role")
  private String role;
  
  @Column(name = "account_non_locked")
  private boolean accountNonLocked;
  
  @Column(name = "failed_attempt")
  private int failedAttempt;
  
  @Column(name = "lock_time")
  private Date lockTime;
  
  @Column(name = "image")
  private String image;


}
