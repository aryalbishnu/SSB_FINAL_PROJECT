package com.example.demo.bishnu.controller;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.bishnu.entity.BishnuEntity;
import com.example.demo.bishnu.repo.BishnuRepository;
import com.example.demo.bishnu.service.CsvGenerator;
import com.example.demo.bishnu.service.NormalPdfGenerator;
import com.example.demo.bishnu.service.PdfGenerator;
import com.lowagie.text.DocumentException;

@Controller
public class PDFController {
  
  @Autowired
  private BishnuRepository bishnuRepository;
  

  // only for admin all list 
  @GetMapping("/admin/export-to-pdf")
  public void  exportIntoPDF(HttpServletResponse response) throws DocumentException, IOException {
    try {
      response.setContentType("application/pdf");
      
      response.addHeader("Content-Disposition", "attachment; filename=\"account.pdf\"");
      PdfGenerator generator = new PdfGenerator();
      
      generator.generate(bishnuRepository.findAll(), response);
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }
  
  
  //only for admin particular account
  @GetMapping("/admin/normal/export-to-pdf/{id}")
  public void  exportIntoPDFAdmin(@PathVariable("id") int id, HttpServletResponse response) throws DocumentException, IOException {
   BishnuEntity user = this.bishnuRepository.findById(id).get();
   String name = user.getFirstName();
    try {
      response.setContentType("application/pdf");
      response.setCharacterEncoding("UTF-8");
 
      //response.addHeader("Content-Disposition", "attachment; filename=\"adminAccount.pdf\"");
      String headerkey = "Content-Disposition";
      String headerValue = "attachment; filename="+name+ ".pdf";
      response.addHeader(headerkey, headerValue);
      NormalPdfGenerator normalPdfGenerator = new NormalPdfGenerator();
      normalPdfGenerator.normalPdf(user, response);
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }
  
  
  
  // only for admin
  @GetMapping("/admin/export-to-csv")
  public void  exportIntoCSV(HttpServletResponse response) throws IOException {
    response.setContentType("text/csv; charset=UTF-8");
    response.setCharacterEncoding("UTF-8");
  response.addHeader("Content-Disposition", "attachment; filename=\"contact.csv\"");
  CsvGenerator generator = new CsvGenerator();
  generator.writeCsv(bishnuRepository.findAll(), response.getWriter());
  }
  
  //normal
  @GetMapping("/normal/export-to-pdf")
  public void  exportIntoPDFNormal(Principal principal, HttpServletResponse response) throws DocumentException, IOException {
    String email= principal.getName();
    BishnuEntity user=this.bishnuRepository.getUserByUserName(email);
    try {
      response.setContentType("application/pdf;");
      response.addHeader("Content-Disposition", "attachment; filename=\"normalAccount.pdf\"");
      NormalPdfGenerator normalPdfGenerator = new NormalPdfGenerator();
      normalPdfGenerator.normalPdf(user, response);
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }

  
}
