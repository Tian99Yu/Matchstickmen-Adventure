package com.example.game.gamecode.Snake;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.game.Games;
import com.example.game.R;
import com.example.game.gamecode.GameActivity;
import com.example.game.gamecode.GameView;
import com.example.game.leaderboardcode.LeaderboardManager;
import com.example.game.leaderboardcode.Saver;
import com.example.game.settingscode.Customizable;
import com.example.game.settingscode.CustomizableGame;
import com.example.game.settingscode.SettingsManager;

import java.io.IOException;

public class SnakeActivity extends GameActivity implements Saver, CustomizableGame {
    Button LeftButton;
    Button RightButton;

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

        setCustomization(settingsManager.getSetting("difficulty"), settingsManager.getSetting("theme"),
                settingsManager.getSetting("character"));

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
    public void saveScore() {
        String[][] statistic = ((SnakeView)gameView).getStatistics();
        this.leaderboardManager.saveData(Games.SNAKE, this.username, statistic[0], statistic[1]);
    }

    /**
     * Create and return a new view from this activity
     * @return the crated view
     */
    protected GameView setView() {
        return new SnakeView(this);
    }

    @Override
    public void setDifficulty(String difficulty) {
        SnakeView snakeView = (SnakeView)this.gameView;
        if (difficulty.equals("easy")) {
            snakeView.setDifficulty(3);
        } else if (difficulty.equals("medium")) {
            snakeView.setDifficulty(5);
        } else {
            snakeView.setDifficulty(10);
        }
    }

    @Override
    public void setCharacter(String character) {
        if (character.equals("one")){
            ((SnakeView)this.gameView).setCharacter(SnakeShape.CIRCLE);
        } else {
            ((SnakeView)this.gameView).setCharacter(SnakeShape.SQUARE);
        }
    }

    /**
     * Set the color of the background of game view to background
     * @param background the color of the view's background
     */
    public void setBackground(int background) {
        ((SnakeView)this.gameView).setBackground(background);
    }

    @Override
    public void setTheme(String theme) {
        View view = findViewById(R.id.SnakeLayout);
        if (theme.equals("dark")) {
            this.setBackground(Color.DKGRAY);
            view.setBackgroundColor(Color.DKGRAY);
        } else {
            this.setBackground(Color.WHITE);
            view.setBackgroundColor(Color.WHITE);
        }
    }

    /**
     * Update the game to match the customization
     * @param level the difficulty of the game
     * @param theme the color theme of the game
     * @param character the character shape of the game
     */
    public void setCustomization(String level, String theme, String character) {
        setDifficulty(level);
        setTheme(theme);
        setCharacter(character);

    }
}