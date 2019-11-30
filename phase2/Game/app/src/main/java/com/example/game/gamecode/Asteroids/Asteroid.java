package com.example.game.gamecode.Asteroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.util.ArrayList;
import java.util.List;

class Asteroid extends AsteroidGameObject {
  /** Number of hitpoints this asteroid has remaining. */
  private int hitPoints;
  /** The level of the asteroid, higher means larger. Asteroids of level 0 will not divide. */
  private int level;

  Asteroid(
      double x,
      double y,
      double vX,
      double vY,
      double angle,
      double collisionRadius,
      int hitPoints,
      int level) {
    super(x, y, vX, vY, angle, collisionRadius);
    this.hitPoints = hitPoints;
    this.level = level;
  }

  /** Returns smaller asteroids upon destruction. */
  List<AsteroidGameObject> split(
      int newHp, LivesManager livesManager, WeaponPowerupUnlocker weaponPowerupUnlocker) {
    List<AsteroidGameObject> smallerAsteroids = new ArrayList<>();
    if (isDestroyed() && level > 0) {
      for (int i = 0; i < AsteroidCustomizations.splittingRatio; i++) {
        double newX = x + (int) ((int) (Math.random() * collisionRadius) - collisionRadius / 2);
        double newY = y + (int) ((int) (Math.random() * collisionRadius) - collisionRadius / 2);
        double newVX = 1.1 * vX + 200 * Math.random() - 100;
        double newVY = 1.1 * vY + 200 * Math.random() - 100;
        double newAngle = Math.random() * 2 * Math.PI;
        double newCollisionRadius = collisionRadius * 0.6;
        if (Math.random() < 0.05) {
          smallerAsteroids.add(
              PowerupFactory.getRandomPowerup(
                  newX,
                  newY,
                  newVX,
                  newVY,
                  newAngle,
                  newCollisionRadius,
                  livesManager,
                  weaponPowerupUnlocker));
        } else {
          smallerAsteroids.add(
              new Asteroid(
                  newX, newY, newVX, newVY, newAngle, newCollisionRadius, newHp, level - 1));
        }
      }
    }
    return smallerAsteroids;
  }

  /**
   * Returns the number of points the player gains for destroying this asteroid.
   *
   * @return the value of this asteroid.
   */
  int getValue() {
    return (level + 1) * ((int) (Math.random() * 90) + 10);
  }

  @Override
  boolean isDestroyed() {
    return hitPoints <= 0;
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
      vX = speed * dx / norm + Math.random() * 20 - 10;
      vY = speed * dy / norm + Math.random() * 20 - 10;
      if (dx != 0) {
        x += 1.5 * dx / Math.abs(dx);
      }
      if (dy != 0) {
        y += 1.5 * dy / Math.abs(dy);
      }
    } else if (other instanceof Projectile) {
      hitPoints -= ((Projectile) other).getDamage();
    } else if (other instanceof Ship && !((Ship) other).hasSpawnProtection()) {
      hitPoints = 0;
    }
  }
}
