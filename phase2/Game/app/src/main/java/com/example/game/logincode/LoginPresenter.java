package com.example.game.logincode;

import android.os.Handler;

public class LoginPresenter implements LoginManager.OnLoginFinishedListener {
  LoginView loginView;
  LoginManager loginManager;

  public LoginPresenter(LoginView view, LoginManager manager) {
    loginView = view;
    loginManager = manager;
  }

  public void onDestroy() {
    loginView = null;
  }

  public void validateCredentials(String username, String password) {
    loginManager.login(username, password, this);
  }

  @Override
  public void onUsernameError() {
    loginView.setUsernameError();
  }

  @Override
  public void onPasswordError() {
    loginView.setPasswordError();
  }

  @Override
  public void onSuccess() {
    loginView.returnToMenu();
  }

  @Override
  public void onFail() {
    loginView.setLoginFail();
  }

  @Override
  public void onAccountCreated() {
    loginView.displayAccountCreated();
    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      public void run() {
        onSuccess();
      }
    }, 2500);
  }
}
