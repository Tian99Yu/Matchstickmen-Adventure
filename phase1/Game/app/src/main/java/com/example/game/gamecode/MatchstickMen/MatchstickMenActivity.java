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
import com.example.game.settingscode.Customizable;
import com.example.game.settingscode.SettingsManager;

import java.io.IOException;

public class MatchstickMenActivity extends GameActivity implements Customizable {
  private LeaderboardManager leaderboardManager;
  private SettingsManager settingsManager;
  private String username;

  /** A flag recording whether the data of this game is saved. */
  private boolean saved = false;

  private int level = 0;
  private int color = Color.WHITE;
  private String character = "circle";

  public void customization(int level, String theme, String character) {
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
  private Button btnAdd, btnMinus, btnDone, btnRestart;
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


    customization(level, settingsManager.getSetting("theme"), character);
//    setCharacter();
//    setBackground();
//    setDifficulty();

    gameView = this.setView();
    final FrameLayout frameLayout = findViewById(R.id.canvas_matches);
    frameLayout.addView(gameView);
    // Process the count down display on progressbar and timeleft.
    pgBar = findViewById(R.id.progressBar);
    timeleft = findViewById(R.id.text_timeleft);

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
          }

          @Override
          public void onFinish() {
            timeleft.setText("Time's up!");
            i++;
            pgBar.setProgress(100);
            ((MatchstickMenBackend) gameView.gameBackend).setOver(true);
            if (!saved) {
              try {
                String count =
                    Integer.toString(((MatchstickMenBackend) gameView.gameBackend).getCount());
                String score = Integer.toString(gameView.gameBackend.getCurrentScore());
                String timeUsed =
                    Integer.toString(((MatchstickMenBackend) gameView.gameBackend).getTimeUsed());
                leaderboardManager.saveData(Games.MATCHSTICKMEN, username, "Count", count);
                leaderboardManager.saveData(Games.MATCHSTICKMEN, username, "Score", score);
                leaderboardManager.saveData(Games.MATCHSTICKMEN, username, "Time used", timeUsed);
                saved = true;
              } catch (IOException e) {
                e.printStackTrace();
              }
            }
          }
        }.start();

    // Display and control count.
    btnAdd = findViewById(R.id.btn_add);
    btnMinus = findViewById(R.id.btn_minus);
    btnDone = findViewById(R.id.btn_done);
    btnRestart = findViewById(R.id.btn_restart);

    count = findViewById(R.id.text_count);
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
            //              String result = count.getText().toString();
            //
            //              while(((MatchstickMenBackend) gameView.gameBackend).compare(result)) {
            //                  ((MatchstickMenBackend)
            // gameView.gameBackend).draw(((MatchstickMenView) gameView).canvas);

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
              if (!saved) {
                try {
                  String count =
                      Integer.toString(((MatchstickMenBackend) gameView.gameBackend).getCount());
                  String score = Integer.toString(gameView.gameBackend.getCurrentScore());
                  String timeused =
                      Integer.toString(((MatchstickMenBackend) gameView.gameBackend).getTimeUsed());
                  leaderboardManager.saveData(Games.MATCHSTICKMEN, username, "Count", count);
                  leaderboardManager.saveData(Games.MATCHSTICKMEN, username, "Score", score);
                  leaderboardManager.saveData(Games.MATCHSTICKMEN, username, "Time used", timeused);
                  saved = true;
                } catch (IOException e) {
                  e.printStackTrace();
                }
              }
              //              gameView.gameBackend.update();
            }
          }
        });

    // Restart the gameBackend
//    btnRestart.setOnClickListener(
//        new View.OnClickListener() {
//          @Override
//          public void onClick(View view) {
//
//            //              gameView.togglePause();
//
//            Intent intent = getIntent();
//            new Intent(MatchstickMenActivity.this, MatchstickMenActivity.class);
//            ////              intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//            //              ((MatchstickMenBackend) gameView.gameBackend).setOver(false);
//            //            System.out.println();
//            //            onCreate(null);
//            //              MatchstickMenActivity.super.onStop();
//          }
//        });
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
  public void setDifficulty(int difficulty) {
    this.level = difficulty;
  }

  @Override
  public void setCharacter(String character) {
    this.character = character;
  }

  @Override
  public void setTheme(String theme) {
    if (theme == "dark") {
      this.color = Color.BLACK;
    } else {
      this.color = Color.WHITE;
    }
      //    final FrameLayout frameLayout = findViewById(R.id.canvas_matches);
//    frameLayout.setBackgroundColor(color);
  }
}
