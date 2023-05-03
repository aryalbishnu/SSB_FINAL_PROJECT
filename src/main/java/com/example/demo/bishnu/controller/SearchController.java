package com.example.demo.bishnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bishnu.entity.BishnuEntity;
import com.example.demo.bishnu.repo.BishnuRepository;

@RestController
public class SearchController {
  
  @Autowired
  private BishnuRepository bishnuRepository;
  
  @GetMapping("/search/{query}")
  public ResponseEntity<?>search(@PathVariable("query") String query){
   List<BishnuEntity>bishnuEntities = this.bishnuRepository.findByFirstNameContaining(query);
   return ResponseEntity.ok(bishnuEntities);
    
  }

}
