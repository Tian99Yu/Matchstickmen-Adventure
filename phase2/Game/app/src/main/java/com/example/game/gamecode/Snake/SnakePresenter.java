package com.example.game.gamecode.Snake;

import android.graphics.Color;

import com.example.game.gamecode.GameBackend;
import com.example.game.gamecode.GamePresenter;

public class SnakePresenter<T> extends GamePresenter<T> {
  /** The SnakeDrawer that handles drawing for the snake game. */
  private SnakeDrawer<T> snakeDrawer;

  SnakePresenter(SnakeDrawer<T> snakeDrawer, GameBackend backend) {
    super(backend);
    this.snakeDrawer = snakeDrawer;
  }

  @Override
  public void draw(T drawingSurface) {
    //example usage
    snakeDrawer.drawUpdate(drawingSurface);
  }
}
