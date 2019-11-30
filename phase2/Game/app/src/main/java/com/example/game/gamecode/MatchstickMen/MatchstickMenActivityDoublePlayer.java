package com.example.game.gamecode.MatchstickMen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.game.R;
import com.example.game.gamecode.GameView;
import com.example.game.leaderboardcode.LeaderboardManager;
import com.example.game.leaderboardcode.Saver;
import com.example.game.logincode.LoginManager;
import com.example.game.settingscode.CustomizableGame;
import com.example.game.settingscode.SettingsManager;

import java.util.ArrayList;

public class MatchstickMenActivityDoublePlayer extends SuperMatchstickMenActivity
    implements CustomizableGame, Saver {
  /** shows player one how many matchStickMen he has counted */
  private TextView count1;
  /** shows player two how many matchstickMen he has counted */
  private TextView count2;
  /** the add button for player one to add one more matchstickMen */
  private Button addP1;
  /** the add button for player two to add one more matchstick men */
  private Button addP2;
  /**
   * the minus button for player one to subtract one from the number of matchstick men he has
   * counted
   */
  private Button minusP1;
  /**
   * the minus button for player two to subtract one from the number of matchstick men he has
   * counted
   */
  private Button minusP2;
  /** the restart Button for the game */
  private Button restart;
  /** the single player button, press to go to the single player mode */
  private Button singlePlayer;
  /** the done button, press to check your answer before time is up */
  private Button btnDone;
  /** the number of matchstick men player one has counted */
  private int num1 = 0;
  /** the number of matchstick men player two has counted */
  private int num2 = 0;

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

  /**
   * getter for variable num2
   *
   * @return the number of matchstick men player2 has counted
   */
  public int getNum2() {
    return num2;
  }

  /**
   * setter for variable num2
   *
   * @param num2 the number of matchstick men player2 has counted
   */
  public void setNum2(int num2) {
    this.num2 = num2;
  }

  /**
   * getter for variable num1
   *
   * @return the number of matchstick men player 1 has counted
   */
  public int getNum1() {
    return num1;
  }

  /**
   * setter for variable num1
   *
   * @param num the number of matchstick men player 1 has counted
   */
  public void setNum1(int num) {
    this.num1 = num;
  }

  /**
   * create the view, (the entire game actually)
   *
   * @param savedInstanceState the activity's previously saved state
   */
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.matchstickmen_double_layout);

    loginManager = (LoginManager) getIntent().getSerializableExtra("loginManager");
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
    MatchstickMenView matchstickMenView = ((MatchstickMenView) gameView);
    matchstickMenView.setMatchstickMenCustomization(matchstickMenCustomization);
    matchstickMenView.setDifficulty();

    setButtons();
    setOnclickListeners();

    pgBar.setProgress(0);

    // Count down and display the time left in the text bar on the upper right corner
    timer = setTimers(pgBar, timeleft);
    timer.start();
    setCount();
  }

  /** link all view widgets with its corresponding id in the xml */
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

  /** set onClickListeners for all the buttons */
  @Override
  void setOnclickListeners() {

    addP1.setOnClickListener(
        new View.OnClickListener() {

          @Override
          public void onClick(View view) {
            if (!gameView.gameBackend.isGameOver()) {
              setNum1(getNum1() + 1);
              String strCount1 = Integer.toString(getNum1());
              count1.setText(strCount1);
              ((MatchstickMenBackend) gameView.gameBackend).addCount();
            }
          }
        });

    addP2.setOnClickListener(
        new View.OnClickListener() {

          @Override
          public void onClick(View view) {
            if (!gameView.gameBackend.isGameOver()) {
              setNum2(getNum2() + 1);
              String strCount2 = Integer.toString(getNum2());
              count2.setText(strCount2);
              ((MatchstickMenBackend) gameView.gameBackend).addCount2();
            }
          }
        });

    minusP1.setOnClickListener(
        new View.OnClickListener() {

          @Override
          public void onClick(View view) {
            if (!(gameView.gameBackend).isGameOver()) {
              setNum1(getNum1() - 1);
              String strCount1 = Integer.toString(getNum1());
              count1.setText(strCount1);
              ((MatchstickMenBackend) gameView.gameBackend).minusCount();
            }
          }
        });

    minusP2.setOnClickListener(
        new View.OnClickListener() {

          @Override
          public void onClick(View view) {
            if (!(gameView.gameBackend).isGameOver()) {
              setNum2(getNum2() - 1);
              String strCount1 = Integer.toString(getNum2());
              count2.setText(strCount1);
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

    restart.setOnClickListener(
        new View.OnClickListener() {

          @Override
          public void onClick(View view) {
            finish();
            startActivity(getIntent());
          }
        });
  }

  /** set the initial value for the count view in the screen */
  @Override
  void setCount() {
    String zero = Integer.toString(0);
    count1.setText(zero);
    count2.setText(zero);
  }
}
