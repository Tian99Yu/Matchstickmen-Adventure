package com.example.game.gamecode.Snake;

import android.graphics.Color;

import com.example.game.gamecode.GameBackend;
import com.example.game.gamecode.GameObject;
import com.example.game.gamecode.GamePresenter;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Presenter for a snake game
 *
 * @param <T> the surface that the snake game will be presented on
 */
public class SnakePresenter<T> extends GamePresenter<T> {
  /** The SnakeDrawer that handles drawing for the snake game. */
  private SnakeDrawer<T> snakeDrawer;

  /** Adjustment on height for centering the drawing surface. */
  private float heightAdjust;
  /** Adjustment on width for centering the drawing surface. */
  private float widthAdjust;
  /** The number of snake object that can fit on the shorter side of the drawing surface. */
  private int playSize = 64;
  /** A map corresponding the type of the snake object to the color of that type. */
  private HashMap<SnakeObjectType, Integer> snakeColor = new HashMap<>();

  /** Whether the snake backend is initialized */
  private boolean initialized = false;

  /**
   * Constructor for snake presenter
   *
   * @param snakeDrawer the surface to be drawn on
   * @param backend the game backend of the game to be presented.
   */
  SnakePresenter(SnakeDrawer<T> snakeDrawer, GameBackend backend) {
    super(backend);
    this.snakeDrawer = snakeDrawer;

    // Setting the corresponding color of each snake object type
    snakeColor.put(SnakeObjectType.SNAKE_COMPONENT, Color.GREEN);
    snakeColor.put(SnakeObjectType.APPLE, Color.RED);
    snakeColor.put(SnakeObjectType.SNAKE_HEAD, Color.YELLOW);
    snakeColor.put(SnakeObjectType.WALL, Color.YELLOW);
    snakeColor.put(null, Color.WHITE);
  }

  /** Update and refresh the game status. */
  void update() {
    if (initialized) {
      backend.update();
    } else {
      // Initialize the dimensions of the play space
      int height = snakeDrawer.getHeight();
      int width = snakeDrawer.getWidth();

      int gameObjectSize = Math.min(height / playSize, width / playSize);
      int gridHeight = height / gameObjectSize;
      int gridWidth = width / gameObjectSize;
      int gameHeight = gridHeight * gameObjectSize;
      int gameWidth = gridWidth * gameObjectSize;
      heightAdjust = (height - gameHeight) / 2.0f;
      widthAdjust = (width - gameWidth) / 2.0f;
      System.out.println(widthAdjust);
      // Initialize the snake backend
      SnakeBackend snakeBackend = (SnakeBackend) backend;
      snakeBackend.setGridHeight(gridHeight);
      snakeBackend.setGridWidth(gridWidth);
      snakeBackend.setSize(gameObjectSize);
      snakeBackend.createObjects();
      this.initialized = true;
    }
  }

  /**
   * Draw the snake object onto the drawing surface.
   *
   * @param snakeObject the snake object that is going to be drawn
   * @param drawingSurface the surface to be drawn in
   */
  private void drawSnakeObject(SnakeObject snakeObject, T drawingSurface) {
    SnakeShape shape = snakeObject.getShape();
    int size = snakeObject.size;
    int x = snakeObject.x;
    int y = snakeObject.y;

    Integer color = snakeColor.get(snakeObject.getType());
    if (color == null) {
      color = Color.WHITE;
    }
    switch (shape) {
      case CIRCLE:
        float radiusAdjustment = ((float) size) / 2;
        snakeDrawer.drawCircle(
            drawingSurface,
            x * size + radiusAdjustment + widthAdjust,
            y * size + radiusAdjustment + heightAdjust,
            size / 2,
            color);
        break;
      case SQUARE:
        snakeDrawer.drawRect(
            drawingSurface, x * size, y * size, (x + 1) * size, (y + 1) * size, color);
        break;
    }
  }

  /**
   * Draw the game onto the drawing surface.
   *
   * @param drawingSurface the surface to be drawn on.
   */
  @Override
  public void draw(T drawingSurface) {
    SnakeBackend snakeBackend = (SnakeBackend) this.backend;

    snakeDrawer.drawBackground(drawingSurface);

    Iterator<GameObject> gameObjectIterator = snakeBackend.getGameObjectsIterator();
    while (gameObjectIterator.hasNext()) {
      GameObject gameObject = gameObjectIterator.next();
      if (gameObject != null) {
        drawSnakeObject((SnakeObject) gameObject, drawingSurface);
      }
    }
  }

  /**
   * Set the color of the snake head
   *
   * @param color the color that the snake head will be in
   */
  private void setHeadColor(int color) {
    this.snakeColor.put(SnakeObjectType.SNAKE_HEAD, color);
  }

  /**
   * Set the color of the apples in the game
   *
   * @param color the color that the apples will be in
   */
  private void setAppleColor(int color) {
    this.snakeColor.put(SnakeObjectType.APPLE, color);
  }

  /**
   * Set the color of all the walls in the game
   *
   * @param color the color that the walls will be in
   */
  private void setWallColor(int color) {
    this.snakeColor.put(SnakeObjectType.WALL, color);
  }

  /**
   * Set the color of the body of the snake
   *
   * @param color the color that the body of the snake will be in
   */
  private void setBodyColor(int color) {
    this.snakeColor.put(SnakeObjectType.SNAKE_COMPONENT, color);
  }
}
