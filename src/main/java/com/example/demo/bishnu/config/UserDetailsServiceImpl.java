package com.example.demo.bishnu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo.bishnu.entity.BishnuEntity;
import com.example.demo.bishnu.mapper.BishnuMapper;
import com.example.demo.bishnu.repo.BishnuRepository;

public class UserDetailsServiceImpl implements UserDetailsService{
  
  @Autowired
  private BishnuMapper bishnuMapper;
  
  @Autowired
  private BishnuRepository bishnuRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
   BishnuEntity bishnuEntity =bishnuRepository.getUserByUserName(username);
   if(bishnuEntity==null) {
     throw new UsernameNotFoundException("user not found ");
   }
     CustomUserDetails customUserDetails = new CustomUserDetails(bishnuEntity);
    
    return customUserDetails;
  }

}
