package com.example.game.gamecode.MatchstickMen;

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

public class MatchstickMenActivity extends GameActivity implements CustomizableGame, Saver {
  /**
   * A flag recording whether the data of this game is saved.
   */
  private boolean saved = false;

  private int level = 0;
  private int color = Color.WHITE;
  private String character = "circle";

  /**
   * Make the customizations.
   *
   * @param level     the level of this game
   * @param theme     the theme of this game
   * @param character the character that will be shown on the screen
   */
  public void customization(String level, String theme, String character) {
//    this.level = level;
//    this.color = color;
//    this.character = character;

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

  private ProgressBar pgBar;
  private TextView timeleft, count;
  private Button btnAdd, btnMinus, btnDone;
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

    username = (String) getIntent().getSerializableExtra("username");
    settingsManager = (SettingsManager) getIntent().getSerializableExtra("settingsManager");
    leaderboardManager =
            (LeaderboardManager) getIntent().getSerializableExtra("leaderboardManager");

    customization(
            settingsManager.getSetting("difficulty"),
            settingsManager.getSetting("theme"),
            settingsManager.getSetting("character"));
    gameView = this.setView();
    final FrameLayout frameLayout = findViewById(R.id.canvas_matches);
    frameLayout.addView(gameView);
    // Process the count down display on progressbar and timeleft.
    pgBar = findViewById(R.id.progressBar);
    timeleft = findViewById(R.id.textTimeleft);

    pgBar.setProgress(0);

    // Count down and display the time left in the textbar on the upper right corner
    int totalTime = getTotalTime();

    final CountDownTimer timer =
            new CountDownTimer(totalTime * 1000, 1000) {
              int i = 0;

              @Override
              public void onTick(long l) {
                i++;
                pgBar.setProgress((int) i * 10);
                timeleft.setText(Float.toString(l / 1000) + "secs");
                  //TODO: fix progress bar
              }

              @Override
              public void onFinish() {
                timeleft.setText("Time's up!");
                i++;
                pgBar.setProgress(100);
                ((MatchstickMenBackend) gameView.gameBackend).setOver(true);
                if (!saved) {
                  String count =
                          Integer.toString(((MatchstickMenBackend) gameView.gameBackend).getCount());
                  String score = Integer.toString(gameView.gameBackend.getCurrentScore());
                  String timeUsed =
                          Integer.toString(((MatchstickMenBackend) gameView.gameBackend).getTimeUsed());
                  String[] stats = {"Count", "Score", "Time used"};
                  String[] values = {count, score, timeUsed};
                  leaderboardManager.saveData(Games.MATCHSTICKMEN, username, stats, values);
                }
              }
            }.start();

    // Display and control count.
    btnAdd = findViewById(R.id.btnAdd);
    btnMinus = findViewById(R.id.btnMinus);
    btnDone = findViewById(R.id.btnDone);

    count = findViewById(R.id.textCount);
    count.setText(Integer.toString(0));

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
  }

  @Override
  public void saveScore() {
    if (!saved) {
      String[] stats = {"Count", "Score", "Time"};
      String[] values = {String.valueOf(((MatchstickMenBackend) gameView.gameBackend).getCount()),
              String.valueOf((gameView.gameBackend).getCurrentScore()),
              String.valueOf(((MatchstickMenBackend) gameView.gameBackend).getTimeUsed())
      };
    }
  }

  @Override
  protected GameView setView() {
    return new MatchstickMenView(this, this);
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
}