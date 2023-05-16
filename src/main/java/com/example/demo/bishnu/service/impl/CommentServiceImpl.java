package com.example.demo.bishnu.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.bishnu.entity.Comment;
import com.example.demo.bishnu.repo.CommentRepo;
import com.example.demo.bishnu.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
  @Autowired
  private CommentRepo commentRepo;

  public Comment insertComment(int productid, int userid, String comment) {
     Comment comm = new Comment();
     comm.setProductid(productid);
     comm.setUserid(userid);
     comm.setComment(comment);
     LocalDateTime dateTime = LocalDateTime.now();
     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
     String dateTimeString = dateTime.format(formatter);
     comm.setTime(dateTimeString);
     comm.setUpdatetime(null);
    return commentRepo.save(comm);
  }

  @Override
  public int countComment(int productid) {
   int result = commentRepo.countCommentOnProduct(productid);
    return result;
  }

  @Override
  public List<Comment> selectCommentByProductId(int productid) {
   List<Comment> comments = commentRepo.selectCommentOnProducr(productid);
    return comments;
  }

  @Override
  public void deleteComment(int productid, int userid, int commentid) {
    Comment comment = commentRepo.selectCommentOnProductDelete(productid, userid, commentid);
    commentRepo.delete(comment); 
  }

  @Override
  public Comment updateCommentByCommentId(int commentid, String comment) {
   Comment comm = commentRepo.updateCommentByCommentId(commentid);
   comm.setComment(comment);
   LocalDateTime dateTime = LocalDateTime.now();
   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
   String dateTimeString = dateTime.format(formatter);
   comm.setUpdatetime(dateTimeString);
    return commentRepo.save(comm);
  }

}
