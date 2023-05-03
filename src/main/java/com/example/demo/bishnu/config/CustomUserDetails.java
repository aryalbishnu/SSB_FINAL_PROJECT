package com.example.demo.bishnu.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.bishnu.entity.BishnuEntity;

public class CustomUserDetails implements UserDetails{
  
  private BishnuEntity bishnuEntity;
  
  public CustomUserDetails(BishnuEntity bishnuEntity) {
    super();
    this.bishnuEntity= bishnuEntity;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
  SimpleGrantedAuthority simpleGrantedAuthority=  new SimpleGrantedAuthority(bishnuEntity.getRole());
    return List.of(simpleGrantedAuthority);
  }

  @Override
  public String getPassword() {
  
    return bishnuEntity.getPassword();
  }

  @Override
  public String getUsername() {

    return bishnuEntity.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {

    return true;
  }

  @Override
  public boolean isAccountNonLocked() {

    return bishnuEntity.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
   
    return true;
  }

  @Override
  public boolean isEnabled() {
 
    return true;
  }
  
  public BishnuEntity getUser() {
    return this.bishnuEntity;
  }

}
