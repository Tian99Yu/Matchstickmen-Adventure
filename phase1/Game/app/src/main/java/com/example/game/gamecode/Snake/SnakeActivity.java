package com.example.game.gamecode.Snake;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.game.R;
import com.example.game.gamecode.GameActivity;
import com.example.game.gamecode.GameView;

public class SnakeActivity extends GameActivity {
  Button LeftButton;
  Button RightButton;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

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

  protected GameView setView() {
    return new SnakeView(this);
  }
}
