package com.example.game.gamecode.MatchstickMen;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.CountDownTimer;

import com.example.game.R;
import com.example.game.gamecode.GameActivity;
import com.example.game.gamecode.GameView;

public class MatchstickMenActivity extends GameActivity {

    private ProgressBar pgbar;
    private TextView timeleft, count;
    private Button btn_add, btn_restart;
    //private ImageView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matchstickmen_layout);

        //处理显示count和加count
        btn_add = findViewById(R.id.btn_add);
        btn_restart = findViewById(R.id.btn_restart);

        count.setText(0);

    btn_add.setOnClickListener(new View.OnClickListener(){
        int i = 0;
        @Override
        public void onClick(View view){
            i++;
            count.setText(i);
            //backend数据也要更新一下

        }
    });

    btn_restart.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view){
            //重新生成图片们
        }
    });

        //处理progressbar和timeleft的倒计时显示
        pgbar = findViewById(R.id.progressBar);
        timeleft = findViewById(R.id.text_timeleft);

        pgbar.setProgress(0);
        //Count down and display the time left in the textbar on the upper right corner
        CountDownTimer timer = new CountDownTimer(10000,1000) {
            int i = 0;

            @Override
            public void onTick(long l) {
                i++;
                pgbar.setProgress((int)i*10);
                timeleft.setText(l / 1000 + "secs");
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
        return null;
    }
}
