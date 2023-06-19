package com.example.demo.bishnu.controller;

import java.time.LocalTime;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.bishnu.entity.BishnuEntity;
import com.example.demo.bishnu.helper.Message;
import com.example.demo.bishnu.model.ChangePassword;
import com.example.demo.bishnu.repo.BishnuRepository;
import com.example.demo.bishnu.service.impl.MainMethod;


@Controller
@RequestMapping("/bishnu/")
public class ForgotPasswordController {
  
  @Autowired
  private BishnuRepository bishnuRepository;
  
  @Autowired
  private MainMethod mainMethod;
  
  @Autowired
  private BCryptPasswordEncoder passwordEncoder;
  
  @GetMapping("/otpSend")
  public String otpSendBy(Model model) {
    model.addAttribute("title", "otp email-send");
    return "userLogin/forgotPassword";
  }
  
  @PostMapping("/send-otp") 
  public String senOtpbyEmail(Model model, @RequestParam("email") String email, HttpSession session) {
    model.addAttribute("title", "verify otp-number");
    List<BishnuEntity>bishnuEntities = this.bishnuRepository.findAll();
    for(BishnuEntity bishnuEntity: bishnuEntities) {
      if(bishnuEntity.getEmail().equals(email)) {
        session.setAttribute("message", new Message("please check your email OTP", "success"));
        Random random= new Random();
        String otp=Integer.toString(random.nextInt(1000, 9999));
       boolean f = this.mainMethod.sendOneTimePassword(email, otp);
       if(f) {
         LocalTime currentTime = LocalTime.now();
         session.setAttribute("otpSendTime", currentTime);
         session.setAttribute("sendEmail", email);
         session.setAttribute("sendOtp", otp);
       }
        return "userLogin/otpPassword";
      }
      }

    session.setAttribute("message", new Message("your email is wrong.......", "danger"));
    return "userLogin/forgotPassword";
  }
  
  @PostMapping("/verify-otp")
  public String verfiyotpByOtpNumber(Model model, @RequestParam("otpNumber") String otpNumber, ChangePassword changePassword,  HttpSession session){
    String sendOtp= (String) session.getAttribute("sendOtp");
    
    String sendEmail= (String)session.getAttribute("sendEmail");
    BishnuEntity bishnuEntity = this.bishnuRepository.getUserByUserName(sendEmail);
    if(bishnuEntity!=null) {
      //check otp number
      if(otpNumber.equals(sendOtp)) {
        // get current time 
        LocalTime currentTime = LocalTime.now();
        // get otp send time and add 1 minute
        LocalTime otpSendTime = (LocalTime) session.getAttribute("otpSendTime");
        LocalTime otpSendTime2 = otpSendTime.plusMinutes(1);
        System.out.println(currentTime);
        System.out.println(otpSendTime2);
        int timeCompare = currentTime.compareTo(otpSendTime2);
        // check session time 
        if(timeCompare<0) {
          model.addAttribute("title", "password-change");
          return "userLogin/createNewPassword";
        } else {
          session.setAttribute("message", new Message("your session is time out.......", "danger"));
          return "redirect:/bishnu/loginForm";
        }
      } else {
        session.setAttribute("message", new Message("your one time password is worng.......", "danger"));
        return "redirect:/bishnu/loginForm";
      }
    }
    return "redirect:/bishnu/loginForm";
  }
  /*
  @PostMapping("/changePassword")
  public String changePasswordByUser(Model model, HttpSession session, @RequestParam("password") String password, @RequestParam("re_password") String re_password){
   if(password.length()>=6 && re_password.length()>=6) {
     if(password.equals(re_password)) {
       // get email from session
       String email= (String)session.getAttribute("sendEmail");
       BishnuEntity bishnuEntity = this.bishnuRepository.getUserByUserName(email);
       bishnuEntity.setPassword(this.passwordEncoder.encode(password));
       this.bishnuRepository.save(bishnuEntity);
       session.setAttribute("message", new Message("Succesfull register", "success"));
       return "redirect:/bishnu/loginForm";
     } else {
       session.setAttribute("message", new Message("please enter a same password", "danger"));
       return "userLogin/createNewPassword";
     }
   } else {
     session.setAttribute("message", new Message("password must be 6 digit", "danger"));
     return "userLogin/createNewPassword";
   }
    
  }
  */
  @PostMapping("/changePassword")
  public String changePasswordto(@Valid ChangePassword changePassword, BindingResult result, Model model, HttpSession session) {
     // validation check
    if(result.hasErrors()){
     
      //  model.addAttribute("message",);
      session.setAttribute("message", new Message("please enter a 6 digit above password", "danger"));
      return "userLogin/createNewPassword";
        }
    if(changePassword.getNewPassword().equals(changePassword.getReNewPassword())) {
      String email= (String)session.getAttribute("sendEmail");
      BishnuEntity bishnuEntity = this.bishnuRepository.getUserByUserName(email);
      bishnuEntity.setPassword(this.passwordEncoder.encode(changePassword.getNewPassword()));
      this.bishnuRepository.save(bishnuEntity);
      session.setAttribute("message", new Message("Succesfull create a new password", "success"));
      return "redirect:/bishnu/loginForm";
    }
    session.setAttribute("message", new Message("Please enter a same password", "danger"));
    return "userLogin/createNewPassword";
  }


}
