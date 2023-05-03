package com.example.demo.bishnu.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.bishnu.entity.BishnuEntity;

public interface BishnuRepository extends JpaRepository<BishnuEntity, Integer>{
  

  @Query("select u from BishnuEntity u where u.email =:email")
  public BishnuEntity getUserByUserName(@Param("email") String email);
  
  @Query("select u from BishnuEntity u where u.cardNumber =:cardNumber")
  public BishnuEntity getUserByCardNumber(@Param("cardNumber") String cardNumber);
  
  @Query("UPDATE BishnuEntity u SET u.failedAttempt = ?1 WHERE u.email = ?2")
  @Modifying
  public void updateFailedAttempts(int failAttempts, String email);
  
  //Search 
  public List<BishnuEntity> findByFirstNameContaining(String keywords);


}
