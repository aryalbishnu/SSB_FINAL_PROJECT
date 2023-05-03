package com.example.demo.bishnu.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletResponse;

import com.example.demo.bishnu.entity.BishnuEntity;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class NormalPdfGenerator {

  public void normalPdf(BishnuEntity bishnuEntity, HttpServletResponse response)
  throws DocumentException, IOException{
    
    //create Document 
    Document document = new Document(PageSize.A4);
    
    //write
    PdfWriter.getInstance(document, response.getOutputStream());
    
    document.open();
    
    
    
    Font font = new Font(BaseFont.createFont("KozMinPro-Regular", "UniJIS-UCS2-H", BaseFont.EMBEDDED), 20);    
    font.setColor(CMYKColor.BLUE);
    
    //create font size and color
    Font fontTitle = FontFactory.getFont(FontFactory.COURIER);
    fontTitle.setSize(20);
    
    //generate local time
    LocalDateTime date = LocalDateTime.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    String now = date.format(format);
    Font fontTitle1 = FontFactory.getFont(FontFactory.COURIER);
    fontTitle1.setSize(7);
    
    //create paragraph
    Paragraph paragraph1 = new Paragraph(now, fontTitle1);
    paragraph1.setAlignment(Paragraph.ALIGN_RIGHT);
    
    document.add(paragraph1);
   // Font fontTitle2 = FontFactory.getFont(FontFactory.COURIER);
   // fontTitle2.setSize(20);
   // fontTitle2.setColor(CMYKColor.BLUE);
    
    StringBuffer name= new StringBuffer();
    name.append(bishnuEntity.getFirstName());
    name.append(' ');
    name.append(bishnuEntity.getLastName());
    name.append(' ');
    name.append("さんの情報");
    String nam = name.toString();
    
    Paragraph paragraph = new Paragraph(nam, font);
    paragraph.setAlignment(Paragraph.ALIGN_CENTER);
    
    document.add(paragraph);
    //add particular image
    String img = bishnuEntity.getImage();
    com.lowagie.text.Image image01 = com.lowagie.text.Image.getInstance("C:\\my ssb\\SSB_PROJECT\\target\\classes\\static\\img\\bishnu\\"+ img);
    image01.setAlignment(Paragraph.ALIGN_CENTER);
    document.add(image01);
    
    //create table
    PdfPTable table = new PdfPTable(2);
    
    //set width of table column
    table.setWidthPercentage(100f);
    table.setWidths(new int[] {5,5});
    table.setSpacingBefore(10);
    
    //create table cell header
    Font font1 = new Font(BaseFont.createFont("KozMinPro-Regular", "UniJIS-UCS2-H", BaseFont.EMBEDDED), 10);
    PdfPCell cell = new PdfPCell();
       
    //setting background color and padding of table
  
    cell.setPadding(5);

    //adding cell to table
  
    table.addCell("ID");
    table.addCell(String.valueOf(bishnuEntity.getId()));
    
    table.addCell("FirstName");
    table.addCell(bishnuEntity.getFirstName());
    
    table.addCell("LastName");
    table.addCell(bishnuEntity.getLastName());
    
    table.addCell(new PdfPCell(new Phrase("メイ",font1)));
    table.addCell(new PdfPCell(new Phrase(bishnuEntity.getKataFirstName(),font1)));
    
    table.addCell(new PdfPCell(new Phrase("セイ",font1)));
    table.addCell(new PdfPCell(new Phrase(bishnuEntity.getKataLastName(),font1)));
    
    table.addCell("Gender");
    table.addCell(bishnuEntity.getGender());
    
    table.addCell("Date of Birth");
    StringBuffer sb = new StringBuffer();
    sb.append(bishnuEntity.getDateofyear());
    sb.append('/');
    sb.append(bishnuEntity.getDateofmonth());
    sb.append('/');
    sb.append(bishnuEntity.getDateofday());
    String birth = sb.toString();
    table.addCell(birth);
    
    table.addCell("Married Status");
    table.addCell(bishnuEntity.getMarriedStatus());
    
    table.addCell("Country Name");
    table.addCell(new PdfPCell(new Phrase(bishnuEntity.getCountryStatus(),font1)));
    
    table.addCell("E-Mail");
    table.addCell(bishnuEntity.getEmail());
    
    table.addCell("Mobile Number");
    StringBuffer mobile = new StringBuffer();
    mobile.append(bishnuEntity.getMobile1());
    mobile.append('-');
    mobile.append(bishnuEntity.getMobile2());
    mobile.append('-');
    mobile.append(bishnuEntity.getMobile3());
    String mob = mobile.toString();
    table.addCell(mob);
    
    table.addCell("Tel Number");
    StringBuffer tel = new StringBuffer();
    tel.append(bishnuEntity.getTel1());
    tel.append('-');
    tel.append(bishnuEntity.getTel2());
    tel.append('-');
    tel.append(bishnuEntity.getTel3());
    String telNumber= tel.toString();
    table.addCell(telNumber);
    
    table.addCell("Zip Code");
    table.addCell(bishnuEntity.getZipCode());
    
    table.addCell("Address");
    StringBuffer address= new StringBuffer();
    address.append(bishnuEntity.getAddress1());
    address.append(' ');
    address.append(bishnuEntity.getAddress3());
    String add = address.toString();
    table.addCell(add);
    
    table.addCell(new PdfPCell(new Phrase("アドレス",font1)));
    StringBuffer address2 = new StringBuffer();
    address2.append(bishnuEntity.getAddress1());
    address2.append(' ');
    address2.append(bishnuEntity.getAddress3());
    String addre= address2.toString(); 
    table.addCell(new PdfPCell(new Phrase(addre,font1)));
    
    table.addCell("Living Situation");
    table.addCell(bishnuEntity.getLivingSituation());
    
    table.addCell("Mortage Loan");
    table.addCell(bishnuEntity.getMortageLoan());
    
    table.addCell("Driving License");
    table.addCell(bishnuEntity.getDrivingLicense());
    
    table.addCell("License Number");
    table.addCell(bishnuEntity.getLicenseNumber());
    
    table.addCell("Card Type");
    table.addCell(bishnuEntity.getCardType());
    
    table.addCell("Card Number");
    table.addCell(bishnuEntity.getCardNumber());
    
    table.addCell("Role");
    table.addCell(bishnuEntity.getRole());
    
    table.addCell("Create Time");
    table.addCell(bishnuEntity.getCreatTime());
    
    table.addCell("Update Time");
    table.addCell(bishnuEntity.getUpdateTime());

    document.add(table);
  
    

    document.close();

  }

}
