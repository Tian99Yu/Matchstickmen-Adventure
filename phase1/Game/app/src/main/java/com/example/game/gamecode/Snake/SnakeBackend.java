package com.example.game.gamecode.Snake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.NonNull;

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

  /** The shape of its snake objects. */
  private SnakeShape shape = SnakeShape.CIRCLE;

  /** The color of the background */
  private int canvasColor = Color.BLACK;

    /**
     * Return an array list of game objects that this snake backend controls
     * @return an array of game object that this snake backend controls
     */
  public ArrayList<GameObject> getGameObjects() {
    return gameObjects;
  }

    /**
     * Add the snake object to game objects
     * @param snakeObject the snake object to be added.
     */
  private void addSnakeObj(SnakeObject snakeObject) {
    this.gameObjects.add(snakeObject);
  }

    /**
     * Set if this game is still running or is lost
     * @param lost true if the game is lost and ended, false otherwise.
     */
  private void setLost(boolean lost) {
    this.lost = lost;
  }

    /**
     * Constructor for snake backend
     * @param height the height of this game
     * @param width the width of this game
     */
  SnakeBackend(int height, int width) {
    gameObjects = new ArrayList<>();
    lost = false;
    this.size = (int) Double.min(height / 64, width / 64);
    gridHeight = height / size;
    gridWidth = width / size;
  }

    /**
     * Draw the current status of this game on the canvas
     * @param canvas the canvas to draw this game on.
     */
  @Override
  public void draw(Canvas canvas) {
    drawBackground(canvas);
    for (int a = 0; a < gameObjects.size(); a++) {
      if (gameObjects.get(a) != null) {
        gameObjects.get(a).draw(canvas);
      }
    }
  }

    /**
     * Set the color of the snake head
     * @param color the color that the snake head will be in
     */
  private void setHeadColor(int color) {
    this.snakeHead.setColor(color);
  }

    /**
     * Set the color of the apples in the game
     * @param color the color that the apples will be in
     */
  private void setAppleColor(int color) {
    for (GameObject gameObject : gameObjects) {
      if (gameObject instanceof Apple) {
        ((Apple) gameObject).setColor(color);
      }
    }
  }

    /**
     * Set the color of all the walls in the game
     * @param color the color that the walls will be in
     */
  private void setWallColor(int color) {
    for (GameObject gameObject : gameObjects) {
      if (gameObject instanceof Wall) {
        ((Wall) gameObject).setColor(color);
      }
    }
  }

    /**
     * Set the color of the body of the snake
     * @param color the color that the body of the snake will be in
     */
  private void setBodyColor(int color) {
    for (GameObject gameObject : gameObjects) {
      if (gameObject instanceof SnakeComponent && !(gameObject instanceof SnakeHead)) {
        ((SnakeComponent) gameObject).setColor(color);
      }
    }
  }

    /**
     * Update and refresh the game status.
     */
  @Override
  public void update() {
    snakeHead.move();
    boolean eatApple = false;

    for (GameObject gameObject : gameObjects) {
      if (gameObject instanceof Apple) {
        Apple apple = (Apple) gameObject;
        if (snakeHead.atPosition(apple.x, apple.y)) {
          eatApple(apple);
          eatApple = true;
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

    if (eatApple) {
      addSnakeComponent();
    }

    Random random = new Random();
    int randomInt = random.nextInt(100);
    if (randomInt == 50){
      Apple apple = new Apple(random.nextInt(gridHeight - 2) + 1,
              random.nextInt(gridHeight - 2) + 1, size, shape);
      addSnakeObj(apple);
      apple.setColor(Color.RED);
    }

    randomInt = random.nextInt(500);
    if (randomInt == 100) {
      addSnakeComponent();
    }

    distance++;
  }

    /**
     * Turn the snake in turn direction
     * @param turnDirection The direction for this turn
     */
  void turnSnake(TurnDirection turnDirection) {
    snakeHead.turn(turnDirection);
  }

    /**
     * Mark that the apple is eaten and add one to the number of apple eaten
     * @param apple the apple that is eaten
     */
  private void eatApple(Apple apple) {
    apple.setIsEaten(true);
    this.apples += 1;
  }

    /**
     * Remove the item from game objects
     * @param gameObject the game object to be removed
     */
  private void deleteItem(GameObject gameObject) {
    gameObjects.remove(gameObject);
  }

    /**
     * Add one body component to the snake
     */
  private void addSnakeComponent() {
    SnakeComponent component = snakeHead.addComponent();
    addSnakeObj(component);
    component.setColor(Color.GREEN);
    snakeLength++;
  }

    /**
     * Initialize and create all the objects when stating the game
     */
  void createObjects() {
    Random random = new Random();

    for (int x = 0; x < gridWidth; x++) {
      gameObjects.add(new Wall(x, 0, size, shape));
      gameObjects.add(new Wall(x, gridHeight - 1, size, shape));
    }
    for (int y = 0; y < gridHeight; y++) {
      gameObjects.add(new Wall(0, y, size, shape));
      gameObjects.add(new Wall(gridWidth - 1, y, size, shape));
    }
    addSnakeObj(new Apple(random.nextInt(gridHeight - 2) + 1,
            random.nextInt(gridHeight - 2) + 1, size, shape));
    addSnakeObj(new Apple(random.nextInt(gridHeight - 2) + 1,
            random.nextInt(gridHeight - 2) + 1, size, shape));
    addSnakeObj(new Apple(random.nextInt(gridHeight - 2) + 1,
            random.nextInt(gridHeight - 2) + 1, size, shape));

    snakeHead = new SnakeHead(gridWidth / 2, gridHeight / 2, size, shape);
    gameObjects.add(snakeHead);

    addSnakeComponent();
    addSnakeComponent();

    setAppleColor(Color.RED);
    setBodyColor(Color.GREEN);
    setHeadColor(Color.YELLOW);
    setWallColor(Color.YELLOW);
  }

    /**
     * Set the shape of the characters in the game
     * @param shape the shape that the characters will take
     */
  public void setShape(SnakeShape shape){
      this.shape = shape;
      for (GameObject gameObject: gameObjects) {
          ((SnakeObject) gameObject).setShape(shape);
      }
  }

    /**
     * Draw the background of this game on canvas.
     * @param canvas the canvas that the game in running on.
     */
  public void drawBackground(Canvas canvas){
    Paint paint = new Paint();
    paint.setColor(canvasColor);
    paint.setStyle(Paint.Style.FILL);
    canvas.drawPaint(paint);
  }

    /**
     * Set the background color
     * @param color the background color this game will have
     */
  public void setCanvasColor(int color) {
    canvasColor = color;
  }

    /**
     * Return whether the game is over
     * @return true if the game is over, false otherwise.
     */
  @Override
  public boolean isGameOver() {
    return lost;
  }

    /**
     * Return the length of the snake
     * @return the length of the snake
     */
  @Override
  public int getCurrentScore() {
    return this.snakeLength;
  }

    /**
     * Get the statistics recorded in the game
     * @return the statistics recorded in the game
     */
  String[][] getStatistics(){
    String[][]  statistic = new String[2][3];
    statistic[0][0] = "Length";
    statistic[0][1] = "Apples";
    statistic[0][2] = "Distance";
    statistic[1][0] = ((Integer)this.snakeLength).toString();
    statistic[1][1] = ((Integer)this.apples).toString();
    statistic[1][2] = ((Integer)this.distance).toString();
    return statistic;
  }

    /**
     * Return a string representation of this snake backend
     * @return a string that contain all the information needed to reconstruct the backend
     */
  @Override
  @NonNull
  public String toString(){
      StringBuilder string = new StringBuilder();
      for (GameObject gameObject: gameObjects){
          if (gameObject instanceof SnakeObject && !(gameObject instanceof Wall)){
              SnakeObject snakeObject = (SnakeObject) gameObject;
              string.append(snakeObject.toString() + ",");
          }
      }
      return string.toString();
  }
}
