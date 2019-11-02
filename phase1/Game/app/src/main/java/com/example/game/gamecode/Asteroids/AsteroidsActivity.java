package com.example.game.gamecode.Asteroids;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;

import com.example.game.Games;
import com.example.game.MainMenuScreen;
import com.example.game.R;
import com.example.game.gamecode.CustomControls.JoystickView;
import com.example.game.gamecode.GameActivity;
import com.example.game.gamecode.GameView;
import com.example.game.leaderboardcode.LeaderboardManager;
import com.example.game.leaderboardcode.Saver;
import com.example.game.settingscode.Customizable;
import com.example.game.settingscode.CustomizableGame;
import com.example.game.settingscode.SettingsManager;

import java.io.IOException;

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

    Asteroid.appearance = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid);

    Ship.appearance = BitmapFactory.decodeResource(getResources(), R.drawable.ship);

    Projectile.appearance = BitmapFactory.decodeResource(getResources(), R.drawable.laser);

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

  @Override
  public void saveScore() {
    final GameActivity currentGame = this;
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        AlertDialog.Builder builder = new AlertDialog.Builder(currentGame);
        builder.setCancelable(false);
        builder.setTitle("You've died! :(");

        LinearLayout rows = new LinearLayout(currentGame);
        rows.setOrientation(LinearLayout.VERTICAL);
        builder.setView(rows);

        LinearLayout bottomButtons = new LinearLayout(currentGame);
        bottomButtons.setOrientation(LinearLayout.HORIZONTAL);
        bottomButtons.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                0.5f);

        final ToggleButton saveToggle = new ToggleButton(currentGame);
        final Button retryButton = new Button(currentGame);
        final Button mainMenuButton = new Button(currentGame);
        retryButton.setLayoutParams(buttonParams);
        mainMenuButton.setLayoutParams(buttonParams);

        saveToggle.setTextOn("Save?");
        saveToggle.setTextOff("Save off");
        retryButton.setText("Retry");
        mainMenuButton.setText("Main Menu");

        retryButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent mainIntent = new Intent(AsteroidsActivity.this, AsteroidsActivity.class);
            if (saveToggle.isChecked()) {
              saveData();
            }
            AsteroidsActivity.this.startActivity(mainIntent);
          }
        });

        mainMenuButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent mainIntent = new Intent(AsteroidsActivity.this, MainMenuScreen.class);
            if (saveToggle.isChecked()) {
              saveData();
            }
            AsteroidsActivity.this.startActivity(mainIntent);
          }
        });

        bottomButtons.addView(retryButton);
        bottomButtons.addView(mainMenuButton);

        rows.addView(saveToggle);
        rows.addView(bottomButtons);

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
      }
    });
  }

  private void saveData() {String[] score = {"Score"};
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
