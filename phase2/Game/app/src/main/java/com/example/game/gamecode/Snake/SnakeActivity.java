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
import com.example.game.settingscode.CustomizableGame;
import com.example.game.settingscode.SettingsManager;

/** Activity for snake game. */
public class SnakeActivity extends GameActivity implements Saver, CustomizableGame {
  /** The button responsible for turning left */
  Button leftButton;
  /** the button responsible for turning right */
  Button rightButton;


  /**
   * Initialize this activity
   *
   * @param savedInstanceState data used to restore activities to previous state
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    username = (String) getIntent().getSerializableExtra("username");
    settingsManager = (SettingsManager) getIntent().getSerializableExtra("settingsManager");
    leaderboardManager =
        (LeaderboardManager) getIntent().getSerializableExtra("leaderboardManager");

    setContentView(R.layout.snake_layout);

    leftButton = findViewById(R.id.leftButton);
    rightButton = findViewById(R.id.rightButton);

    // add the gameview as a second layout:
    gameView = this.setView();
    FrameLayout frameLayout = findViewById(R.id.snakeLayout);
    frameLayout.addView(gameView);

    setCustomization(
        settingsManager.getSetting("difficulty"),
        settingsManager.getSetting("theme"),
        settingsManager.getSetting("character"));

    View.OnClickListener leftListener =
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            ((SnakeBackend) gameView.gameBackend).turnSnake(TurnDirection.LEFT);
          }
        };

    leftButton.setOnClickListener(leftListener);

    View.OnClickListener rightListener =
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            ((SnakeBackend) gameView.gameBackend).turnSnake(TurnDirection.RIGHT);
          }
        };

    rightButton.setOnClickListener(rightListener);
  }

  /** Pause this activity. */
  @Override
  protected void onPause() {
    super.onPause();
  }

  /** Resume this activity. */
  @Override
  protected void onResume() {
    super.onResume();
  }

  /** Save the score of this game to leader board manager. */
  @Override
  public void saveScore() {
    String[][] statistic = ((SnakeView) gameView).getStatistics();
    this.leaderboardManager.saveData(Games.SNAKE, this.username, statistic[0], statistic[1]);
  }

  /**
   * Create and return a new view from this activity
   *
   * @return the crated view
   */
  protected GameView setView() {
    return new SnakeView(this);
  }

  /**
   * Update the difficulty of this game to difficulty
   *
   * @param difficulty the difficulty of this game, can be easy, medium, or hard.
   */
  @Override
  public void setDifficulty(String difficulty) {
    SnakeView snakeView = (SnakeView) this.gameView;
    if (difficulty.equals("easy")) {
      snakeView.setDifficulty(3);
    } else if (difficulty.equals("medium")) {
      snakeView.setDifficulty(5);
    } else {
      snakeView.setDifficulty(10);
    }
  }

  /**
   * Set the shape of the character of this game
   *
   * @param character the character of this game, can be one or two
   */
  @Override
  public void setCharacter(String character) {
    if (character.equals("one")) {
      ((SnakeView) this.gameView).setCharacter(SnakeShape.CIRCLE);
    } else {
      ((SnakeView) this.gameView).setCharacter(SnakeShape.SQUARE);
    }
  }

  /**
   * Set the color of the background of game view to background
   *
   * @param background the color of the view's background
   */
  public void setBackground(int background) {
    ((SnakeView) this.gameView).setBackground(background);
  }

  /**
   * Set the theme of this game.
   *
   * @param theme the theme of this game.
   */
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
   *
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
