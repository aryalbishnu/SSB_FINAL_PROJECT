package com.example.demo.bishnu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class MyConfig extends WebSecurityConfigurerAdapter{
  
  @Bean
  public UserDetailsService getUserDetailsService() {    
    return new UserDetailsServiceImpl();
  }
  
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  
  
  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    return daoAuthenticationProvider;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
   auth.authenticationProvider(authenticationProvider());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
  http.authorizeRequests().antMatchers("/bishnu/user/admin/**").hasRole("ADMIN")
  .antMatchers("/bishnu/user/**").access("hasRole('ROLE_NORMAL') or hasRole('ROLE_ADMIN')")
//.antMatchers("/bishnu/user/**").hasAnyRole("ADMIN", "NORMAL")
  //.antMatchers("/bishnu/user/**").hasRole("NORMAL")
  //.antMatchers("/bishnu/user/**").hasRole("ADMIN")
 .antMatchers("/**").permitAll().and().formLogin()

  .loginPage("/bishnu/loginForm")
  .loginProcessingUrl("/dologin") 
  //.defaultSuccessUrl("/bishnu/user/dologin")
  .failureHandler(loginFailureHandler)
  .successHandler(loginSuccessHandler)  
  .usernameParameter("username")
  .and().csrf().disable();
 
               
 
  }
  
@Autowired
private CustomLoginFailureHandler loginFailureHandler;

@Autowired
private CustomLoginSuccessHandler loginSuccessHandler;

  

  

}
