package com.example.demo.bishnu.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.bishnu.entity.BishnuEntity;
import com.example.demo.bishnu.entity.CardEntity;
import com.example.demo.bishnu.entity.OrderListEntity;
import com.example.demo.bishnu.entity.ProductEntity;
import com.example.demo.bishnu.entity.SaleEntity;
import com.example.demo.bishnu.helper.Message;
import com.example.demo.bishnu.model.PaymentModel;
import com.example.demo.bishnu.repo.BishnuRepository;
import com.example.demo.bishnu.repo.CardEntityRepo;
import com.example.demo.bishnu.repo.OrderListRepo;
import com.example.demo.bishnu.repo.ProductRepo;
import com.example.demo.bishnu.repo.SaleEntityRepo;
import com.example.demo.bishnu.service.impl.MainMethod;




@Controller
@RequestMapping("bishnu/user")
public class ShoppingController {
  
  @Autowired
  private BishnuRepository bishnuRepository;
  
  @Autowired
  private ProductRepo productRepo;
  
  @Autowired
  private OrderListRepo orderListRepo;
  
  @Autowired
  private MainMethod mainMethod;
  
  @Autowired
  private CardEntityRepo cardEntityRepo;
  
  @Autowired
  private SaleEntityRepo saleEntityRepo;
  
  //common data
  @ModelAttribute
  public void addCommonData(Model model, Principal principal) {
    String userName = principal.getName();

    //get the user using userName(Email)
   BishnuEntity user=this.bishnuRepository.getUserByUserName(userName);
    model.addAttribute("user", user);
  }
  
  @GetMapping("/shopping")
  public String shoppingBy(Model model) {
    model.addAttribute("title", "Shopping page");
    List<ProductEntity>productList = this.productRepo.findAll();
    
    model.addAttribute("productList", productList);
    return "bishnu/normal/shopping";
  }
  
  //add card button click add your order list
  //quantity is less from total product
  @PostMapping("/addCard/{id}")
  public String addCardBy(@PathVariable("id") Integer id, Model model, OrderListEntity orderListEntity) {
   ProductEntity productEntity= this.productRepo.findById(id).get();
   orderListEntity.setProductImage(productEntity.getProductImage());
   orderListEntity.setProductName(productEntity.getProductName());
   orderListEntity.setProductPrice(productEntity.getProductPrice());
   orderListEntity.setProductQuantity(1);
   orderListRepo.save(orderListEntity);
   productEntity.setProductQuantity(productEntity.getProductQuantity()-1);
   productRepo.save(productEntity);
   return "redirect:/bishnu/user/shopping";
  }
  

  //order list 
  @GetMapping("/orderList")
  public String orderListBy(Model model) {
    model.addAttribute("title", "Order list page");
    List<OrderListEntity>orderList = this.orderListRepo.findAll();
    model.addAttribute("orderList", orderList);
    /*
    int sum=0;
for (OrderListEntity orderListEntity : orderList) {
  sum += orderListEntity.getProductPrice();
}
    System.out.println(sum);
    */
    return "bishnu/normal/orderList";
  }
  
  //delete order list
  //particular prddoductEntity add of quantity  
  @PostMapping("/deleteOrderList/{id}")
  public String deleteOrderListBy(@PathVariable("id") Integer id, Model model, HttpSession session) throws IOException {
   OrderListEntity orderListEntity = this.orderListRepo.findById(id).get();
   ProductEntity productEntity = this.productRepo.getProductEntityByProductName(orderListEntity.getProductName());
   productEntity.setProductQuantity(productEntity.getProductQuantity()+ 1);
   this.productRepo.save(productEntity);
   this.orderListRepo.deleteById(id);
    session.setAttribute("message", new Message("SuccessFully delete your product..... ", "success"));
    return "redirect:/bishnu/user/orderList";
  }
  
  //pay of shopping list
  //delete all data from orderListEntity
  
  @PostMapping("/paymentModel")
   public String paymentModelBy(@Valid PaymentModel paymentModel, BindingResult result, Model model, HttpSession session, Principal principal) {
    //check Validation
    if(result.hasErrors()) {
      session.setAttribute("message", new Message("Payment something is wrong..... ", "danger"));
      return "redirect:/bishnu/user/orderList";
    }
    //check pin number and account number
    String userName = principal.getName();
    BishnuEntity bishnuEntity= this.bishnuRepository.getUserByUserName(userName);
    CardEntity cardEntity = this.cardEntityRepo.getCardEntityByCardNumber(bishnuEntity.getCardNumber());
    String cardNumber = cardEntity.getCardNumber();
    String pinNumber= cardEntity.getPinNumber();
    int amount = cardEntity.getBalance();
    
    if(!(paymentModel.getCardNumber().equals(cardNumber)) || !(paymentModel.getPinNumber().equals(pinNumber))) {
      session.setAttribute("message", new Message("暗証番号とカード番号一致されていません ...", "danger"));
      return "redirect:/bishnu/user/orderList";
    }
    
    //check amount
    if(amount-99<paymentModel.getAmount()) {
      session.setAttribute("message", new Message("お金が不足しています ...", "danger"));
      return "redirect:/bishnu/user/orderList";
    }
    
    LocalDateTime date = LocalDateTime.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    String now = date.format(format);
    cardEntity.setBalance(amount - paymentModel.getAmount());
    
    this.cardEntityRepo.save(cardEntity);
    List<OrderListEntity>orderListEntities = this.orderListRepo.findAll();
    for (OrderListEntity orderListEntity : orderListEntities) {
        SaleEntity saleEntity = new SaleEntity();
        saleEntity.setSaleName(orderListEntity.getProductName());
        saleEntity.setSaleQuantity(orderListEntity.getProductQuantity());
        saleEntity.setSalePrice(orderListEntity.getProductPrice());
        saleEntity.setSaleDate(now);
        saleEntityRepo.save(saleEntity);
      }
    
    this.orderListRepo.deleteAll(orderListEntities);
    this.mainMethod.paymentOrder(principal, paymentModel);
    session.setAttribute("message", new Message("SuccessFully payment your order..... ", "success"));
   return "redirect:/bishnu/user/shopping";
  }
  
}
