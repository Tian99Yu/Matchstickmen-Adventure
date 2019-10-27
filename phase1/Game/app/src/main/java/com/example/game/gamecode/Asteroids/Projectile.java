package com.example.game.gamecode.Asteroids;

import android.graphics.Canvas;

public class Projectile extends AsteroidGameObject {
  /** Remaining time until Projectile gets despawned */
  int range;
  /** Damage that can be inflicted by this  projectile. */
  int damage;

  public Projectile(
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
    updatePosition();
  }

  public int getDamage() {
    return damage;
  }

  @Override
  public void draw(Canvas canvas) {}
}
