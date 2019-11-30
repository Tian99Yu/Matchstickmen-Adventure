package com.example.game.gamecode.Asteroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;

class Projectile extends AsteroidGameObject {
  /** Remaining time until Projectile gets despawned */
  private int range;
  /** Damage that can be inflicted by this projectile. */
  private int damage;

  Projectile(
      double x,
      double y,
      double vX,
      double vY,
      double angle,
      double collisionRadius,
      int range,
      int damage) {
    super(x, y, vX, vY, angle, collisionRadius);
    this.range = range;
    this.damage = damage;
  }

  @Override
  void move() {
    range--;
    super.move();
  }

  @Override
  boolean isDestroyed() {
    return range <= 0;
  }

  int getDamage() {
    return damage;
  }

  @Override
  void resolveCollision(AsteroidGameObject other) {
    if (other instanceof Asteroid) {
      range = 0;
    }
  }
}
