package com.example.game.gamecode.Snake;

import android.graphics.Color;

import com.example.game.gamecode.GameBackend;
import com.example.game.gamecode.GameObject;
import com.example.game.gamecode.GamePresenter;

import java.util.HashMap;
import java.util.Iterator;

public class SnakePresenter<T> extends GamePresenter<T> {
  /** The SnakeDrawer that handles drawing for the snake game. */
  private SnakeDrawer<T> snakeDrawer;

  private float heightAdjust;
  private float widthAdjust;
  private int playSize = 64;
  private HashMap<SnakeObjectType, Integer> snakeColor = new HashMap<>();

  /** Whether the snake backend is initialized*/
  private boolean initialized = false;

  SnakePresenter(SnakeDrawer<T> snakeDrawer, GameBackend backend) {
    super(backend);
    this.snakeDrawer = snakeDrawer;

    snakeColor.put(SnakeObjectType.SNAKE_COMPONENT, Color.GREEN);
    snakeColor.put(SnakeObjectType.APPLE, Color.RED);
    snakeColor.put(SnakeObjectType.SNAKE_HEAD, Color.YELLOW);
    snakeColor.put(SnakeObjectType.WALL, Color.YELLOW);
    snakeColor.put(null, Color.WHITE);
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

      int gameObjectSize = Math.min(height/playSize, width/playSize);
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
        snakeDrawer.drawCircle(drawingSurface, x * size + radiusAdjustment + widthAdjust,
                y * size + radiusAdjustment + heightAdjust, size / 2, color);
        break;
      case SQUARE:
        snakeDrawer.drawRect(drawingSurface, x * size, y * size, (x + 1) * size,
                (y + 1) * size, color);
        break;
    }
  }

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
}
