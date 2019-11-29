package com.example.game.gamecode.Snake;

import androidx.annotation.NonNull;

import com.example.game.gamecode.GameBackend;

import java.util.ArrayList;
import java.util.Random;

/** A back end that is responsible for managing all of the snake objects. */
public class SnakeBackend extends GameBackend<SnakeObject> {
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

  private ArrayList<MysteryObject> mysteryObjects;


  /** Create an uninitialized empty snake backend */
  SnakeBackend() {
    gameObjects = new ArrayList<>();
    lost = false;
    mysteryObjects = new ArrayList<>();
  }

  /**
   * Return an array list of game objects that this snake backend controls
   *
   * @return an array of game object that this snake backend controls
   */
  public ArrayList<SnakeObject> getGameObjects() {
    return gameObjects;
  }

  /**
   * Add the snake object to game objects
   *
   * @param snakeObject the snake object to be added.
   */
  private void addSnakeObj(SnakeObject snakeObject) {
    this.gameObjects.add(snakeObject);
  }

  /**
   * Set if this game is still running or is lost
   *
   * @param lost true if the game is lost and ended, false otherwise.
   */
  private void setLost(boolean lost) {
    this.lost = lost;
  }

  /**
   * Constructor for snake backend
   *
   * @param height the height of this game
   * @param width the width of this game
   */
  SnakeBackend(int height, int width) {
    gameObjects = new ArrayList<>();
    mysteryObjects = new ArrayList<>();
    lost = false;
    this.size = Math.min(height / 64, width / 64);
    gridHeight = height / size;
    gridWidth = width / size;
  }

  /** Update and refresh the game status. */
  @Override
  public void update() {
    boolean eatApple = false;
    boolean eatMysteryObject = false;

    snakeHead.move();
    for (MysteryObject mysteryObject: mysteryObjects){
      int x = mysteryObject.x;
      int y = mysteryObject.y;
      if (! snakeHead.atPosition(mysteryObject.x, mysteryObject.y)) {
      mysteryObject.move();
      } else {
        eatMysteryObject(mysteryObject);
        eatMysteryObject = true;
      }
    }

    for (SnakeObject gameObject : gameObjects) {
      if (gameObject instanceof Apple) {
        Apple apple = (Apple) gameObject;
        if (snakeHead.atPosition(apple.x, apple.y)) {
          eatApple(apple);
          eatApple = true;
        }
      } else if (gameObject instanceof Wall || gameObject instanceof SnakeComponent) {
        if (snakeHead.atPosition(gameObject.x, gameObject.y) && snakeHead != gameObject) {
          snakeHead.setDead();
          setLost(true);
        }
      }
    }

    int length = gameObjects.size();
    for (int i = 0; i < length; i++) {
      boolean eaten = false;
      if (gameObjects.get(i) instanceof Edible) {
        if (((Edible) gameObjects.get(i)).isEaten()) {
          eaten = true;
        }
      }
      if (eaten) {
        deleteItem(gameObjects.get(i));
        i--;
        length--;
      }
    }

    if (eatApple) {
      addSnakeComponent();
    }

    if (eatMysteryObject) {
      Random random = new Random();
      int bonus = random.nextInt(10);
      for (int i = 0; i < bonus; i++) {
        addSnakeComponent();
      }
    }

    Random random = new Random();
    int randomInt = random.nextInt(100);
    if (randomInt == 50) {
      Apple apple =
          new Apple(
              random.nextInt(gridHeight - 2) + 1, random.nextInt(gridHeight - 2) + 1, size, shape);
      addSnakeObj(apple);
    }

    randomInt = random.nextInt(500);
    if (randomInt == 100) {
      addSnakeComponent();
    }

    randomInt = random.nextInt(1000);
    if (randomInt == 1) {
      MysteryObject mysteryObject =
          new MysteryObject(
              random.nextInt(gridHeight - 2) + 1, random.nextInt(gridHeight - 2) + 1, size, shape);
      addSnakeObj(mysteryObject);
      mysteryObjects.add(mysteryObject);
    }

    distance++;
  }

  /**
   * Turn the snake in turn direction
   *
   * @param turnDirection The direction for this turn
   */
  void turnSnake(TurnDirection turnDirection) {
    snakeHead.turn(turnDirection);
  }

  /**
   * Mark that the apple is eaten and add one to the number of apple eaten
   *
   * @param apple the apple that is eaten
   */
  private void eatApple(Apple apple) {
    apple.setEaten();
    this.apples += 1;
  }

  /**
   * Mark that the mystery object is eaten and give bonus points
   *
   * @param mysteryObject the apple that is eaten
   */
  private void eatMysteryObject(MysteryObject mysteryObject) {
    mysteryObject.setEaten();
  }

  /**
   * Remove the item from game objects
   *
   * @param gameObject the game object to be removed
   */
  private void deleteItem(SnakeObject gameObject) {
    gameObjects.remove(gameObject);
  }

  /** Add one body component to the snake */
  private void addSnakeComponent() {
    SnakeComponent component = snakeHead.addComponent();
    addSnakeObj(component);
    snakeLength++;
  }

  /** Initialize and create all the objects when stating the game */
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

    snakeHead = new SnakeHead(gridWidth / 2, gridHeight / 2, size, shape);
    gameObjects.add(snakeHead);

    addSnakeComponent();
    addSnakeComponent();
  }

  /**
   * Set the shape of the characters in the game
   *
   * @param shape the shape that the characters will take
   */
  void setShape(SnakeShape shape) {
    this.shape = shape;
    for (SnakeObject gameObject : gameObjects) {
      gameObject.setShape(shape);
    }
  }

  /**
   * Return whether the game is over
   *
   * @return true if the game is over, false otherwise.
   */
  @Override
  public boolean isGameOver() {
    return lost;
  }

  /**
   * Return the length of the snake
   *
   * @return the length of the snake
   */
  @Override
  public int getCurrentScore() {
    return this.snakeLength;
  }

  /**
   * Get the statistics recorded in the game
   *
   * @return the statistics recorded in the game
   */
  String[][] getStatistics() {
    String[][] statistic = new String[2][3];
    statistic[0][0] = "Length";
    statistic[0][1] = "Apples";
    statistic[0][2] = "Distance";
    statistic[1][0] = ((Integer) this.snakeLength).toString();
    statistic[1][1] = ((Integer) this.apples).toString();
    statistic[1][2] = ((Integer) this.distance).toString();
    return statistic;
  }

  /**
   * Return a string representation of this snake backend
   *
   * @return a string that contain all the information needed to reconstruct the backend
   */
  @Override
  @NonNull
  public String toString() {
    StringBuilder string = new StringBuilder();
    for (SnakeObject gameObject : gameObjects) {
      if (!(gameObject instanceof Wall)) {
        SnakeObject snakeObject = gameObject;
        string.append(snakeObject.toString());
        string.append(",");
      }
    }
    return string.toString();
  }

  /**
   * Set the size of each snake object to size.
   *
   * @param size the size of each snake object.
   */
  public void setSize(int size) {
    this.size = size;
  }

  /**
   * Set the width of the game grid to gridWidth
   *
   * @param gridWidth the width of the game grid
   */
  void setGridWidth(int gridWidth) {
    this.gridWidth = gridWidth;
  }

  /**
   * Set the height of game grid to gridHeight
   *
   * @param gridHeight the height of the game grid
   */
  void setGridHeight(int gridHeight) {
    this.gridHeight = gridHeight;
  }
}
