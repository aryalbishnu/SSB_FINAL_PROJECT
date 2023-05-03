package com.example.demo.bishnu.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.example.demo.bishnu.entity.BishnuEntity;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  @Autowired
   private UserServices userServices;
  
  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {
    
    String email = request.getParameter("username");
    BishnuEntity user = userServices.getByEmail(email);
    
    if(user !=null) {
       if(user.isAccountNonLocked()) {
         if(user.getFailedAttempt()<UserServices.MAX_FAILED_ATTEMPTS - 1) {
           userServices.increaseFailedAttempts(user);
           
         } else {
           userServices.lock(user);
          exception = new LockedException("your account locked for 5 minute");
         }
       }
       else if(!user.isAccountNonLocked()){
         if(userServices.unlockWhenTimeExpired(user)) {
           exception = new LockedException("your account has been unlocked");
         }
         
       }
    }
    
    super.setDefaultFailureUrl("/bishnu/loginForm?error");
    
     super.onAuthenticationFailure(request, response, exception);
  }
  
  

}
