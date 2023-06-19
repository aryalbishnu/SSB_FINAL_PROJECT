package com.example.demo.bishnu.controller;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.bishnu.dto.SaleDto;
import com.example.demo.bishnu.service.SaleService;

@Controller

public class BatchiController {
  
  @Autowired
  private SaleService saleService;
  
  
  //@Scheduled(cron = "0 27 17 * * ?")
  @GetMapping("/hello")
  @ResponseBody
  public String helloBy() {
  List<SaleDto>saleDtos = this.saleService.dailySale();
  if(saleDtos.size()>0) {
  try {
    // create Save to Server side 
    LocalDateTime now = LocalDateTime.now();
    // Format the date and time as a string to be used in the file name
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    String timestamp = now.format(formatter);
    String path = "C:\\my ssb\\SSB_PROJECT\\target\\classes\\static\\img\\bishnu\\totalSalePdf\\";
    String file = "sale_report_" + timestamp + ".pdf";
    String filePath= path + file;
    boolean f = this.saleService.generateDaliySalePdf(saleDtos, filePath);
    // if file create success after email process
    if(f) {
     // Perform further operations with the server-side path
      File serverSidePath = new File(filePath);
      // send email location
      String email = "ariyalbishnu@gmail.com";
      this.saleService.sendTotalSale(email, serverSidePath);
    } else {
      // only message send by 
      String email = "ariyalbishnu@gmail.com";
      this.saleService.sendSaleNotExit(email);
    }
    
    
    
    
  } catch (Exception e) {
    e.printStackTrace();
  }
  }
    return "this is my testing phase";
  }
/*
  @Scheduled(cron = "0 53 17 * * ?") // Run at 17:39 (5:39 PM) every day
  public void delayedHello() {
 
    // Code to be executed after 1 minute
    System.out.println("Delayed hello after 1 minute.................." + LocalTime.now());
  }
*/
   
 }


