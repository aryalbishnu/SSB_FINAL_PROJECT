package com.example.demo.bishnu.service;

import com.example.demo.bishnu.entity.Liked;


public interface LikedService {
  
  public Liked insertLike(int productid, int userid);
  
  public int countLikeOnPost(int productid);
  
  public boolean isLikeUser(int productid, int userid);
  
  public void deleteLike(int productid, int userid);

  

}
