package com.example.demo.bishnu.service.impl;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.bishnu.dto.BishnuDto;
import com.example.demo.bishnu.entity.BishnuEntity;
import com.example.demo.bishnu.entity.CardEntity;
import com.example.demo.bishnu.model.PinNumberSet;
import com.example.demo.bishnu.repo.BishnuRepository;
import com.example.demo.bishnu.repo.CardEntityRepo;
import com.example.demo.bishnu.service.CreatService;

@Service
public class CreatServiceImpl implements CreatService {

  @Autowired
  private BishnuRepository bishnuRepository;
  
  @Autowired
  private CardEntityRepo cardEntityRepo;
  
  @Autowired
  private ModelMapper modelMapper;
  
  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Override
  public void insert(BishnuDto bishnuDto) {
    
    BishnuEntity bishnuEntity= new BishnuEntity();
    modelMapper.map(bishnuDto, bishnuEntity);
    
    LocalDateTime date = LocalDateTime.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    String now = date.format(format);
    bishnuEntity.setCreatTime(now);
    bishnuEntity.setRole("ROLE_NORMAL");
    bishnuEntity.setImage("default.jpg");
    bishnuEntity.setAccountNonLocked(true);
    
    bishnuEntity.setPassword(passwordEncoder.encode(bishnuEntity.getPassword()));
    bishnuRepository.save(bishnuEntity);
  }

  @Override
  public void insertCard(PinNumberSet pinNumberSet, Principal principal) {
    CardEntity cardEntity = new CardEntity();
    
    cardEntity.setBalance(0);
    cardEntity.setCardNumber(pinNumberSet.getCardNumber());
    cardEntity.setPinNumber(pinNumberSet.getPinNumber());
    String userName = principal.getName();
    BishnuEntity user = this.bishnuRepository.getUserByUserName(userName);
    cardEntity.setBishnuEntity(user);
    
    //db insert
    cardEntityRepo.save(cardEntity);
    
  }

  

  
}
