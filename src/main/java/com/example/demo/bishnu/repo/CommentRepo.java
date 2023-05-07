package com.example.demo.bishnu.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.bishnu.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

  @Query("SELECT count(*) from Comment u  WHERE u.productid = ?1")
  public int countCommentOnProduct(int productid);
  
  @Query("SELECT u from Comment u  WHERE u.productid = ?1")
  public List<Comment> selectCommentOnProducr(int productid);
  
  @Query("SELECT u from Comment u  WHERE u.productid = ?1 and u.userid =?2 and u.commentid =?3")
  public Comment selectCommentOnProductDelete(int productid, int userid, int commentid);
  
  
}
