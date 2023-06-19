package com.example.demo.bishnu.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.bishnu.Convertor;
import com.example.demo.bishnu.EntityNotFoundException;
import com.example.demo.bishnu.dto.ProductCommentDto;
import com.example.demo.bishnu.dto.ProductDto;
import com.example.demo.bishnu.entity.BishnuEntity;
import com.example.demo.bishnu.entity.CardEntity;
import com.example.demo.bishnu.entity.Comment;
import com.example.demo.bishnu.entity.OrderListEntity;
import com.example.demo.bishnu.entity.ProductEntity;
import com.example.demo.bishnu.entity.SaleEntity;
import com.example.demo.bishnu.helper.Message;
import com.example.demo.bishnu.mapper.ProductCommentMapper;
import com.example.demo.bishnu.mapper.ProductDtoMapper;
import com.example.demo.bishnu.model.PaymentModel;
import com.example.demo.bishnu.repo.BishnuRepository;
import com.example.demo.bishnu.repo.CardEntityRepo;
import com.example.demo.bishnu.repo.LikedRepo;
import com.example.demo.bishnu.repo.OrderListRepo;
import com.example.demo.bishnu.repo.ProductRepo;
import com.example.demo.bishnu.repo.SaleEntityRepo;
import com.example.demo.bishnu.service.CommentService;
import com.example.demo.bishnu.service.LikedService;
import com.example.demo.bishnu.service.ProductService;
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
  
  @Autowired
  private LikedService likedService;
  
  @Autowired
  private LikedRepo likedRepo;
  
  @Autowired
  private CommentService commentService;
  
  @Autowired
  private ProductCommentMapper productCommentMapper;
  
  @Autowired
  private ProductDtoMapper productDtoMapper;
  
  @Autowired
  private ProductService productService;
  
  private static final Logger logger = LoggerFactory.getLogger(ShoppingController.class);
  
  //common data
  @ModelAttribute
  public void addCommonData(Model model, Principal principal) {
    String userName = principal.getName();

    //get the user using userName(Email)
   BishnuEntity user=this.bishnuRepository.getUserByUserName(userName);
    model.addAttribute("user", user);
  }
  
  // open shopping page 
  @GetMapping("/shopping")
  public String shoppingBy(Model model, Principal principal) {
    model.addAttribute("title", "Shopping page");
    // get userId 
    String userName = principal.getName();
   int userId=this.bishnuRepository.getUserByUserName(userName).getId();
    List<ProductDto>pro = productDtoMapper.productDtoList();
    pro.forEach(productDetail -> {
      try {
        // check boolean userid and product id is liked of product
        Boolean f =  productDtoMapper.booleanCheck(productDetail.getId(), userId);
        if(f) {
          int likeId = productDtoMapper.productDtoLiked(productDetail.getId(), userId);
          if (likeId != 0) {
            productDetail.setLikeid(likeId);
        }
        }
        
      } catch (Exception e) {
        e.printStackTrace();
      }   
  });
   //List of product
    model.addAttribute("productList", pro);
    //List of product
    List<ProductEntity>productList = this.productRepo.findAll();
    //model.addAttribute("productList", productList);
    //List of count Like in particular userid and productid
    List<Integer> likeds= productList.stream().map(product ->{
      return this.likedService.countLikeOnPost(product.getId());
     }).collect(Collectors.toList()); 
    model.addAttribute("likeds", likeds);
  
    //List of count comment in particuar userid and productid
    List<Integer> comments = productList.stream().map(product ->{
      return this.commentService.countComment(product.getId());
    }).collect(Collectors.toList());
    model.addAttribute("comments", comments);
    //List of comment in particular product
    List<List<Comment>> commentDetail = productList.stream().map(product ->{
      return this.commentService.selectCommentByProductId(product.getId());
    }).collect(Collectors.toList());
    model.addAttribute("commentDetail", commentDetail);
  //List of comment in particular product by productid
    List<List<ProductCommentDto>>productIdByComment = productList.stream().map(productComment ->{
       return this.productCommentMapper.productCommentDtos(productComment.getId());   
           }).collect(Collectors.toList());
    model.addAttribute("productIdByComment", productIdByComment);
    
    
    return "bishnu/normal/shopping";
  }


  //like button click add your like in db
  @PostMapping("/like/{productid}/{userid}")
  public String productLike(@PathVariable("productid") Integer productid, @PathVariable("userid") Integer userid, Model model) {
    likedService.insertLike(productid, userid);
    return "redirect:/bishnu/user/shopping";
  }
  
  //dislike button click remove your like in db
  @PostMapping("/dislike/{productid}/{userid}")
  public String productDisLike(@PathVariable("productid") Integer productid, @PathVariable("userid") Integer userid, Model model) {
    likedService.deleteLike(productid, userid);
    return "redirect:/bishnu/user/shopping";
  }
  
  //comment submit to add a comment in db
  @PostMapping("/comment/insert/{productid}/{userid}")
  public ResponseEntity<Comment> commentInsert(@PathVariable("productid") Integer productid, @PathVariable("userid") Integer userid,
      @RequestParam("comment") String comment, Model model) {
   Comment comment2 = commentService.insertComment(productid, userid, comment);
    return new ResponseEntity<Comment>(comment2, HttpStatus.OK);
  }
  
  //comment edit click edit button in parcticula user
  @PostMapping("/comment/update/{productid}/{commentid}")
  public ResponseEntity<Comment> Update(@PathVariable("commentid") Integer commentid, @PathVariable("productid") Integer productid,
           @RequestParam("comment") String comment, Model model) {
   Comment comment2 = commentService.updateCommentByCommentId(commentid, comment);
    return new ResponseEntity<Comment>(comment2, HttpStatus.OK);
  }
  
// comment delete  click delete button  
  @PostMapping("/delete/{commentid}/{userid}")
  public String commentDelete(@PathVariable("commentid") Integer commentid, @PathVariable("userid") Integer userid, Model model) {
    productCommentMapper.deleteCommentProduct(commentid, userid);
    return "redirect:/bishnu/user/shopping";
  }
  
  //add card button click add your order list
  //quantity is less from total product
  @PostMapping("/addCard/{id}")
  public String addCardBy(@PathVariable("id") Integer id, Model model, OrderListEntity orderListEntity) {
   ProductEntity productEntity= this.productRepo.findById(id).get();
   orderListEntity.setProductImage(productEntity.getProductImage());
   orderListEntity.setProductName(productEntity.getProductName());
   orderListEntity.setProductPrice(productEntity.getProductPrice());
   orderListEntity.setProductId(productEntity.getId());   
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
    // get userid 
    int userId = bishnuEntity.getId();
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
        saleEntity.setProductId(orderListEntity.getProductId());
        saleEntity.setUserId(userId);
        saleEntity.setSaleDate(now);
        saleEntityRepo.save(saleEntity);
      }
    
    this.orderListRepo.deleteAll(orderListEntities);
    this.mainMethod.paymentOrder(principal, paymentModel);
    session.setAttribute("message", new Message("SuccessFully payment your order..... ", "success"));
   return "redirect:/bishnu/user/shopping";
  }
  
  // product detail of praticular product by product id
  @GetMapping("/productDetail/{productid}")
  public String particularProductDetail(@PathVariable("productid") Integer productid, Model model) {
    model.addAttribute("title", "productDetail page");
    // get product detail by productid
    ProductEntity product = this.productService.findByProductId(productid);
    model.addAttribute("product", product);
    
    // product add admin detail get by productuserId
    BishnuEntity user = this.bishnuRepository.findById(product.getProductUserId()).orElseThrow(() -> new EntityNotFoundException("this product added admin is not availabel"));  
    model.addAttribute("user", user);
    // string convert to date format
    model.addAttribute("stringConvertToDate", Convertor.class);
    
    return "bishnu/normal/productDetail";
  }

  
}
