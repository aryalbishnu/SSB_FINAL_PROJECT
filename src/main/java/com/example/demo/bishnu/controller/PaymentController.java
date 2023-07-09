package com.example.demo.bishnu.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.bishnu.entity.BishnuEntity;
import com.example.demo.bishnu.entity.CardEntity;
import com.example.demo.bishnu.helper.Message;
import com.example.demo.bishnu.mapper.SaleMapper;
import com.example.demo.bishnu.model.BalanceInquery;
import com.example.demo.bishnu.model.BalanceTransfer;
import com.example.demo.bishnu.model.ChargeAmount;
import com.example.demo.bishnu.model.PinNumberSet;
import com.example.demo.bishnu.repo.BishnuRepository;
import com.example.demo.bishnu.repo.CardEntityRepo;
import com.example.demo.bishnu.service.CreatService;
import com.example.demo.bishnu.service.impl.MainMethod;

@Controller
@RequestMapping("/bishnu/user/")
public class PaymentController {
  
  @Autowired 
  private BishnuRepository bishnuRepository;
  
  @Autowired
  private CardEntityRepo cardEntityRepo;
  
  @Autowired
  private CreatService creatService;
  
  @Autowired
  private MainMethod mainMethod;
  
  @Autowired
  private SaleMapper saleMapper;
  
  //common data
  @ModelAttribute
  public void addCommonData(Model model, Principal principal) {
    String userName = principal.getName();

    //get the user using userName(Email)
   BishnuEntity user=this.bishnuRepository.getUserByUserName(userName);
    model.addAttribute("user", user);
    // List of product in add cart find by principle
    int userId = user.getId();
    int addCart = this.saleMapper.countAddCart(userId);
    model.addAttribute("addCart", addCart); 
  }
  
  //open payment page click of payment
  @GetMapping("/payment")
  public String paymentBy(Principal principal, Model model) {
    model.addAttribute("title", "payment page");
    String userName= principal.getName();
    BishnuEntity data  = this.bishnuRepository.getUserByUserName(userName);
    model.addAttribute("data", data);
    try {
    CardEntity cardEntity = this.cardEntityRepo.getCardEntityByCardNumber(data.getCardNumber());
    if(!(cardEntity == null)) {
      model.addAttribute("cardEntity", cardEntity);
    }
    }catch (Exception e) {
      e.printStackTrace();
    }
    return "bishnu/normal/payment";
  }
  
  //set pin Numbet of your account
  @PostMapping("/pinNumberSet")
  public String pinNumberSetBy(@Valid PinNumberSet pinNumberSet, BindingResult result, 
      Model model, HttpSession session, Principal principal) {
    //check validation
    if(result.hasErrors()) {
      session.setAttribute("message", new Message("Something is wrong ...", "danger"));
      return "redirect:/bishnu/user/payment";
    }
    
    //check pin number 
    if(!pinNumberSet.getPinNumber().equals(pinNumberSet.getRePinNumber())) {
      session.setAttribute("message", new Message("暗証番号を同じ入力してください。 ...", "danger"));
      return "redirect:/bishnu/user/payment";
    }
    
    String userName= principal.getName();
    BishnuEntity  bishnuEntity = this.bishnuRepository.getUserByUserName(userName);
    String cardNumber=bishnuEntity.getCardNumber();
    //Check card Number
   if(!cardNumber.equals(pinNumberSet.getCardNumber())) {
     session.setAttribute("message", new Message("カード番号を間違えています ...", "danger"));
     return "redirect:/bishnu/user/payment";
   }
    
  creatService.insertCard(pinNumberSet, principal);
  this.mainMethod.setPinNumber(principal, pinNumberSet);
  session.setAttribute("message", new Message("Success of set pin number ...", "success"));
    return "redirect:/bishnu/user/payment";
  }
  
  //charge of your blance in account
  @PostMapping("/charge")
  public String chargeBy(@Valid ChargeAmount chargeAmount, BindingResult result, Model model, HttpSession session, Principal principal) {
    
    //check validation
    if(result.hasErrors()) {
      session.setAttribute("message", new Message("Something is wrong ...", "danger"));
      return "redirect:/bishnu/user/payment";
    }
    
    //check pin number and account number
    String userName= principal.getName();
    BishnuEntity bishnuEntity = this.bishnuRepository.getUserByUserName(userName);
    String cardNum = bishnuEntity.getCardNumber();
    CardEntity cardEntity = this.cardEntityRepo.getCardEntityByCardNumber(cardNum);

    String pinNumber = cardEntity.getPinNumber();
    String cardNumber = cardEntity.getCardNumber();
    if(!pinNumber.equals(chargeAmount.getPinNumber())|| !cardNumber.equals(chargeAmount.getCardNumber())) {
      session.setAttribute("message", new Message("暗証番号とカード番号一致されていません ...", "danger"));
      return "redirect:/bishnu/user/payment";
    }
   
    cardEntity.setBalance(cardEntity.getBalance() + chargeAmount.getAmount());
    this.cardEntityRepo.save(cardEntity);
    mainMethod.chargeMethod(principal, chargeAmount);
    session.setAttribute("message", new Message("Success of charge your account ...", "success"));
    return "redirect:/bishnu/user/payment";
  }
  
  //Balance inquery of your account
  
  @PostMapping("/balanceInquery")
  public String balanceInqueryBy(@Valid BalanceInquery balanceInquery, BindingResult result, Model model, HttpSession session, Principal principal) {
    //check validation
    if(result.hasErrors()) {
      session.setAttribute("message", new Message("Something is wrong ...", "danger"));
      return "redirect:/bishnu/user/payment";
    }
    
    //check pin number and card number
    String userName = principal.getName();
    BishnuEntity bishnuEntity = this.bishnuRepository.getUserByUserName(userName);
    CardEntity cardEntity = this.cardEntityRepo.getCardEntityByCardNumber(bishnuEntity.getCardNumber());
    String cardNumber = cardEntity.getCardNumber();
    String pinNumber = cardEntity.getPinNumber();
    if(!cardNumber.equals(balanceInquery.getCardNumber()) || !pinNumber.equals(balanceInquery.getPinNumber())) {
      session.setAttribute("message", new Message("暗証番号とカード番号一致されていません ...", "danger"));
      return "redirect:/bishnu/user/payment";
    }
    mainMethod.balanceCheckMethod(principal);
    session.setAttribute("message", new Message("Your current balance is : " + cardEntity.getBalance() , "success"));
    return "redirect:/bishnu/user/payment";
  }
  
  //balance transfer of another account
  @PostMapping("/balanceTransfer")
  public String balanceTransferBy(@Valid BalanceTransfer balanceTransfer, BindingResult result, Model model, HttpSession session, Principal principal) {
    //validation Check
    if(result.hasErrors()) {
      session.setAttribute("message", new Message("Something is wrong ...", "danger"));
      return "redirect:/bishnu/user/payment";
    }
    
    //check pin number and your account number
    String userName = principal.getName();
    BishnuEntity bishnuEntity = this.bishnuRepository.getUserByUserName(userName);
    CardEntity cardEntity = this.cardEntityRepo.getCardEntityByCardNumber(bishnuEntity.getCardNumber());
    String cardNumber = cardEntity.getCardNumber();
    String pinNumber = cardEntity.getPinNumber();
    if(!cardNumber.equals(balanceTransfer.getFromCardNumber()) || !pinNumber.equals(balanceTransfer.getPinNumber())) {
      session.setAttribute("message", new Message("暗証番号とカード番号一致されていません ...", "danger"));
      return "redirect:/bishnu/user/payment";
    }
    
  //chec transfer account
   CardEntity cardEntity2 = this.cardEntityRepo.getCardEntityByCardNumber(balanceTransfer.getToCardNumber());
    if((cardEntity2==null || balanceTransfer.getFromCardNumber().equals(balanceTransfer.getToCardNumber()))) {
      session.setAttribute("message", new Message("送信先のカード番号一致されていません ...", "danger"));
      return "redirect:/bishnu/user/payment";
    }
    
    //check amount of your balance
    if((cardEntity.getBalance()-99)<=balanceTransfer.getAmount()) {
      session.setAttribute("message", new Message("アカウントの料金を不足しております ...", "danger"));
      return "redirect:/bishnu/user/payment";
    }
    
    
    cardEntity.setBalance(cardEntity.getBalance() - balanceTransfer.getAmount());
    cardEntity2.setBalance(cardEntity2.getBalance() + balanceTransfer.getAmount());
    this.cardEntityRepo.save(cardEntity);
    this.cardEntityRepo.save(cardEntity2);
    this.mainMethod.balanceTransferFrom(principal, balanceTransfer);
    this.mainMethod.balanceTransferTo(principal, balanceTransfer);
    session.setAttribute("message", new Message("Success of deposit amount ...", "success"));
    return "redirect:/bishnu/user/payment";
  }
}
