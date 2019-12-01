package com.group0540.matchstickmenadventures.gamecode.matchstickmen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.matchstickmenadventures.R;
import com.group0540.matchstickmenadventures.leaderboardcode.LeaderboardManager;
import com.group0540.matchstickmenadventures.leaderboardcode.Saver;
import com.group0540.matchstickmenadventures.logincode.LoginManager;
import com.group0540.matchstickmenadventures.settingscode.CustomizableGame;
import com.group0540.matchstickmenadventures.settingscode.SettingsManager;

public class MatchstickMenActivitySinglePlayer extends SuperMatchstickMenActivity
    implements CustomizableGame, Saver {
  /** the add button */
  private Button btnAdd;
  /** the minus button */
  private Button btnMinus;
  /** the done button, press when you finish the game */
  private Button btnDone;
  /** the double player button, go to the double player mode */
  private Button doublePlayer;
  /** the count view, the number of matchstickMen the sure has counted */
  private TextView count;
  /** the number of matchstick men the user has counted */
  private int num = 0;

  /**
   * the getter for the variable num
   *
   * @return the number of matchstick men the user has counted
   */
  public int getNum() {
    return num;
  }

  /**
   * set the num variable
   *
   * @param num the number of matchstick men the user has counted
   */
  public void setNum(int num) {
    this.num = num;
  }

  /**
   * the creation of the activity (the game)
   *
   * @param savedInstanceState the activity's previously saved state
   */
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.matchstickmen_layout);

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

    setUpTimer();

    setCount();
  }

  /** link all the view variables with its corresponding ID in xml */
  @Override
  void setButtons() {

    super.pgBar = findViewById(R.id.progressBar);
    doublePlayer = findViewById(R.id.nextLevel);
    restart = findViewById(R.id.restart);
    timeleft = findViewById(R.id.textTimeleft);
    btnAdd = findViewById(R.id.btnAdd);
    btnMinus = findViewById(R.id.btnMinus);
    btnDone = findViewById(R.id.btnDone);
    count = findViewById(R.id.textCount);
  }

  /** set the onClickListeners for all the buttons */
  @Override
  void setOnclickListeners() {
    btnAdd.setOnClickListener(
        new View.OnClickListener() {

          @Override
          public void onClick(View view) {
            if (!gameView.gameBackend.isGameOver()) {
              setNum(getNum() + 1);
              String strCount = Integer.toString(getNum());
              count.setText(strCount);
              ((MatchstickMenBackend) gameView.gameBackend).addCount();
            }
          }
        });

    btnMinus.setOnClickListener(
        new View.OnClickListener() {

          @Override
          public void onClick(View view) {
            if (!(gameView.gameBackend).isGameOver()) {
              setNum(getNum() - 1);
              String strCount = Integer.toString(getNum());
              count.setText(strCount);
              ((MatchstickMenBackend) gameView.gameBackend).minusCount();
            }
          }
        });

    btnDone.setOnClickListener(
        new View.OnClickListener() {

          @Override
          public void onClick(View view) {
            if (!gameView.gameBackend.isGameOver()) {
              String result = count.getText().toString();
              if (((MatchstickMenBackend) gameView.gameBackend).compare(result)) {
                  ((MatchstickMenBackend) gameView.gameBackend).score += ((MatchstickMenBackend) gameView.gameBackend).getAnswer();
                count.setTextSize(30);
                count.setText(R.string.correct);
                timer.cancel();
                ((MatchstickMenBackend) gameView.gameBackend).setOver(true);

              } else {
                count.setTextSize(30);
                count.setText(R.string.wrong);
              }
              String text = timeleft.getText().toString();
              String time_remaining = text.substring(0, text.indexOf('.'));
              ((MatchstickMenBackend) gameView.gameBackend).setTimeUsed(time_remaining);
            }
          }
        });

    doublePlayer.setOnClickListener(
        new View.OnClickListener() {
          public void onClick(View view) {
            Intent mainIntent =
                    new Intent(MatchstickMenActivitySinglePlayer.this, MatchstickMenActivityDoublePlayer.class);
            sendToIntent(mainIntent);
            MatchstickMenActivitySinglePlayer.this.startActivity(mainIntent);
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

  /** set the count TextView, the initial value is 0 */
  @Override
  void setCount() {
    String zero = Integer.toString(0);
    count.setText(zero);
  }
}
