package com.example.game.gamecode.Snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.FrameLayout;

import com.example.game.R;
import com.example.game.gamecode.GameObject;
import com.example.game.gamecode.GameThread;
import com.example.game.gamecode.GameView;
import com.example.game.leaderboardcode.Saver;

import java.util.ArrayList;
import java.util.Iterator;

public class SnakeView extends GameView implements SnakeDrawer<Canvas> {
  private int backgroundColor = Color.WHITE;
  private int playSize = 64;

  public SnakeView(Context context) {
    super(context);
    thread = new GameThread(getHolder(), this, (Saver) context);
    thread.setUpdateInterval(100);

    gameBackend = new SnakeBackend(1650, 960);
    setPresenter(new SnakePresenter<>(this, this.gameBackend));

    ((SnakeBackend) gameBackend).createObjects();
  }

  @Override
  public void update() {
    super.update();
  }

  public String[][] getStatistics() {
    return ((SnakeBackend) gameBackend).getStatistics();
  }

  public String getValue() {
    return gameBackend.toString();
  }

  public void setDifficulty(int difficulty) {
    this.thread.setUpdateInterval(Math.max(500 - (difficulty * 50), 30));
  }

  public void setCharacter(SnakeShape shape) {
    ((SnakeBackend) this.gameBackend).setShape(shape);
  }

  public void setBackground(int background) {
    this.backgroundColor = background;
  }

  /**
   * Draw the background of this game on canvas.
   *
   * @param canvas the canvas that the game in running on.
   */
  public void drawBackground(Canvas canvas) {
    Paint paint = new Paint();
    paint.setColor(backgroundColor);
    paint.setStyle(Paint.Style.FILL);
    canvas.drawPaint(paint);
  }

  @Override
  public void drawRect(
      Canvas drawingSurface, float left, float top, float right, float bottom, int color) {
    Paint paint = new Paint();
    paint.setColor(color);
    drawingSurface.drawRect(left, top, right, bottom, paint);
  }

  public void drawSnakeObject(SnakeObject snakeObject, Canvas canvas) {
    SnakeShape shape = snakeObject.getShape();
    int size = snakeObject.size;
    int x = snakeObject.x;
    int y = snakeObject.y;
    Paint paint = snakeObject.getPaint();
    switch (shape) {
      case CIRCLE:
        float radiusAdjustment = ((float) size) / 2;
        canvas.drawCircle(
            x * size + radiusAdjustment, y * size + radiusAdjustment, size / 2, paint);
        break;
      case SQUARE:
        canvas.drawRect(x * size, y * size, (x + 1) * size, (y + 1) * size, paint);
        break;
    }
  }

  /**
   * Draw the current status of this game on the canvas
   *
   * @param drawingSurface the canvas to draw this game on.
   */
  @Override
  public void drawUpdate(Canvas drawingSurface) {
    drawBackground(drawingSurface);
    Iterator<GameObject> gameObjectIterator = gameBackend.getGameObjectsIterator();
    while (gameObjectIterator.hasNext()) {
      GameObject gameObject = gameObjectIterator.next();
      if (gameObject != null) {
        drawSnakeObject((SnakeObject) gameObject, drawingSurface);
      }
    }
  }
}
