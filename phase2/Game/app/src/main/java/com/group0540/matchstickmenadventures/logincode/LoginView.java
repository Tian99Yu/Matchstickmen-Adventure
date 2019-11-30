package com.group0540.matchstickmenadventures.logincode;

interface LoginView {
  void setUsernameError();
  void setPasswordError();
  void setLoginFail();
  void displayAccountCreated();
  void returnToMenu();
}
