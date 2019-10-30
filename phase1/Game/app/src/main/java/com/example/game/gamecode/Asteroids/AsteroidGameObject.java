package com.example.game.gamecode.Asteroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.example.game.gamecode.GameObject;

abstract class AsteroidGameObject extends GameObject {
  /** time interval to approximate movement */
  static final double dt = 1.0 / 60.0;
  /** position of AsteroidGameObject */
  double x, y;
  /** velocity in the x and y direction of the AsteroidGameObject */
  double vX, vY;
  /**
   * angle AsteroidGameObject is pointing with respect to horizontal with 0 being east rotating
   * clockwise restricted to be between 0 inclusive and 2 pi exclusive
   */
  double angle;
  /** hitbox of AsteroidGameObject assuming perfect ball */
  double collisionRadius;

  AsteroidGameObject(
      double x, double y, double vX, double vY, double angle, double collisionRadius) {
    this.x = x;
    this.y = y;
    this.vX = vX;
    this.vY = vY;
    this.angle = angle;
    this.collisionRadius = collisionRadius;
  }

  /**
   * Returns true iff this object and other object are within collision distance of each other.
   *
   * @param other object with which we are checking for collision with.
   * @return whether or not this has collided with other.
   */
  boolean isColliding(AsteroidGameObject other) {
    if (other != null) {
      double dx = (x - other.x);
      double dy = (y - other.y);
      double r = collisionRadius + other.collisionRadius;
      return dx * dx + dy * dy <= r * r;
    }
    return false;
  }

  /** Moves this AsteroidGameObject. */
  void move() {
    updatePosition();
  }

  /** Updates the position of this AsteroidGameObject. */
  void updatePosition() {
    x = x + vX * dt;
    y = y + vY * dt;
  }

  /** Returns true iff this object is destroyed */
  abstract boolean isDestroyed();

  /**
   * Resolves collision with other
   *
   * @param other object being collided with.
   */
  abstract void resolveCollision(AsteroidGameObject other);

  /**
   * Draws the bitmap onto canvas centered at x, y rotated by angle and scaled to have a radius of
   * size
   *
   * @param canvas the canvas to draw on
   * @param bitmap the bitmap to draw
   */
  void drawRotatedBitmap(Canvas canvas, Bitmap bitmap) {
    Matrix matrix = new Matrix();
    matrix.preRotate((float) Math.toDegrees(angle), bitmap.getWidth() / 2, bitmap.getHeight() / 2);
    matrix.postScale(
        2 * (float) collisionRadius / (float) bitmap.getWidth(),
        2 * (float) collisionRadius / (float) bitmap.getWidth(),
        bitmap.getWidth() / 2,
        bitmap.getHeight() / 2);
    matrix.postTranslate(
        (float) (x - bitmap.getWidth() / 2.0), (float) (y - bitmap.getHeight() / 2.0));
    canvas.drawBitmap(bitmap, matrix, null);
  }
}
