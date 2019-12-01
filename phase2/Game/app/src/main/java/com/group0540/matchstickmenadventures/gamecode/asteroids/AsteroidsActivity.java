package com.group0540.matchstickmenadventures.gamecode.asteroids;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.group0540.matchstickmenadventures.Games;
import com.example.matchstickmenadventures.R;
import com.group0540.matchstickmenadventures.gamecode.customcontrols.JoystickView;
import com.group0540.matchstickmenadventures.gamecode.GameActivity;
import com.group0540.matchstickmenadventures.gamecode.GameView;
import com.group0540.matchstickmenadventures.leaderboardcode.LeaderboardManager;
import com.group0540.matchstickmenadventures.leaderboardcode.Saver;
import com.group0540.matchstickmenadventures.logincode.LoginManager;
import com.group0540.matchstickmenadventures.settingscode.CustomizableGame;
import com.group0540.matchstickmenadventures.settingscode.SettingsManager;

public class AsteroidsActivity extends GameActivity implements Saver, CustomizableGame {
  @SuppressLint("ClickableViewAccessibility")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    super.setContentView(R.layout.asteroids_layout);

    loginManager = (LoginManager) getIntent().getSerializableExtra("loginManager");
    settingsManager = (SettingsManager) getIntent().getSerializableExtra("settingsManager");
    leaderboardManager =
        (LeaderboardManager) getIntent().getSerializableExtra("leaderboardManager");
    setDifficulty(settingsManager.getSetting("difficulty"));
    setTheme(settingsManager.getSetting("theme"));
    setCharacter(settingsManager.getSetting("character"));

    gameView = this.setView();

    FrameLayout frameLayout = findViewById(R.id.frameLayout);
    frameLayout.addView(gameView);

    super.addRunningButton();

    ImageButton fireButton = findViewById(R.id.fireButton);
    ImageButton thrusterButton = findViewById(R.id.thrusterButton);
    final JoystickView joystick = new JoystickView(this);

    ((FrameLayout) findViewById(R.id.joystick)).addView(joystick);

    fireButton.setOnTouchListener(
        new GameView.OnTouchListener() {
          @Override
          public boolean onTouch(View view, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
              ((AsteroidGameManager) gameView.gameBackend).setFireActive(true);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
              ((AsteroidGameManager) gameView.gameBackend).setFireActive(false);
              return true;
            }
            return false;
          }
        });

    thrusterButton.setOnTouchListener(
        new GameView.OnTouchListener() {
          @Override
          public boolean onTouch(View view, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
              ((AsteroidGameManager) gameView.gameBackend).setThrusterActive(true);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
              ((AsteroidGameManager) gameView.gameBackend).setThrusterActive(false);
              return true;
            }
            return false;
          }
        });

    joystick.setOnTouchListener(
        new GameView.OnTouchListener() {
          @Override
          public boolean onTouch(View view, MotionEvent event) {
            joystick.onTouchEvent(event);
            if (!gameView.isPaused()) {
              ((AsteroidGameManager) gameView.gameBackend).setTargetDirection(joystick.getAngle());
            }
            return true;
          }
        });
  }

  @Override
  protected GameView setView() {
    return new AsteroidsView(
        this,
        Resources.getSystem().getDisplayMetrics().widthPixels,
        Resources.getSystem().getDisplayMetrics().heightPixels
            - (int)
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 96, getResources().getDisplayMetrics()));
  }

  protected void saveData() {
    String[] score = {"Score", "Asteroids Destroyed", "Powerups Collected"};
    String[] value = {
      String.valueOf(gameView.gameBackend.getCurrentScore()),
      String.valueOf(((AsteroidGameManager) gameView.gameBackend).getAsteroidsDestroyed()),
      String.valueOf(((AsteroidGameManager) gameView.gameBackend).getPowerupsCollected())
    };
    leaderboardManager.saveData(Games.ASTEROIDS, loginManager.getUsername(), score, value);
  }

  @Override
  public void setDifficulty(String difficulty) {
    if (difficulty.equals("easy")) {
      AsteroidCustomizations.splittingRatio = 2;
    } else if (difficulty.equals("medium")) {
      AsteroidCustomizations.splittingRatio = 3;
    } else {
      AsteroidCustomizations.splittingRatio = 4;
    }
  }

  @Override
  public void setCharacter(String character) {
    if (character.equals("one")) {
      AsteroidCustomizations.weaponOption = 0;
    } else {
      AsteroidCustomizations.weaponOption = 1;
    }
  }

  @Override
  public void setTheme(String theme) {
    if (theme.equals("dark")) {
      AsteroidCustomizations.themeIndex = 0;
    } else {
      AsteroidCustomizations.themeIndex = 1;
    }
  }
}
