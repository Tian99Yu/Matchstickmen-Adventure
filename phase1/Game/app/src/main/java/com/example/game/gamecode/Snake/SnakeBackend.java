package com.example.game.gamecode.Snake;

import android.graphics.Canvas;

import com.example.game.gamecode.GameBackend;
import com.example.game.gamecode.GameObject;
import java.util.Random;

import java.util.ArrayList;

public class SnakeBackend extends GameBackend {

  /** Store snake objects. */
  ArrayList<SnakeObject> snakeObj;

  private boolean lost;

  /** The number of apples eaten. */
  int apples;

  /** The distance that the snake traveled (in grids). */
  int distance;

  /** Game duration (in seconds). */
  int time;

  /** The width of Snake. */
  private int gridWidth;

  /** The height of Snake. */
  private int gridHeight;

  public ArrayList<SnakeObject> getSnakeObj() {
    return snakeObj;
  }

  public void addSnakeObj(SnakeObject g) {
    this.snakeObj.add(g);
  }

  public boolean isLost() {
    return lost;
  }

  public void setLost(boolean lost) {
    this.lost = lost;
  }

  public int getGridWidth() {
    return gridWidth;
  }

  public int getGridHeight() {
    return gridHeight;
  }

  SnakeBackend(int h, int w) {
    gridHeight = h;
    gridWidth = w;
    snakeObj = new ArrayList<>();
    lost = false;
  }

  void draw(Canvas canvas) {
    for (int a = 0; a < snakeObj.size(); a++) {
      if (snakeObj.get(a) != null) {
        snakeObj.get(a).draw(canvas);
      }
    }
  }

  @Override
  public void update() {
    for (int a = 0; a < snakeObj.size(); a++) {
      if (snakeObj.get(a) instanceof SnakeComponent) {
        ((SnakeComponent) snakeObj.get(a)).move();
      }
    }
  }

  public void addApple() {
    this.apples += 1;
  }

  public void deleteItem(SnakeObject g) {
    snakeObj.remove(g);
  }

  void createObjects() {
    for (int x = 0; x < gridWidth; x++) {
      snakeObj.add(new Wall(x, 0, '-', 1));
      snakeObj.add(new Wall(x, gridHeight, '-', 1));
    }
    for (int y = 0; y < gridWidth; y++) {
      snakeObj.add(new Wall(0, y, '|', 1));
      snakeObj.add(new Wall(gridWidth, y, '|', 1));
    }
    snakeObj.add(
        new Apple(
            (int) (Math.random() * (gridWidth - 3)),
            (int) (Math.random() * (gridHeight - 3)),
            '@',
            1));
    snakeObj.add(
        new Apple(
            (int) (Math.random() * (gridWidth - 3)),
            (int) (Math.random() * (gridHeight - 3)),
            '@',
            1));
    snakeObj.add(
        new Apple(
            (int) (Math.random() * (gridWidth - 3)),
            (int) (Math.random() * (gridHeight - 3)),
            '@',
            1));
    snakeObj.add(new SnakeHead((int) gridWidth / 2, (int) gridHeight / 2, ':', 1));

    /** snakeObj.add(
        new SnakeComponent(
            (int) gridWidth / 2 + 1,
            (int) gridHeight / 2,
            '*',
            1)); // 这就很尴尬了，之后snakecomponent应该不会是abstract吧
    snakeObj.add(
        new SnakeComponent(
            (int) gridWidth / 2 + 2,
            (int) gridHeight / 2,
            '*',
            1)); // 这就很尴尬了，之后snakecomponent应该不会是abstract吧 */
  }
}
