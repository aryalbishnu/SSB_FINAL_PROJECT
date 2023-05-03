package com.example.demo.bishnu.service;

import java.security.Principal;

import com.example.demo.bishnu.dto.BishnuDto;
import com.example.demo.bishnu.model.PinNumberSet;


public interface CreatService {
  

  // insert by db creat user
  public void insert(BishnuDto bishnuDto);
  
  //insert by db card entity
public void insertCard(PinNumberSet pinNumberSet, Principal principal);
 
  

}
