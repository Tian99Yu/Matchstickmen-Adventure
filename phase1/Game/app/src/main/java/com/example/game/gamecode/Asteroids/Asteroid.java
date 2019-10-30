package com.example.game.gamecode.Asteroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

class Asteroid extends AsteroidGameObject {
  /** Number of hitpoints this asteroid has remaining. */
  private int hp;
  /** The level of the asteroid, higher means larger. Asteroids of level 0 will not divide. */
  private int level;
  /** appearance */
  static Bitmap appearance;
  /** color of asteroid game object */
  static Paint paint;

  Asteroid(
      double x,
      double y,
      double vX,
      double vY,
      double angle,
      double collisionRadius,
      int hp,
      int level) {
    super(x, y, vX, vY, angle, collisionRadius);
    this.hp = hp;
    this.level = level;
  }

  /** Reduces hp by damage amount. */
  void hit(int damage) {
    hp -= damage;
  }

  /** Returns smaller asteroids upon destruction. */
  List<Asteroid> split(int newHp) {
    List<Asteroid> smallerAsteroids = new ArrayList<>();
    if (isDestroyed() && level > 0) {
      int count = (int) (Math.random() * 2) + 2;
      for (int i = 0; i < 3; i++) {
        smallerAsteroids.add(
            new Asteroid(
                x + (int) ((int) (Math.random() * collisionRadius) - collisionRadius / 2),
                y + (int) ((int) (Math.random() * collisionRadius) - collisionRadius / 2),
                1.1 * vX + 2 * Math.random() - 1,
                1.1 * vY + 2 * Math.random() - 1,
                Math.random() * 2 * Math.PI,
                collisionRadius * 0.6,
                newHp,
                level - 1));
      }
    }
    return smallerAsteroids;
  }

  @Override
  boolean isDestroyed() {
    return hp <= 0;
  }

  @Override
  void resolveCollision(AsteroidGameObject other) {
    if (other instanceof Asteroid) {
      double dx = x - other.y;
      double dy = y - other.y;
      if (dx == 0 && dy == 0) {
        return;
      }
      // basic collision resolution where objects reflect at the same speed away from each other
      double norm = Math.sqrt(dx * dx + dy * dy);
      double speed = Math.sqrt(vX * vX + vY * vY);
      vX = speed * dx / norm;
      vY = speed * dy / norm;
      x += 0.5 * dx / Math.abs(dx);
      y += 0.5 * dy / Math.abs(dy);
    } else if (other instanceof Projectile) {
      hit(((Projectile) other).getDamage());
    }
  }

  @Override
  public void draw(Canvas canvas) {
    drawRotatedBitmap(canvas, appearance);
  }
}
