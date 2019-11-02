package com.example.game.gamecode.Asteroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.util.ArrayList;
import java.util.List;

class Asteroid extends AsteroidGameObject {
  /** Number of hitpoints this asteroid has remaining. */
  private int hp;
  /** The level of the asteroid, higher means larger. Asteroids of level 0 will not divide. */
  private int level;
  /** appearance */
  static Bitmap appearance;

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
                1.1 * vX + 200 * Math.random() - 100,
                1.1 * vY + 200 * Math.random() - 100,
                Math.random() * 2 * Math.PI,
                collisionRadius * 0.6,
                newHp,
                level - 1));
      }
    }
    return smallerAsteroids;
  }

  /**
   * Returns the number of points the player gains for destroying this asteroid.
   *
   * @return the value of this asteroid.
   */
  public int getValue() {
    return (level + 1) * ((int) (Math.random() * 90) + 10);
  }

  @Override
  boolean isDestroyed() {
    return hp <= 0;
  }

  @Override
  void resolveCollision(AsteroidGameObject other) {
    if (other instanceof Asteroid) {
      double dx = x - other.x;
      double dy = y - other.y;
      if (dx == 0 && dy == 0) {
        return;
      }
      // basic collision resolution where objects reflect at the same speed away from each other
      double norm = Math.sqrt(dx * dx + dy * dy);
      double speed = Math.sqrt(vX * vX + vY * vY);
      vX = speed * dx / norm + Math.random()*20 - 10;
      vY = speed * dy / norm + Math.random()*20 - 10;
      if (dx != 0) {
        x += 1.5 * dx / Math.abs(dx);
      }
      if (dy != 0) {
        y += 1.5 * dy / Math.abs(dy);
      }
    } else if (other instanceof Projectile) {
      hp -= ((Projectile) other).getDamage();
    } else if (other instanceof Ship && !((Ship) other).hasSpawnProtection()) {
      hp = 0;
    }
  }

  @Override
  public void draw(Canvas canvas) {
    drawRotatedBitmap(canvas, appearance);
  }
}
