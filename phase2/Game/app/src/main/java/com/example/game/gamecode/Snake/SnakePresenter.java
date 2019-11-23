package com.example.game.gamecode.Snake;

import android.graphics.Color;

import com.example.game.gamecode.GameBackend;
import com.example.game.gamecode.GamePresenter;

public class SnakePresenter<T> extends GamePresenter<T> {
  /** The SnakeDrawer that handles drawing for the snake game. */
  private SnakeDrawer<T> snakeDrawer;

  private int gameObjectSize;
  private int gameHeight;
  private int gameWidth;
  private int heightAdjust;
  private int widthAdjust;

  /** Whether the snake backend is initialized*/
  private boolean initialized = false;

  SnakePresenter(SnakeDrawer<T> snakeDrawer, GameBackend backend) {
    super(backend);
    this.snakeDrawer = snakeDrawer;
  }

  /**
   * Update and refresh the game status.
   */
  void update(){
    if (initialized) {
      backend.update();
    } else {
      // Initialize the dimensions of the play space
      int height = snakeDrawer.getHeight();
      int width = snakeDrawer.getWidth();
      gameObjectSize = Math.min(height/64, width/64);
      int gridHeight = height / gameObjectSize;
      int gridWidth = width / gameObjectSize;
      gameHeight = gridHeight * gameObjectSize;
      gameWidth = gridWidth * gameObjectSize;
      heightAdjust = (height - gameHeight) / 2;
      widthAdjust = (width - gameWidth) / 2;
      // Initialize the snake backend
      SnakeBackend snakeBackend = (SnakeBackend) backend;
      snakeBackend.setGridHeight(gridHeight);
      snakeBackend.setGridWidth(gridWidth);
      snakeBackend.setSize(gameObjectSize);
      snakeBackend.createObjects();
      this.initialized = true;
    }
  }

  @Override
  public void draw(T drawingSurface) {
    //example usage
    snakeDrawer.drawUpdate(drawingSurface);
  }
}
