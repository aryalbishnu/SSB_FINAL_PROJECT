package com.example.demo.bishnu.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

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

@Service
public class PdfGenerator {

  
  public void generate(List<BishnuEntity>list,  HttpServletResponse response)
  throws DocumentException, IOException{
    
    
    //create Document 
    Document document = new Document(PageSize.A4);
    
    //write
    PdfWriter.getInstance(document, response.getOutputStream());
    
    document.open();
    
    //create font size and color
    Font fontTitle = FontFactory.getFont(FontFactory.COURIER);
    fontTitle.setSize(20);
    
    Font font2 = new Font(BaseFont.createFont("KozMinPro-Regular", "UniJIS-UCS2-H", BaseFont.EMBEDDED), 20);    
    font2.setColor(CMYKColor.BLUE);
    
    
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
    
    Paragraph paragraph = new Paragraph("List of Account",font2);
    paragraph.setAlignment(Paragraph.ALIGN_CENTER);
    document.add(paragraph);
    
    //create table
    PdfPTable table = new PdfPTable(7);
    
    //set width of table column
    table.setWidthPercentage(100f);
    table.setWidths(new int[] {1,4,4,5,4,5,4});
    table.setSpacingBefore(5);
    
    //create table cell header
    Font font1 = new Font(BaseFont.createFont("KozMinPro-Regular", "UniJIS-UCS2-H", BaseFont.EMBEDDED), 10);
    PdfPCell cell = new PdfPCell();
    
    //setting background color and padding of table
    cell.setBackgroundColor(CMYKColor.BLUE);
    cell.setPadding(5);
    
    //setting font style and size
    
    Font font = FontFactory.getFont(FontFactory.TIMES_ITALIC);
    font.setColor(CMYKColor.WHITE);
    
    
    //adding cell to table
    cell.setPhrase(new Phrase("ID", font));
    table.addCell(cell);
    
    cell.setPhrase(new Phrase("Image", font));
    table.addCell(cell);
    
    cell.setPhrase(new Phrase("Name", font));
    table.addCell(cell);
   
    cell.setPhrase(new Phrase("Email",font));
    table.addCell(cell);
    cell.setPhrase(new Phrase("Role", font));
    table.addCell(cell);

    cell.setPhrase(new Phrase("Address", font));
    table.addCell(cell);
    cell.setPhrase(new Phrase("Mobile", font));
    table.addCell(cell);
    
    //List of account Iterating
    for(BishnuEntity bishnuEntity: list) {
      
      table.addCell(String.valueOf(bishnuEntity.getId()));
      
      //image set
      String img = bishnuEntity.getImage();
      com.lowagie.text.Image image01 = com.lowagie.text.Image.getInstance("C:\\my ssb\\SSB_PROJECT\\target\\classes\\static\\img\\bishnu\\"+ img);
      image01.scaleAbsolute(30, 30);
      table.addCell(image01);
      
      //find name
      StringBuffer buffer = new StringBuffer();
      buffer.append(bishnuEntity.getFirstName());
      buffer.append(' ');
      buffer.append(bishnuEntity.getLastName());
      String name  = buffer.toString();
      table.addCell(name);

      table.addCell(bishnuEntity.getEmail());
   
      table.addCell(bishnuEntity.getRole());
     
      //find address
      StringBuffer add = new StringBuffer();
      add.append(bishnuEntity.getAddress1());
      add.append(' ');
      add.append(bishnuEntity.getAddress3());
      String address = add.toString();
      table.addCell(new PdfPCell(new Phrase(address,font1)));
      
      //find mobile number
      StringBuffer sbBuffer = new StringBuffer();
      sbBuffer.append(bishnuEntity.getMobile1());
      sbBuffer.append('-');
      sbBuffer.append(bishnuEntity.getMobile2());
      sbBuffer.append('-');
      sbBuffer.append(bishnuEntity.getMobile3());
      String mobile = sbBuffer.toString();
     
      table.addCell(mobile);
    }
    
    //adding create table
    
    document.add(table);
    document.close();

  }

}