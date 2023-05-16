package com.example.demo.bishnu.service;

import java.util.List;

import com.example.demo.bishnu.entity.Comment;

public interface CommentService {
  
  public Comment insertComment(int productid, int userid, String Comment);
  
  public int countComment(int productid);
  
  public List<Comment> selectCommentByProductId(int productid);
  
  public void deleteComment(int productid, int userid, int commentid);
  
  public Comment updateCommentByCommentId(int commentid, String comment);
  

}
