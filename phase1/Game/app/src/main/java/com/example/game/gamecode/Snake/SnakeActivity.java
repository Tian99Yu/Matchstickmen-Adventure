package com.example.game.gamecode.Snake;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.game.Games;
import com.example.game.R;
import com.example.game.gamecode.GameActivity;
import com.example.game.gamecode.GameView;
import com.example.game.leaderboardcode.LeaderboardManager;
import com.example.game.settingscode.Customizable;
import com.example.game.settingscode.SettingsManager;

import java.io.IOException;

public class SnakeActivity extends GameActivity implements Customizable {
    Button LeftButton;
    Button RightButton;
    private LeaderboardManager leaderboardManager;
    private SettingsManager settingsManager;
    private String username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = (String) getIntent().getSerializableExtra("username");
        settingsManager = (SettingsManager) getIntent().getSerializableExtra("settingsManager");
        leaderboardManager = (LeaderboardManager) getIntent().getSerializableExtra("leaderboardManager");

        setContentView(R.layout.snake_layout);

        LeftButton = findViewById(R.id.LeftButton);
        RightButton = findViewById(R.id.RightButton);

        // add the gameview as a second layout:
        gameView = this.setView();
        FrameLayout frameLayout = findViewById(R.id.snakeLayout);
        frameLayout.addView(gameView);

        // super.addRunningButton();

        View.OnClickListener leftListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SnakeBackend) gameView.gameBackend).turnSnake(TurnDirection.LEFT);
            }
        };

        LeftButton.setOnClickListener(leftListener);


        View.OnClickListener rightListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SnakeBackend) gameView.gameBackend).turnSnake(TurnDirection.RIGHT);
            }
        };

        RightButton.setOnClickListener(rightListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        try{
            String[][] statistic = ((SnakeView)gameView).getStatistics();
            for (int i = 0; i < statistic.length; i++){
                this.leaderboardManager.saveData(Games.SNAKE, this.username, statistic[0][i]
                        , statistic[1][i]);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    protected GameView setView() {
        return new SnakeView(this);
    }

    @Override
    public void setDifficulty() {
    }

    @Override
    public void setCharacter() {

    }

    @Override
    public void setBackground() {

    }
}
