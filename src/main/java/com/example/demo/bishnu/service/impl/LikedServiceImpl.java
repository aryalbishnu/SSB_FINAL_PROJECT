package com.example.demo.bishnu.service.impl;

import java.sql.ResultSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.bishnu.entity.Liked;
import com.example.demo.bishnu.repo.LikedRepo;
import com.example.demo.bishnu.service.LikedService;

@Service
public class LikedServiceImpl implements LikedService{
  
  @Autowired
  private LikedRepo likedRepo;

  @Override
  public Liked insertLike(int productid, int userid) {
   Liked liked = new Liked();
   liked.setUserid(userid);
   liked.setProductid(productid);
    return likedRepo.save(liked);
  }

  @Override
  public int countLikeOnPost(int productid) { 
    int resultSet = likedRepo.countLikeOnProduct(productid);
    return resultSet;
  }

  @Override
  public boolean isLikeUser(int productid, int userid) {
    boolean f = false;
    try {
      ResultSet resultSet = likedRepo.isLikeUser(productid, userid);
      if(resultSet.next()) {
        f= true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return f;
  }

  @Override
  public void deleteLike(int productid, int userid) {
    try {
     List<Liked>result = likedRepo.selectLikedByUseridAndProductid(productid, userid);
     if(!result.isEmpty()) {
       Liked liked = result.get(0);
       likedRepo.delete(liked);
     }
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }

}
