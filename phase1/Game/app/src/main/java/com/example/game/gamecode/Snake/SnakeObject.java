package com.example.game.gamecode.Snake;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.game.gamecode.GameObject;

/** A generic object in Snake */
abstract class SnakeObject extends GameObject {
  /** The x-coordinate of this snake object */
  public int x;

  /** The y-coordinate of this snake object */
  public int y;

  /** The appearance of this snake object */
  public Object appearance; // The implementation of appearance is not decided yet.

  /** The size of this snake object */
  public int size;

  /** The color of this snake object */
  public Paint paint = new Paint();

  /**
   * Constructs a new snake object.
   *
   * @param x the initial x coordinate of this snake object
   * @param y the initial y coordinate of this snake object
   * @param appearance the appearance of this snake object
   * @param size the side length of this snake object
   */
  SnakeObject(int x, int y, int appearance, int size) {
    this.x = x;
    this.y = y;
    this.appearance = appearance;
    this.size = size;
  }

  /**
   * Draws this snake object.
   *
   * @param canvas the graphics context in which to draw this item.
   */
  public void draw(Canvas canvas) {
    canvas.drawRect(x * size, y * size, (x+1) * this.size, (y-1) * this.size, paint);
  }
}
