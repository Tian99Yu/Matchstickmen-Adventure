package com.example.game.logincode;

public interface LoginView {
  void setUsernameError();
  void setPasswordError();
  void setLoginFail();
  void displayAccountCreated();
  void returnToMenu();
}
