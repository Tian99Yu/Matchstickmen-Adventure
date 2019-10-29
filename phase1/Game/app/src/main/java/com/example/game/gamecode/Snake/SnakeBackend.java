package com.example.game.gamecode.Snake;

import android.graphics.Canvas;

import com.example.game.gamecode.GameBackend;
import com.example.game.gamecode.GameObject;

import java.util.ArrayList;

public class SnakeBackend extends GameBackend {
  private SnakeHead snakeHead;
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

  public ArrayList<GameObject> getGameObjects() {
    return gameObjects;
  }

  public void addSnakeObj(SnakeObject g) {
    this.gameObjects.add(g);
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
    gameObjects = new ArrayList<>();
    lost = false;
  }

  void draw(Canvas canvas) {
    for (int a = 0; a < gameObjects.size(); a++) {
      if (gameObjects.get(a) != null) {
        gameObjects.get(a).draw(canvas);
      }

      for (GameObject gameObject: gameObjects){
        if (gameObject instanceof Apple){
          Apple apple = (Apple) gameObject;
          if (snakeHead.atPosition(apple.x, apple.y)) {
            eatApple(apple);
            // code for increase length of snake
          }
        } else if (gameObject instanceof Wall || gameObject instanceof SnakeComponent) {
          SnakeObject snakeObject = (SnakeObject) gameObject;
          if (snakeHead.atPosition(snakeObject.x, snakeObject.y)) {
            snakeHead.setDead(true);
            setLost(true);
          }
        }
      }
    }
  }

  @Override
  public void update() {
    snakeHead.move();

  }

  public void turnSnake(TurnDirection turnDirection) {
    snakeHead.turn(turnDirection);
  }

  public void eatApple(Apple apple) {
    deleteItem(apple);
    this.apples += 1;
  }

  public void deleteItem(SnakeObject g) {
    gameObjects.remove(g);
  }

  void createObjects() {
    for (int x = 0; x < gridWidth; x++) {
      gameObjects.add(new Wall(x, 0, "-", 1));
      gameObjects.add(new Wall(x, gridHeight, "-", 1));
    }
    for (int y = 0; y < gridWidth; y++) {
      gameObjects.add(new Wall(0, y, "|", 1));
      gameObjects.add(new Wall(gridWidth, y, "|", 1));
    }
    gameObjects.add(
        new Apple(
            (int) (Math.random() * (gridWidth - 3)),
            (int) (Math.random() * (gridHeight - 3)),
            "@",
            1));
    gameObjects.add(
        new Apple(
            (int) (Math.random() * (gridWidth - 3)),
            (int) (Math.random() * (gridHeight - 3)),
            "@",
            1));
    gameObjects.add(
        new Apple(
            (int) (Math.random() * (gridWidth - 3)),
            (int) (Math.random() * (gridHeight - 3)),
            "@",
            1));

    snakeHead = new SnakeHead((int) gridWidth / 2, (int) gridHeight / 2, ":", 1);
    gameObjects.add(snakeHead);

    gameObjects.add(
        new SnakeComponent(
            (int) gridWidth / 2 + 1,
            (int) gridHeight / 2,
            "*",
            1));
    gameObjects.add(
        new SnakeComponent(
            (int) gridWidth / 2 + 2,
            (int) gridHeight / 2,
            "*",
            1));
  }
}
