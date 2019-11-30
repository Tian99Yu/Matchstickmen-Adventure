package com.example.game.logincode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.game.MainMenuScreen;
import com.example.game.R;
import com.example.game.gamecode.GameActivity;
import com.example.game.leaderboardcode.LeaderboardManager;
import com.example.game.settingscode.Customizable;
import com.example.game.settingscode.SettingsManager;

public class LoginActivity extends AppCompatActivity implements Customizable, LoginView {
  private LoginPresenter presenter;
  private LoginManager loginManager;
  private LeaderboardManager leaderboardManager;
  private SettingsManager settingsManager;
  private EditText username;
  private EditText password;
  private TextView log;

  protected void onCreate(Bundle onSavedInstanceState) {
    super.onCreate(onSavedInstanceState);
    setContentView(R.layout.login_layout);

    loginManager = (LoginManager) getIntent().getSerializableExtra("loginManager");
    leaderboardManager = (LeaderboardManager) getIntent().getSerializableExtra("leaderboardManager");
    settingsManager = (SettingsManager) getIntent().getSerializableExtra("settingsManager");

    presenter = new LoginPresenter(this, loginManager);

    setFields();
    addButtonListener();
  }

  private void setFields() {
    username = findViewById(R.id.usernameField);
    password = findViewById(R.id.passwordField);
    log = findViewById(R.id.errorLog);
  }

  private void addButtonListener() {
    Button loginButton = findViewById(R.id.loginButton);
    loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        presenter.validateCredentials(username.getText().toString(), password.getText().toString());
      }
    });
  }

  @Override
  public void onBackPressed() {
    Intent intent = new Intent(this, MainMenuScreen.class);
    startActivity(intent);
  }

  @Override
  public void setTheme(String theme) {

  }

  @Override
  public void setUsernameError() {
    username.setError(getString(R.string.username_error));
  }

  @Override
  public void setLoginFail() {
    log.setText(getString(R.string.password_incorrect));
  }

  @Override
  public void displayAccountCreated() {
    log.setText(getString(R.string.account_created));
  }

  @Override
  public void setPasswordError() {
    password.setError(getString(R.string.password_error));
  }

  @Override
  public void returnToMenu() {
    Intent intent = new Intent(this, MainMenuScreen.class);
    intent.putExtra("leaderboardManager", leaderboardManager);
    intent.putExtra("settingsManager", settingsManager);
    intent.putExtra("loginManager", loginManager);
    presenter.onDestroy();
    startActivity(intent);
  }
}
