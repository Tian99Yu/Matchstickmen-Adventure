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

public class MatchstickMenActivity extends SuperMatchstickMenActivity
    implements CustomizableGame, Saver {
  /** A flag recording whether the data of this game is saved. */


  private Button btnAdd, btnMinus, btnDone, nextLevel;
  private TextView count;
//  private CountDownTimer timer;
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
    final FrameLayout frameLayout = findViewById(R.id.matchstickManSurface);
    frameLayout.addView(gameView);
    MatchstickMenView matchstickMenView = ((MatchstickMenView) gameView);
    matchstickMenView.setMatchstickMenCustomization(matchstickMenCustomization);
    matchstickMenView.setDifficulty();

    setButtons();
    setOnclickListeners();

    setUpTimer();



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
  void setButtons() {

    super.pgBar = findViewById(R.id.progressBar);
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


}
