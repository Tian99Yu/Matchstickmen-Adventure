package com.example.game.gamecode.Snake;

import android.graphics.Canvas;
import android.graphics.Color;

import com.example.game.gamecode.GameBackend;
import com.example.game.gamecode.GameObject;

import java.util.ArrayList;
import java.util.Random;

public class SnakeBackend extends GameBackend {
  private SnakeHead snakeHead;
  private boolean lost;

  /** The number of apples eaten. */
  private int apples;

  /** The distance that the snake traveled (in grids). */
  private int distance;

  /** The length of the snake. */
  private int snakeLength = 1;

  /** The size of each component */
  private int size;

  /** The width of Snake. */
  private int gridWidth;

  /** The height of Snake. */
  private int gridHeight;

  public ArrayList<GameObject> getGameObjects() {
    return gameObjects;
  }

  private void addSnakeObj(SnakeObject g) {
    this.gameObjects.add(g);
  }

  boolean isLost() {
    return lost;
  }

  private void setLost(boolean lost) {
    this.lost = lost;
  }

  public int getGridWidth() {
    return gridWidth;
  }

  public int getGridHeight() {
    return gridHeight;
  }

  SnakeBackend(int h, int w) {
    gameObjects = new ArrayList<>();
    lost = false;
    this.size = (int) Double.min(h / 64, w / 64);
    gridHeight = h / size;
    gridWidth = w / size;
    System.out.println(size);
    System.out.println(gridHeight);
  }

  void draw(Canvas canvas) {
    for (int a = 0; a < gameObjects.size(); a++) {
      if (gameObjects.get(a) != null) {
        gameObjects.get(a).draw(canvas);
      }
    }
  }

  public void updateHeadColor(int color){
    this.snakeHead.updateColor(color);
  }

  public void updateAppleColor(int color){
    for (GameObject gameObject: gameObjects){
      if (gameObject instanceof Apple){
        ((Apple) gameObject).updateColor(color);
      }
    }
  }

  public void updateWallColor(int color){
    for (GameObject gameObject: gameObjects){
      if (gameObject instanceof Wall){
        ((Wall) gameObject).updateColor(color);
      }
    }
  }

  public void updateBodyColor(int color){
    for (GameObject gameObject: gameObjects){
      if (gameObject instanceof SnakeComponent && !(gameObject instanceof SnakeHead)){
        ((SnakeComponent) gameObject).updateColor(color);
      }
    }
  }

  @Override
  public void update() {
    snakeHead.move();

    for (GameObject gameObject : gameObjects) {
      if (gameObject instanceof Apple) {
        Apple apple = (Apple) gameObject;
        if (snakeHead.atPosition(apple.x, apple.y)) {
          eatApple(apple);
          // code for increase length of snake
        }
      } else if (gameObject instanceof Wall || gameObject instanceof SnakeComponent) {
        SnakeObject snakeObject = (SnakeObject) gameObject;
        if (snakeHead.atPosition(snakeObject.x, snakeObject.y) && snakeHead != snakeObject) {
          snakeHead.setDead(true);
          setLost(true);
        }
      }
    }
    int length = gameObjects.size();
    for (int i = 0; i < length; i++) {
      if (gameObjects.get(i) instanceof Apple) {
        if (((Apple) gameObjects.get(i)).isEaten()) {
          deleteItem(gameObjects.get(i));
          i--;
          length--;
        }
      }
    }

    distance++;
  }

  void turnSnake(TurnDirection turnDirection) {
    snakeHead.turn(turnDirection);
  }

  private void eatApple(Apple apple) {
    apple.setIsEaten(true);
    this.apples += 1;
  }

  private void deleteItem(GameObject g) {
    gameObjects.remove(g);
  }

  private void addSnakeComponent() {
    SnakeComponent component = snakeHead.addComponent();
    addSnakeObj(component);
    snakeLength++;
  }

  void createObjects() {
    Random random = new Random();

    for (int x = 0; x < gridWidth; x++) {
      gameObjects.add(new Wall(x, 0, size));
      gameObjects.add(new Wall(x, gridHeight - 1, size));
    }
    for (int y = 0; y < gridHeight; y++) {
      gameObjects.add(new Wall(0, y, size));
      gameObjects.add(new Wall(gridWidth - 1, y, size));
    }
    gameObjects.add(
        new Apple(random.nextInt(gridHeight - 2) + 1, random.nextInt(gridHeight - 2) + 1, size));
    gameObjects.add(
        new Apple(random.nextInt(gridHeight - 2) + 1, random.nextInt(gridHeight - 2) + 1, size));
    gameObjects.add(
        new Apple(random.nextInt(gridHeight - 2) + 1, random.nextInt(gridHeight - 2) + 1, size));

    snakeHead = new SnakeHead(gridWidth / 2, gridHeight / 2, size);
    gameObjects.add(snakeHead);


    addSnakeComponent();
    addSnakeComponent();

    updateAppleColor(Color.RED);
    updateBodyColor(Color.GREEN);
    updateHeadColor(Color.YELLOW);
    updateWallColor(Color.YELLOW);
  }
}
