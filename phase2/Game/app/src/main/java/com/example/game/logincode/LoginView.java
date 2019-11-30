package com.example.game.logincode;

interface LoginView {
  void setUsernameError();
  void setPasswordError();
  void setLoginFail();
  void displayAccountCreated();
  void returnToMenu();
}
