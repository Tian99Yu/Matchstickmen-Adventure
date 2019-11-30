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
import com.example.game.logincode.LoginManager;
import com.example.game.settingscode.CustomizableGame;
import com.example.game.settingscode.SettingsManager;

public class MatchstickMenActivity extends SuperMatchstickMenActivity
    implements CustomizableGame, Saver {
  /** A flag recording whether the data of this game is saved. */
  private boolean saved = false;

  private int level = 0;
  private int color = Color.WHITE;
    private MatchstickMenType character = MatchstickMenType.HAPPY_MAN;

  /** The customizations of this matchstick men game */
  MatchstickMenCustomization matchstickMenCustomization = new MatchstickMenCustomization();

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

  private Button btnAdd, btnMinus, btnDone, nextLevel;
  private TextView count;
  private CountDownTimer timer;
  private int num = 0;

  public int getNum() {
    return num;
  }

  public void setNum(int num) {
    this.num = num;
  }

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

    pgBar.setProgress(0);

    // Count down and display the time left in the textbar on the upper right corner
    //    final int totalTime = getTotalTime();

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
    switch (matchstickMenCustomization.getDifficulty()) {
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
    this.matchstickMenCustomization.setDifficulty(difficulty);
  }

  @Override
  public void setCharacter(String character) {
    this.matchstickMenCustomization.setCharacter(character);
  }

  @Override
  public void setTheme(String theme) {
    this.matchstickMenCustomization.setTheme(theme);
  }

  @Override
  void setButtons() {

    pgBar = findViewById(R.id.progressBar);
    nextLevel = findViewById(R.id.nextLevel);
    restart = findViewById(R.id.restart);
    timeleft = findViewById(R.id.textTimeleft);
    btnAdd = findViewById(R.id.btnAdd);
    btnMinus = findViewById(R.id.btnMinus);
    btnDone = findViewById(R.id.btnDone);
    count = findViewById(R.id.textCount);
  }

  @Override
  void setOnclickListeners() {

    btnAdd.setOnClickListener(
        new View.OnClickListener() {
          int i = getNum();

          @Override
          public void onClick(View view) {
            if (!gameView.gameBackend.isGameOver()) {
              setNum(getNum() + 1);
              count.setText(Integer.toString(getNum()));
              ((MatchstickMenBackend) gameView.gameBackend).addCount();
            }
          }
        });

    btnMinus.setOnClickListener(
        new View.OnClickListener() {

          int i = getNum();

          @Override
          public void onClick(View view) {
            if (!(gameView.gameBackend).isGameOver()) {
              setNum(getNum() - 1);

              count.setText(Integer.toString(getNum()));
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
                ((MatchstickMenBackend) gameView.gameBackend).score += 1;
                count.setTextSize(30);
                count.setText("Correct!!! :)");
                timer.cancel();
                ((MatchstickMenBackend) gameView.gameBackend).setOver(true);

              } else {
                count.setTextSize(30);
                count.setText("Wrong -_-");
              }
              String text = timeleft.getText().toString();
              String time_remaining = text.substring(0, text.indexOf('.'));
              ((MatchstickMenBackend) gameView.gameBackend).setTimeUsed(time_remaining);
            }
          }
        });

    ((MatchstickMenBackend) gameView.gameBackend).setLevelNum(1);

    nextLevel.setOnClickListener(
        new View.OnClickListener() {
          public void onClick(View view) {
            Intent mainIntent =
                new Intent(MatchstickMenActivity.this, MatchstickMenActivityDoublePlayer.class);
            sendToIntent(mainIntent);
            MatchstickMenActivity.this.startActivity(mainIntent);
          }
        });
  }

  @Override
  void setCount() {
    count.setText(Integer.toString(0));
  }

  private void sendToIntent(Intent intent) {
    intent.putExtra("loginManager", loginManager);
    intent.putExtra("leaderboardManager", leaderboardManager);
    intent.putExtra("settingsManager", settingsManager);
  }
}
