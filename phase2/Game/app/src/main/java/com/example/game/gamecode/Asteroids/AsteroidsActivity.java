package com.example.game.gamecode.Asteroids;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.game.Games;
import com.example.game.R;
import com.example.game.gamecode.CustomControls.JoystickView;
import com.example.game.gamecode.GameActivity;
import com.example.game.gamecode.GameView;
import com.example.game.leaderboardcode.LeaderboardManager;
import com.example.game.leaderboardcode.Saver;
import com.example.game.settingscode.CustomizableGame;
import com.example.game.settingscode.SettingsManager;

public class AsteroidsActivity extends GameActivity implements Saver, CustomizableGame {
  @SuppressLint("ClickableViewAccessibility")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    super.setContentView(R.layout.asteroids_layout);

    username = (String) getIntent().getSerializableExtra("username");
    settingsManager = (SettingsManager) getIntent().getSerializableExtra("settingsManager");
    leaderboardManager =
        (LeaderboardManager) getIntent().getSerializableExtra("leaderboardManager");

//    Asteroid.appearance = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid);
//
//    Ship.appearance = BitmapFactory.decodeResource(getResources(), R.drawable.ship);
//
//    Projectile.appearance = BitmapFactory.decodeResource(getResources(), R.drawable.laser);

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

  protected void saveData() {String[] score = {"Score"};
    String[] value = {String.valueOf(gameView.gameBackend.getCurrentScore())};
    leaderboardManager.saveData(
            Games.ASTEROIDS,
            username,
            score,
            value);
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
