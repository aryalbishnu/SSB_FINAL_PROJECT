package com.example.demo.bishnu.service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.example.demo.bishnu.entity.BishnuEntity;
import com.example.demo.bishnu.repo.BishnuRepository;



public class CsvGenerator {
  
  @Resource
  private BishnuRepository bishnuRepository;
  
  public void writeCsv(List<BishnuEntity> list, Writer writer) {
   
   
    try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
      
      
      
      csvPrinter.printRecord("ID", "FirstName", "LastName", "メイ", "セイ", "Gender", "Date of Birth","Married Situation",
          "Role Situation", "E-mail", "Country Name", "Mobile Number","Tel Number", "Zip Code", "Address", "アドレス", 
          "living Situation", "Mortgage and rent", "Driving License", "License Number", "Card Type", "Card Number");
     
      for (BishnuEntity b: list) {
        
        //find date of birth
        StringBuffer sb = new StringBuffer();
        sb.append(b.getDateofyear());
        sb.append('/');
        sb.append(b.getDateofmonth());
        sb.append('/');
        sb.append(b.getDateofday());
        String dateofBirth = sb.toString();
        
        //find mobile number
        StringBuffer mobile = new StringBuffer();
        mobile.append(b.getMobile1());
        mobile.append('-');
        mobile.append(b.getMobile2());
        mobile.append('-');
        mobile.append(b.getMobile3());
        String mobileNumber = mobile.toString();
        
        //find Tel Number
        StringBuffer tel = new StringBuffer();
        tel.append(b.getTel1());
        tel.append('-');
        tel.append(b.getTel2());
        tel.append('-');
        tel.append(b.getTel3());
        String telNumber = tel.toString();
        
        //address
        StringBuffer add1= new StringBuffer();
        add1.append(b.getAddress1());
        add1.append(' ');
        add1.append(b.getAddress3());
        String address1= add1.toString();
        
        //アドレス
        StringBuffer add2 = new StringBuffer();
        add2.append(b.getAddress2());
        add2.append(' ');
        add2.append(b.getAddress3());
        String address2 = add2.toString();
        
        
        csvPrinter.printRecord(b.getId(), b.getFirstName(), b.getLastName(), b.getKataFirstName(),
            b.getKataLastName(), b.getGender(), dateofBirth, b.getMarriedStatus(),b.getRole(), b.getEmail(),
            b.getCountryStatus(), mobileNumber, telNumber, b.getZipCode(), address1, address2, b.getLivingSituation(),
            b.getMortageLoan(), b.getDrivingLicense(), b.getLicenseNumber(), b.getCardType(), b.getCardNumber());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
