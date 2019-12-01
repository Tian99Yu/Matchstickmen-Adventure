package com.group0540.matchstickmenadventures.logincode;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.group0540.matchstickmenadventures.MainMenuScreen;
import com.example.matchstickmenadventures.R;
import com.group0540.matchstickmenadventures.leaderboardcode.LeaderboardManager;
import com.group0540.matchstickmenadventures.settingscode.Customizable;
import com.group0540.matchstickmenadventures.settingscode.SettingsManager;

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

    setTheme(settingsManager.getSetting("theme"));
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
    int textColor;
    int backgroundColor;

    View loginLayout = findViewById(R.id.loginLayout);
    TextView loginInstructions = findViewById(R.id.loginInstructions);
    TextView usernameField = findViewById(R.id.usernameField);
    TextView passwordField = findViewById(R.id.passwordField);
    TextView errorLog = findViewById(R.id.errorLog);
    TextView loginButton = findViewById(R.id.loginButton);

    if (theme.equals("dark")) {
      backgroundColor = Color.parseColor("#001C27");
      textColor = Color.WHITE;
    } else {
      backgroundColor = Color.parseColor("#FF006F9C");
      textColor = Color.BLACK;
    }

    loginLayout.setBackgroundColor(backgroundColor);
    loginInstructions.setTextColor(textColor);
    usernameField.setTextColor(textColor);
    usernameField.setHintTextColor(textColor);
    usernameField.setHighlightColor(textColor);
    passwordField.setTextColor(textColor);
    passwordField.setHintTextColor(textColor);
    passwordField.setHighlightColor(textColor);
    errorLog.setTextColor(textColor);
    loginButton.setTextColor(textColor);
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
    findViewById(R.id.loginButton).setOnClickListener(null);
    intent.putExtra("leaderboardManager", leaderboardManager);
    intent.putExtra("settingsManager", settingsManager);
    intent.putExtra("loginManager", loginManager);
    startActivity(intent);
  }
}
