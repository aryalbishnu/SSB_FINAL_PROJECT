package com.example.demo.bishnu.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import com.example.demo.bishnu.dto.SaleDto;
import com.lowagie.text.BadElementException;

public interface SaleService {

  public List<SaleDto> dailySale();
  
  public boolean generateDaliySalePdf(List<SaleDto>list, String filePath) throws BadElementException, IOException;
  
  public boolean sendTotalSale(String email, File file)  throws MessagingException ;
  
  public boolean sendSaleNotExit(String email) throws MessagingException;
}
