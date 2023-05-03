package com.example.demo.bishnu.service.impl;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.bishnu.entity.BishnuEntity;
import com.example.demo.bishnu.entity.CardEntity;
import com.example.demo.bishnu.model.BalanceTransfer;
import com.example.demo.bishnu.model.ChargeAmount;
import com.example.demo.bishnu.model.GmailModel;
import com.example.demo.bishnu.model.PaymentModel;
import com.example.demo.bishnu.model.PinNumberSet;
import com.example.demo.bishnu.repo.BishnuRepository;
import com.example.demo.bishnu.repo.CardEntityRepo;
import com.example.demo.bishnu.service.EmailService;

@Service
public class MainMethod {
  
  @Autowired
  private BishnuRepository bishnuRepository;
  
  @Autowired
  private CardEntityRepo cardEntityRepo;
  
  @Autowired
  private EmailService emailService;
  
  //Set pin number send to message
public void setPinNumber(Principal principal, PinNumberSet pinNumberSet) {
  String userName = principal.getName();
  BishnuEntity bishnuEntity= this.bishnuRepository.getUserByUserName(userName);
  GmailModel gmailModel = new GmailModel();
  gmailModel.setMessage("<div style='color:blue'><h1>Your pin number is："+pinNumberSet.getPinNumber() + "</h1></div>");
  gmailModel.setSubject("CodersArea: Confirmation");
  gmailModel.setTo(bishnuEntity.getEmail());
  emailService.sendGmail(gmailModel.getSubject(),gmailModel.getMessage(),  gmailModel.getTo());
}
  
  //Charge Amount Message Send
public void chargeMethod(Principal principal, ChargeAmount chargeAmount) {
  String userName = principal.getName();
  BishnuEntity bishnuEntity= this.bishnuRepository.getUserByUserName(userName);
  CardEntity cardEntity = this.cardEntityRepo.getCardEntityByCardNumber(bishnuEntity.getCardNumber());
  GmailModel gmailModel = new GmailModel();
  gmailModel.setMessage("<div style='color:blue'><h1>Your Charge Amount is："+chargeAmount.getAmount() + "</h1>"
      + "<p>your current balance is:"+cardEntity.getBalance() +"</p></div>");
  gmailModel.setSubject("CodersArea: Confirmation");
  gmailModel.setTo(bishnuEntity.getEmail());
  emailService.sendGmail(gmailModel.getSubject(),gmailModel.getMessage(),  gmailModel.getTo());
}

//Balance inquery check balance
public void balanceCheckMethod(Principal principal) {
  String userName = principal.getName();
  BishnuEntity bishnuEntity= this.bishnuRepository.getUserByUserName(userName);
  CardEntity cardEntity = this.cardEntityRepo.getCardEntityByCardNumber(bishnuEntity.getCardNumber());
  GmailModel gmailModel = new GmailModel();
  gmailModel.setMessage("<div style='color:blue'><h1>Your Current Balance is ："+ cardEntity.getBalance() + "</h1></div>");
  gmailModel.setSubject("CodersArea: Confirmation");
  gmailModel.setTo(bishnuEntity.getEmail());
  emailService.sendGmail(gmailModel.getSubject(),gmailModel.getMessage(),  gmailModel.getTo());
}

//Balance Transfer send from message
public void balanceTransferFrom(Principal principal, BalanceTransfer balanceTransfer) {
  String userName = principal.getName();
  BishnuEntity bishnuEntity= this.bishnuRepository.getUserByUserName(userName);
  CardEntity cardEntity = this.cardEntityRepo.getCardEntityByCardNumber(bishnuEntity.getCardNumber());
  
  CardEntity cardEntity2 = this.cardEntityRepo.getCardEntityByCardNumber(balanceTransfer.getToCardNumber());
  BishnuEntity bishnuEntity2 = this.bishnuRepository.getUserByCardNumber(cardEntity2.getCardNumber());
  GmailModel gmailModel = new GmailModel();
  gmailModel.setMessage("<div style='color:blue'><h1>Balance Transfer is ："+ balanceTransfer.getAmount() + "</h1>"
      + "<p>Balance transfer to " + bishnuEntity2.getEmail() +" </p>"
      + "<p>your current balance is: "+ cardEntity.getBalance() +"</p></div>");
  gmailModel.setSubject("CodersArea: Confirmation");
  gmailModel.setTo(bishnuEntity.getEmail());
  emailService.sendGmail(gmailModel.getSubject(),gmailModel.getMessage(),  gmailModel.getTo());
}

//Balance Transfer send from message
public void balanceTransferTo(Principal principal, BalanceTransfer balanceTransfer) {
String userName = principal.getName();
BishnuEntity bishnuEntity= this.bishnuRepository.getUserByUserName(userName);
//CardEntity cardEntity = this.cardEntityRepo.getCardEntityByBishnuEntity(bishnuEntity.getId());

CardEntity cardEntity2 = this.cardEntityRepo.getCardEntityByCardNumber(balanceTransfer.getToCardNumber());
BishnuEntity bishnuEntity2 = this.bishnuRepository.getUserByCardNumber(cardEntity2.getCardNumber());
GmailModel gmailModel = new GmailModel();
gmailModel.setMessage("<div style='color:blue'><h1>Balance Receive is ："+ balanceTransfer.getAmount() + "</h1>"
    + "<p>Balance transfer from " + bishnuEntity.getEmail() +" </p>"
    + "<p>your current balance is: "+ cardEntity2.getBalance() +"</p></div>");
gmailModel.setSubject("CodersArea: Confirmation");
gmailModel.setTo(bishnuEntity2.getEmail());
emailService.sendGmail(gmailModel.getSubject(),gmailModel.getMessage(),  gmailModel.getTo());
}

//payment of order
public void paymentOrder(Principal principal, PaymentModel paymentModel) {
String userName = principal.getName();
BishnuEntity bishnuEntity= this.bishnuRepository.getUserByUserName(userName);
CardEntity cardEntity = this.cardEntityRepo.getCardEntityByCardNumber(bishnuEntity.getCardNumber());
GmailModel gmailModel = new GmailModel();
gmailModel.setMessage("<div style='color:blue'><h1>Bill Payment amount is ："+ paymentModel.getAmount() + "</h1><br>"
    + "<p>your current balance is: "+ cardEntity.getBalance() +"</p></div>");
gmailModel.setSubject("CodersArea: Confirmation");
gmailModel.setTo(bishnuEntity.getEmail());
emailService.sendGmail(gmailModel.getSubject(),gmailModel.getMessage(),  gmailModel.getTo());
}
}