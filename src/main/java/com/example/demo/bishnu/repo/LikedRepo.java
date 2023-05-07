package com.example.demo.bishnu.repo;

import java.sql.ResultSet;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.bishnu.entity.Liked;

public interface LikedRepo extends JpaRepository<Liked, Integer>{
 
  @Query("SELECT u from Liked u  WHERE u.productid = ?1 and u.userid =?2")
  public ResultSet isLikeUser(int productid, int userid);
  
  @Query("SELECT u from Liked u  WHERE u.productid = ?1 and u.userid =?2")
  public List<Liked> selectLikedByUseridAndProductid(int productid, int userid);
  
  @Query("DELETE from Liked u  WHERE u.productid = ?1 and u.userid =?2")
  public void deleteLike(int productid, int userid);

  @Query("SELECT count(*) from Liked u  WHERE u.productid = ?1")
  public int countLikeOnProduct(int productid);

}
