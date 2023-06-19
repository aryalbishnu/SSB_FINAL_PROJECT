package com.example.demo.bishnu.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.bishnu.dto.SaleDto;
import com.example.demo.bishnu.mapper.SaleMapper;
import com.example.demo.bishnu.model.GmailModel;
import com.example.demo.bishnu.repo.ProductRepo;
import com.example.demo.bishnu.service.EmailService;
import com.example.demo.bishnu.service.SaleService;
import com.itextpdf.text.Element;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
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
public class SaleServiceImpl implements SaleService{
  
  @Autowired
  private SaleMapper saleMapper;
  
  @Autowired
  private ProductRepo productRepo;
  
  @Autowired
  private EmailService emailService;

  // 一日分の売上
  @Override
  public List<SaleDto> dailySale() {
    // one day ago current sale
    LocalDate currentDate = LocalDate.now();
    LocalDate oneDayAgo = currentDate.minusDays(1);
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String oneDayAgoString = oneDayAgo.format(formatter);
   List<SaleDto>totalSale = saleMapper.totalSale(oneDayAgoString);
    return totalSale;
  }

  // 一日分の売り上げをpdf file に作成
  @Override
  public boolean generateDaliySalePdf(List<SaleDto> list, String filePath) throws BadElementException, IOException {
    boolean success = false;
    //create Document 
    Document document = new Document(PageSize.A4);
    
    
    //write
    PdfWriter.getInstance(document, new FileOutputStream(filePath));
    
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
    
    Paragraph paragraph = new Paragraph("Total Sale Of Product",font2);
    paragraph.setAlignment(Paragraph.ALIGN_CENTER);
    document.add(paragraph);
    
    //create table
    PdfPTable table = new PdfPTable(7);
    
    //set width of table column
    table.setWidthPercentage(100f);
    table.setWidths(new int[] {2,4,4,5,4,5,4});
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
   
    cell.setPhrase(new Phrase("Price",font));
    table.addCell(cell);
    cell.setPhrase(new Phrase("Date", font));
    table.addCell(cell);

    cell.setPhrase(new Phrase("UserId", font));
    table.addCell(cell);
    cell.setPhrase(new Phrase("ProductId", font));
    table.addCell(cell);
    
    //List of account Iterating
    int totalAmount = 0;
    for (SaleDto saleDto:list) {
      table.addCell(String.valueOf(saleDto.getSale_Id()));
      
      //image set
      String img = saleDto.getProduct_image();
      com.lowagie.text.Image image01 = com.lowagie.text.Image.getInstance("C:\\my ssb\\SSB_PROJECT\\target\\classes\\static\\img\\bishnu\\product\\"+ img);
      image01.scaleAbsolute(30, 30);
      table.addCell(image01);
 
      table.addCell(String.valueOf(saleDto.getSale_Name()));

      table.addCell(String.valueOf(saleDto.getSale_Price()));
      
      table.addCell(String.valueOf(saleDto.getSale_Date()));
      
      table.addCell(String.valueOf(saleDto.getUser_Id()));
      
      table.addCell(String.valueOf(saleDto.getProduct_Id()));
       
      totalAmount = totalAmount + saleDto.getSale_Price();
     
    }
    //adding create table
    document.add(table);
    
    // adding total amount of sale and quantity
    int toalQuantity = list.size();
    
   //create table
    PdfPTable table1 = new PdfPTable(2);
    PdfPCell cell1 = new PdfPCell();
    //set width of table column
    table1.setWidthPercentage(30f);
    table1.setWidths(new int[] {6,3});
    table1.setSpacingBefore(5);
    cell1.setPadding(5);
    
    // Set table alignment to the left
    table1.setHorizontalAlignment(Element.ALIGN_RIGHT);
 
    //adding cell to table
    cell1.setPhrase(new Phrase("Total Amount"));
    table1.addCell(cell1);
    
    cell1.setPhrase(new Phrase(String.valueOf(totalAmount)));
    table1.addCell(cell1);

    //List of account Iterating
      table1.addCell(String.valueOf("Total Quantity"));

      table1.addCell(String.valueOf(toalQuantity));
      

    //adding create table
    document.add(table1);

    document.close();

    success = true;
    return success;
  }

  // send email by 1 day total sale in ssb company
  public boolean sendTotalSale(String email, File file) throws MessagingException {
    
    GmailModel gmailModel = new GmailModel();
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String timestamp = now.format(formatter);
    gmailModel.setMessage("Total sale of  ： " + timestamp);
    gmailModel.setSubject("CodersArea: Confirmation");
    gmailModel.setTo(email);
    return  emailService.fileMessageSend(gmailModel.getSubject(), gmailModel.getMessage(), gmailModel.getTo(), file);

  }
  // send email by 1 day if sale is not exit
  public boolean sendSaleNotExit(String email) throws MessagingException {
    GmailModel gmailModel = new GmailModel();
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String timestamp = now.format(formatter);
    gmailModel.setMessage("YesterDay sale is empty  ： " + timestamp);
    gmailModel.setSubject("CodersArea: Confirmation");
    gmailModel.setTo(email);
    return  emailService.sendGmail(gmailModel.getSubject(), gmailModel.getMessage(), email);
  }
    
  }


