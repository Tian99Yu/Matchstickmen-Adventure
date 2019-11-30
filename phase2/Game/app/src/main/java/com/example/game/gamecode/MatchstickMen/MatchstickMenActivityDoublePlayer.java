package com.example.game.gamecode.MatchstickMen;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.game.Games;
import com.example.game.R;
import com.example.game.gamecode.GameActivity;
import com.example.game.gamecode.GameView;
import com.example.game.leaderboardcode.LeaderboardManager;
import com.example.game.leaderboardcode.Saver;
import com.example.game.settingscode.CustomizableGame;
import com.example.game.settingscode.SettingsManager;

import java.util.ArrayList;

public class MatchstickMenActivityDoublePlayer extends SuperMatchstickMenActivity
    implements CustomizableGame, Saver {
  /** A flag recording whether the data of this game is saved. */
  private boolean saved = false;

  private int level = 0;
  private int color = Color.WHITE;
  private String character = "circle";
  private CountDownTimer timer;

  /**
   * Make the customizations.
   *
   * @param level the level of this game
   * @param theme the theme of this game
   * @param character the character that will be shown on the screen
   */
  public void customization(String level, String theme, String character) {

    setDifficulty(level);
    setTheme(theme);
    setCharacter(character);
  }

  public int getLevel() {
    return level;
  }

  public int getColor() {
    return color;
  }

  public String getCharacter() {
    return character;
  }

  //    private ProgressBar pgBar;
  private TextView count1, count2;
  private Button addP1, addP2, minusP1, minusP2, restart, singlePlayer, btnDone;
  private int num1 = 0;
  private int num2 = 0;

  public int getNum2() {
    return num2;
  }

  public void setNum2(int num2) {
    this.num2 = num2;
  }

  public int getNum1() {
    return num1;
  }

  public void setNum1(int num) {
    this.num1 = num;
  }

  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.matchstickmen_double_layout);

    username = (String) getIntent().getSerializableExtra("username");
    settingsManager = (SettingsManager) getIntent().getSerializableExtra("settingsManager");
    leaderboardManager =
        (LeaderboardManager) getIntent().getSerializableExtra("leaderboardManager");

    customization(
        settingsManager.getSetting("difficulty"),
        settingsManager.getSetting("theme"),
        settingsManager.getSetting("character"));

    gameView = this.setView();
    final FrameLayout frameLayout = findViewById(R.id.matchstickManSurface);
    frameLayout.addView(gameView);

    setButtons();
    setOnclickListeners();

    pgBar.setProgress(0);

    // Count down and display the time left in the textbar on the upper right corner
    timer = setTimers(pgBar, timeleft);
    timer.start();
    setCount();

    restart.setOnClickListener(
        new View.OnClickListener() {

          @Override
          public void onClick(View view) {
            finish();
            startActivity(getIntent());
          }
        });
  }

  @Override
  protected void saveData() {
    if (!saved) {
      String[] stats = {"Count", "Score", "Time"};
      String[] values = {
        String.valueOf(((MatchstickMenBackend) gameView.gameBackend).getCount()),
        String.valueOf((gameView.gameBackend).getCurrentScore()),
        String.valueOf(((MatchstickMenBackend) gameView.gameBackend).getTimeUsed())
      };
    }
  }

  @Override
  protected GameView setView() {
    return new MatchstickMenView(this);
  }

  public int getTotalTime() {
    int totalTime;
    switch (level) {
      case 0:
        totalTime = 10;
        break;
      case 1:
        totalTime = 7;
        break;
      case 2:
        totalTime = 5;
        break;
      default:
        totalTime = 10;
    }
    ;
    return totalTime;
  }

  @Override
  public void setDifficulty(String difficulty) {
    if (difficulty.equals("easy")) {
      this.level = 0;
    } else if (difficulty.equals("medium")) {
      this.level = 1;
    } else {
      this.level = 2;
    }
  }

  @Override
  public void setCharacter(String character) {
    if (character.equals("one")) {
      this.character = "rect";
    } else {
      this.character = "circle";
    }
  }

  @Override
  public void setTheme(String theme) {
    if (theme.equals("dark")) {
      this.color = Color.MAGENTA;
    } else {
      this.color = Color.WHITE;
    }
  }

  @Override
  void setButtons() {

    // the buttons for the activity
    pgBar = findViewById(R.id.progressBarDouble);
    singlePlayer = findViewById(R.id.singlePlayer);
    restart = findViewById(R.id.restartDouble);
    timeleft = findViewById(R.id.textTimeleftDouble);
    addP1 = findViewById(R.id.addP1);
    addP2 = findViewById(R.id.addP2);
    minusP1 = findViewById(R.id.minusP1);
    minusP2 = findViewById(R.id.minusP2);
    btnDone = findViewById(R.id.doneDouble);
    // the TextView
    count1 = findViewById(R.id.countP1);
    count2 = findViewById(R.id.countP2);
  }

  @Override
  void setOnclickListeners() {

    addP1.setOnClickListener(
        new View.OnClickListener() {
          int i = getNum1();

          @Override
          public void onClick(View view) {
            if (!gameView.gameBackend.isGameOver()) {
              setNum1(getNum1() + 1);
              count1.setText(Integer.toString(getNum1()));
              ((MatchstickMenBackend) gameView.gameBackend).addCount();
            }
          }
        });

    addP2.setOnClickListener(
        new View.OnClickListener() {
          int i = getNum2();

          @Override
          public void onClick(View view) {
            if (!gameView.gameBackend.isGameOver()) {
              setNum2(getNum2() + 1);
              count2.setText(Integer.toString(getNum2()));
              ((MatchstickMenBackend) gameView.gameBackend).addCount2();
            }
          }
        });

    minusP1.setOnClickListener(
        new View.OnClickListener() {

          int i = getNum1();

          @Override
          public void onClick(View view) {
            if (!(gameView.gameBackend).isGameOver()) {
              setNum1(getNum1() - 1);

              count1.setText(Integer.toString(getNum1()));
              ((MatchstickMenBackend) gameView.gameBackend).minusCount();
            }
          }
        });

    minusP2.setOnClickListener(
        new View.OnClickListener() {

          int i = getNum2();

          @Override
          public void onClick(View view) {
            if (!(gameView.gameBackend).isGameOver()) {
              setNum2(getNum2() - 1);

              count1.setText(Integer.toString(getNum2()));
              ((MatchstickMenBackend) gameView.gameBackend).minusCount2();
            }
          }
        });

    btnDone.setOnClickListener(
        new View.OnClickListener() {

          @Override
          public void onClick(View view) {
            if (!gameView.gameBackend.isGameOver()) {
              String result1 = count1.getText().toString();
              String result2 = count2.getText().toString();
              String compareOutput =
                  (((MatchstickMenBackend) gameView.gameBackend).compare(result1, result2));
              count1.setTextSize((10));
              count1.setText(compareOutput);
              count2.setTextSize(10);
              count2.setText(compareOutput);

              ArrayList<String> listOutput = new ArrayList<>();
              listOutput.add("you both win");
              listOutput.add("player1 wins");
              listOutput.add("player2 wins");

              if (listOutput.contains(compareOutput)) {
                timer.cancel();
                ((MatchstickMenBackend) gameView.gameBackend).setOver(true);
              }
              String text = MatchstickMenActivityDoublePlayer.super.timeleft.getText().toString();
              String timeRemaining = text.substring(0, text.indexOf('.'));
              ((MatchstickMenBackend) gameView.gameBackend).setTimeUsed(timeRemaining);
            }
          }
        });

    singlePlayer.setOnClickListener(
        new GameView.OnClickListener() {
          public void onClick(View view) {
            Intent mainIntent =
                new Intent(MatchstickMenActivityDoublePlayer.this, MatchstickMenActivity.class);
            sendToIntent(mainIntent);
            MatchstickMenActivityDoublePlayer.this.startActivity(mainIntent);
          }
        });
  }

  @Override
  void setCount() {
    count1.setText(Integer.toString(0));
    count2.setText(Integer.toString(0));
  }

  private void sendToIntent(Intent intent) {
    intent.putExtra("username", username);
    intent.putExtra("leaderboardManager", leaderboardManager);
    intent.putExtra("settingsManager", settingsManager);
  }
}
