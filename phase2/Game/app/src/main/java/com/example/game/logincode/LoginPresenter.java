package com.example.game.logincode;

import android.os.Handler;

public class LoginPresenter implements LoginManager.OnLoginFinishedListener {
  private LoginView loginView;
  private final LoginManager loginManager;

  LoginPresenter(LoginView view, LoginManager manager) {
    loginView = view;
    loginManager = manager;
  }

  void onDestroy() {
    loginView = null;
  }

  void validateCredentials(String username, String password) {
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
