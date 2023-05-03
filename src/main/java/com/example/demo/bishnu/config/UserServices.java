package com.example.demo.bishnu.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.bishnu.entity.BishnuEntity;
import com.example.demo.bishnu.repo.BishnuRepository;

@Service
@Transactional
public class UserServices {

  public static final int MAX_FAILED_ATTEMPTS = 3;
  
  private static final long LOCK_TIME_DURATION = 5 * 60 * 1000; // 24 hours
   
  @Autowired
  private BishnuRepository repo;
   
  public void increaseFailedAttempts(BishnuEntity user) {
      int newFailAttempts = user.getFailedAttempt() + 1;
      repo.updateFailedAttempts(newFailAttempts, user.getEmail());
  }
   
  public void resetFailedAttempts(String email) {
      repo.updateFailedAttempts(0, email);
  }
   
  public void lock(BishnuEntity user) {
      user.setAccountNonLocked(false);
      user.setLockTime(new Date());
       
      repo.save(user);
  }
   
  public boolean unlockWhenTimeExpired(BishnuEntity user) {
      long lockTimeInMillis = user.getLockTime().getTime();
      long currentTimeInMillis = System.currentTimeMillis();
       
      if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
          user.setAccountNonLocked(true);
          user.setLockTime(null);
          user.setFailedAttempt(0);
           
          repo.save(user);
           
          return true;
      }
       
      return false;
  }
  public BishnuEntity getByEmail(String email) {
    return repo.getUserByUserName(email);
  }
  
}
