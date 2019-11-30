package com.group0540.matchstickmenadventures.gamecode.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.group0540.matchstickmenadventures.gamecode.GameThread;
import com.group0540.matchstickmenadventures.gamecode.GameView;
import com.group0540.matchstickmenadventures.leaderboardcode.Saver;

public class SnakeView extends GameView<SnakeObject> implements SnakeDrawer<Canvas> {
  /**
   * The customizations of this snake game
   */
  SnakeCustomization snakeCustomization;

  /**
   * Constructor for snake view.
   *
   * @param context the context that this snake view is created in.
   */
  public SnakeView(Context context) {
    super(context);
    thread = new GameThread(getHolder(), this, (Saver) context);
    thread.setUpdateInterval(100);

    gameBackend = new SnakeBackend();
    setPresenter(new SnakePresenter<>(this, this.gameBackend));
  }

  /** Update this view. */
  @Override
  public void update() {
    ((SnakePresenter) getPresenter()).update();
  }

  /**
   * Return the statistics of the game that is running on this view.
   *
   * @return the statistics including distance traveled, apples eaten, and snake length.
   */
  public String[][] getStatistics() {
    return ((SnakeBackend) gameBackend).getStatistics();
  }

  /**
   * Return a string representation of the game running on this view
   *
   * @return the string representation of the game running on this view.
   */
  public String getValue() {
    return gameBackend.toString();
  }

  /**
   * Set the difficulty to difficulty
   *
   * @param difficulty the difficulty level of the game
   */
  public void setDifficulty(int difficulty) {
    this.thread.setUpdateInterval(Math.max(500 - (difficulty * 50), 30));
  }

  /**
   * Set the representation of the characters.
   *
   * @param shape the representation of the characters.
   */
  public void setCharacter(SnakeShape shape) {
    ((SnakeBackend) this.gameBackend).setShape(shape);
    // TODO Set the shape through presenter rather than backend.
  }

  /**
   * Draw the background of this game on canvas.
   *
   * @param drawingSurface the canvas that the game in running on.
   */
  public void drawBackground(Canvas drawingSurface) {
    Paint paint = new Paint();
    paint.setColor(this.snakeCustomization.getTheme());
    paint.setStyle(Paint.Style.FILL);
    drawingSurface.drawPaint(paint);
  }

  /**
   * Draw a rectangle on the drawing surface a rectangle determined by left, top, right, and bottom.
   *
   * @param drawingSurface the surface to be drawn in
   * @param left the x coordinate of the left side of the rectangle
   * @param top the y coordinate of the top side of the rectangle
   * @param right the x coordinate of the right side of the rectangle
   * @param bottom the y coordinate of the bottom side of the rectangle
   * @param color the color of the rectangle
   */
  @Override
  public void drawRect(
      Canvas drawingSurface, float left, float top, float right, float bottom, int color) {
    Paint paint = new Paint();
    paint.setColor(color);
    drawingSurface.drawRect(left, top, right, bottom, paint);
  }

  /**
   * Draw a circle on the drawing surface a circle determined by x, y and radius.
   *
   * @param drawingSurface the surface to be drawn in
   * @param x the x coordinate of the center of the circle
   * @param y the y coordinate of the center of the circle
   * @param radius the radius of the circle
   * @param color the color of the circle
   */
  public void drawCircle(Canvas drawingSurface, float x, float y, float radius, int color) {
    Paint paint = new Paint();
    paint.setColor(color);
    drawingSurface.drawCircle(x, y, radius, paint);
  }

  /**
   * Set the customization of the game to snake customization
   * @param snakeCustomization the customization object for this game.
   */
  public void setSnakeCustomization(SnakeCustomization snakeCustomization) {
    this.snakeCustomization = snakeCustomization;
    this.setDifficulty(snakeCustomization.getDifficulty());
    ((SnakePresenter)getPresenter()).setCharacter(snakeCustomization.getCharacter());
  }
}
