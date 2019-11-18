package com.example.game.gamecode.Snake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.NonNull;

import com.example.game.gamecode.GameObject;

/** A generic object in Snake */
abstract class SnakeObject extends GameObject {
  /** The x-coordinate of this snake object */
  public int x;

  /** The y-coordinate of this snake object */
  public int y;

  /** The size of this snake object */
  int size;

  /** The color of this snake object */
  private Paint paint = new Paint();

  /** The shape of this snake object */
  private SnakeShape shape;

  /** The type of this snake object */
  private SnakeObjectType type;

  /**
   * Constructs a new snake object.
   *
   * @param x the initial x coordinate of this snake object
   * @param y the initial y coordinate of this snake object
   * @param size the side length of this snake object
   * @param type the type of this snake object
   */
  SnakeObject(int x, int y, int size, SnakeShape shape, SnakeObjectType type) {
    this.x = x;
    this.y = y;
    this.size = size;
    this.paint.setColor(Color.WHITE);
    this.shape = shape;
    this.type = type;
  }

  /**
   * Draws this snake object.
   *
   * @param canvas the graphics context in which to draw this item.
   */
  public void draw(Canvas canvas) {
    switch (shape){
      case CIRCLE:
        float radiusAdjustment = ((float)this.size)/ 2;
        canvas.drawCircle(x * size + radiusAdjustment, y * size + radiusAdjustment, size / 2, paint);
        break;
      case SQUARE:
        canvas.drawRect(x * size, y * size, (x + 1) * size, (y + 1) * size, paint);
        break;
    }

  }

  /**
   * Set the color of this snake object.
   * @param color the color that the snake object will have
   */
  public void setColor(int color){
    this.paint.setColor(color);
  }

  /**
   * Return the color of this snake object
   * @return the color of this snake object
   */
  public int getColor(){
    return this.paint.getColor();
  }

    /**
     * Return the shape of this snake object
     * @return the shape of this snake object
     */
  SnakeShape getShape() {
    return shape;
  }

    /**
     * Set the shape of this snake object
     * @param shape the shape that this snake object will have.
     */
  void setShape(SnakeShape shape) {
    this.shape = shape;
  }

    /**
     * Return the string representation of this snake object
     * @return the type, coordinate, color, and shape separated by |.
     */
  @Override
  @NonNull
  public String toString(){
    return this.type + "|" + this.x + "|" + this.y + "|" + this.paint.getColor() +
            "|" + this.shape;
  }
}
