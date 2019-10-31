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

import com.example.game.R;
import com.example.game.gamecode.GameActivity;
import com.example.game.gamecode.GameView;

public class MatchstickMenActivity extends GameActivity {


    private int level = 0;
    private int color = Color.WHITE;
    private String character = "circle";



    public void customization(int level, int color, String character){
        this.level = level;
        this.color = color;
        this.character = character;
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

    private ProgressBar pgbar;
  private TextView timeleft, count;
  private Button btn_add, btn_minus, btn_done, btn_restart;
  private int num = 0;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    // private ImageView;

  protected void onCreate(Bundle savedInstanceState) {
        customization(2,Color.YELLOW, "rect");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.matchstickmen_layout);
    gameView = this.setView();
    FrameLayout frameLayout = findViewById(R.id.canvas_matches);
    frameLayout.addView(gameView);

    // Display and control count.
    btn_add = findViewById(R.id.btn_add);
    btn_minus = findViewById(R.id.btn_minus);
    btn_done = findViewById(R.id.btn_done);
    btn_restart = findViewById(R.id.btn_restart);

    count = findViewById(R.id.text_count);
    count.setText(Integer.toString(0));

    btn_add.setOnClickListener(
        new View.OnClickListener() {
          int i = getNum();

          @Override
          public void onClick(View view) {
            setNum(getNum() + 1);
            count.setText(Integer.toString(getNum()));
            ((MatchstickMenBackend) gameView.gameBackend).addCount();
          }
        });

    btn_minus.setOnClickListener(
        new View.OnClickListener() {
          int i = getNum();

          @Override
          public void onClick(View view) {
            setNum(getNum()-1);
            count.setText(Integer.toString(getNum()));
            ((MatchstickMenBackend) gameView.gameBackend).minusCount();
          }
        });

    btn_done.setOnClickListener(
        new View.OnClickListener() {

          @Override
          public void onClick(View view) {
              String result = count.getText().toString();
              if (((MatchstickMenBackend) gameView.gameBackend).compare(result)) {
              ((MatchstickMenBackend) gameView.gameBackend).score += 1;
                count.setTextSize(30);
                count.setText("Correct!!! :)");
            }
            else{
                count.setTextSize(30);
                count.setText("Wrong -_-");
            }
              String text = timeleft.getText().toString();
            String time_remaining = text.substring(0, text.indexOf('.'));
              ((MatchstickMenBackend) gameView.gameBackend).setTimeUsed(time_remaining);
              gameView.gameBackend.update();
          }
        });

    // Restart the gameBackend
    btn_restart.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              gameView.toggleRunning();
              Intent intent = new Intent(MatchstickMenActivity.this, MatchstickMenActivity.class);
              startActivity(intent);

          }
        });

    // Process the count down display on progressbar and timeleft.
    pgbar = findViewById(R.id.progressBar);
    timeleft = findViewById(R.id.text_timeleft);

    pgbar.setProgress(0);


    // Count down and display the time left in the textbar on the upper right corner
    int totalTime = getTotalTime();

    CountDownTimer timer =
        new CountDownTimer(totalTime*1000, 1000) {
          int i = 0;

          @Override
          public void onTick(long l) {
            i++;
            pgbar.setProgress((int) i * 10);
            timeleft.setText(Float.toString(l / 1000) + "secs");
          }

          @Override
          public void onFinish() {
            timeleft.setText("Time's up!");
            i++;
            pgbar.setProgress(100);

          }
        }.start();
  }

  @Override
  protected GameView setView() {
    return new MatchstickMenView(this, this);
  }

    public int getTotalTime () {
        int totalTime;
        switch (level){

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
        };
        return totalTime;
    }
}
