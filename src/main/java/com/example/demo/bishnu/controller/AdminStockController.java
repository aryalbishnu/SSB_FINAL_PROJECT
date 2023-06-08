package com.example.demo.bishnu.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.bishnu.entity.BishnuEntity;
import com.example.demo.bishnu.entity.ProductEntity;
import com.example.demo.bishnu.helper.Message;
import com.example.demo.bishnu.model.AddProduct;
import com.example.demo.bishnu.model.UpdateProduct;
import com.example.demo.bishnu.repo.BishnuRepository;
import com.example.demo.bishnu.repo.ProductRepo;
import com.example.demo.bishnu.service.ProductService;

@Controller
@RequestMapping("/bishnu/user/")

//only admin use class
public class AdminStockController {
  
  @Autowired
  private ModelMapper modelMapper;
  
  @Autowired
  private BishnuRepository bishnuRepository;
  
  @Autowired
  private ProductRepo productRepo;
  
  @Autowired
  private ProductService productService;
  
//common data
  @ModelAttribute
  public void addCommonData(Model model, Principal principal) {
    String userName = principal.getName();

    //get the user using userName(Email)
   BishnuEntity user=this.bishnuRepository.getUserByUserName(userName);
    model.addAttribute("user", user);
  }

  //click stock product management form view page
  @PostMapping("/stockProductManagement")
  public String stockProductManagement(Model model) {
    model.addAttribute("title", "stockProductManagement");
    
    return "bishnu/admin/stock/stockChoose";
  }
  
  //click sale Information  form view-- StockProductManagement page
  @PostMapping("/saleInformation")
  public String saleInformation(Model model) {
    model.addAttribute("title", "saleInformation");
    List<ProductEntity> productEntity= this.productRepo.findAll();
    Map<String, Integer> data = new LinkedHashMap<String, Integer>();
  for (ProductEntity productEntity2 : productEntity) {
    data.put(productEntity2.getProductName(), productEntity2.getProductQuantity());
  }
    model.addAttribute("keySet", data.keySet());
    model.addAttribute("values", data.values());
    return "bishnu/admin/stock/saleInformation";
  }
 
  //click materialInformation form view-- StockProductManagement page
  @GetMapping("/materialInformation")
  public String materialInformation(Model model) {
    
    model.addAttribute("title", "materialInformation");
    List<ProductEntity> productEntity= this.productRepo.findAll();
    model.addAttribute("productEntity", productEntity);
    return "bishnu/admin/stock/materialInformation";
  }
  
  //add product from admin
  @PostMapping("/addProduct")
  public String addProductBy(@Valid AddProduct addProduct, BindingResult result, Principal principal, 
     @RequestParam("image") MultipartFile file, Model model, HttpSession session) throws IOException {
    String userName = principal.getName();
    //get the user using userName(Email)
    int  productUserId =this.bishnuRepository.getUserByUserName(userName).getId();
    if(result.hasErrors()){
      model.addAttribute("title", "materialInformation");
      session.setAttribute("message", new Message("Something is wrong..... ", "danger"));
      return "redirect:/bishnu/user/materialInformation";
    }
    if(file.isEmpty()) {
      session.setAttribute("message", new Message("画像を選択してください。..... ", "danger"));
      return "redirect:/bishnu/user/materialInformation"; 
    }
  
    this.productService.addProductMethod(addProduct, file, productUserId);
    model.addAttribute("title", "materialInformation");
    session.setAttribute("message", new Message("SuccessFully add your product..... ", "success"));
    return "redirect:/bishnu/user/materialInformation";
  }
  
  //delete product from admin
  @GetMapping("/deleteProduct/{id}")
  public String deleteBy(@PathVariable("id") Integer id, Model model, HttpSession session) throws IOException {
   this.productService.deleteByProduct(id);
    model.addAttribute("title", "materialInformation");
    session.setAttribute("message", new Message("SuccessFully delete your product..... ", "success"));
    return "redirect:/bishnu/user/materialInformation";
  }
  
  //update product from admin 
  @PostMapping("/updateProduct/{id}")
  public String updateProduct(@PathVariable("id") Integer id, Model model,UpdateProduct updateProduct, ProductEntity productEntity, HttpSession session) {
    
    this.productService.updateProductByProductId(id, updateProduct, productEntity);
    model.addAttribute("title", "materialInformation");
    session.setAttribute("message", new Message("SuccessFully Update your product..... ", "success"));
    return "redirect:/bishnu/user/materialInformation";
  }
}
