package com.example.demo.bishnu.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.demo.bishnu.entity.BishnuEntity;
@Component

public class CustomLoginSuccessHandler extends  SimpleUrlAuthenticationSuccessHandler{
  
  @Autowired
  private UserServices userServices;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    CustomUserDetails userDetails =  (CustomUserDetails) authentication.getPrincipal();
    BishnuEntity user = userDetails.getUser();
    if (user.getFailedAttempt() > 0) {
        userServices.getByEmail(user.getEmail());
    } 
    super.setDefaultTargetUrl("/bishnu/user/dologin");
    super.onAuthenticationSuccess(request, response, authentication);
}
 
  
}
