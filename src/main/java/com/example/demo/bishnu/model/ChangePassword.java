package com.example.demo.bishnu.model;

import javax.validation.constraints.Size;

import lombok.Data;
@Data
public class ChangePassword {

  @Size(min = 6, max = 20, message = "パスワードを6桁以上入力してください。")
  private String newPassword;
@Size(min = 6, max = 20, message = "パスワードを6桁以上入力してください。")
  private String reNewPassword;
@Override
public String toString() {
  return "ChangePassword [newPassword=" + newPassword + ", reNewPassword=" + reNewPassword + "]";
}
}
