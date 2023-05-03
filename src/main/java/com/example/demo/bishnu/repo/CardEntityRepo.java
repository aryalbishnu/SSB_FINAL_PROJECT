package com.example.demo.bishnu.repo;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.bishnu.entity.CardEntity;

public interface CardEntityRepo extends JpaRepository<CardEntity, Integer>{

  @Query("select u from CardEntity u where u.id =:id")
  public CardEntity getCardEntityByBishnuEntity(@Param("id") int id);
  
  @Query("select u from CardEntity u where u.cardNumber =:cardNumber")
  public CardEntity getCardEntityByCardNumber(@Param("cardNumber") String cardNumber);


}
