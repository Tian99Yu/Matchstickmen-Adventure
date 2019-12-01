package com.group0540.matchstickmenadventures.gamecode.snake;

import android.graphics.Color;

import com.group0540.matchstickmenadventures.gamecode.GameBackend;
import com.group0540.matchstickmenadventures.gamecode.GamePresenter;

import java.util.HashMap;

/**
 * Presenter for a snake game
 *
 * @param <T> the surface that the snake game will be presented on
 */
public class SnakePresenter<T> extends GamePresenter<T, SnakeObject> {
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
   * The customizations of this snake game
   */
  private SnakeShape shape;

  /**
   * Constructor for snake presenter
   *
   * @param snakeDrawer the surface to be drawn on
   * @param backend the game backend of the game to be presented.
   */
  SnakePresenter(SnakeDrawer<T> snakeDrawer, GameBackend<SnakeObject> backend) {
    super(backend);
    this.snakeDrawer = snakeDrawer;

    // Setting the corresponding color of each snake object type
    snakeColor.put(SnakeObjectType.SNAKE_COMPONENT, Color.GREEN);
    snakeColor.put(SnakeObjectType.APPLE, Color.RED);
    snakeColor.put(SnakeObjectType.SNAKE_HEAD, Color.YELLOW);
    snakeColor.put(SnakeObjectType.WALL, Color.YELLOW);
    snakeColor.put(SnakeObjectType.MYSTERY_OBJECT, Color.BLACK);
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
            drawingSurface, x * size + widthAdjust, y * size + heightAdjust,
                (x + 1) * size + widthAdjust, (y + 1) * size + heightAdjust, color);
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

    snakeDrawer.drawBackground(drawingSurface);

    for (SnakeObject snakeObject: backend) {
        if (snakeObject != null) {
          drawSnakeObject(snakeObject, drawingSurface);
        }
      }
  }

  /**
   * Set the customization of the game to snake customization
   * @param snakeShape the customization object for this game.
   */
  void setCharacter(SnakeShape snakeShape) {
    this.shape = snakeShape;
  }
}
