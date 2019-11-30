package com.example.game.gamecode.Asteroids;

import java.util.ArrayList;
import java.util.List;

abstract class PowerupAsteroid extends Asteroid {
  /** The amount of time before this decays into an asteroid. */
  private int lifetimeRemaining = 800;

  PowerupAsteroid(double x, double y, double vX, double vY, double angle, double collisionRadius) {
    super(x, y, vX, vY, angle, collisionRadius, 1, 0);
  }

  @Override
  void move() {
    super.move();
    lifetimeRemaining--;
  }

  @Override
  List<AsteroidGameObject> split(
      int newHp, LivesManager livesManager, WeaponPowerupUnlocker weaponPowerupUnlocker) {
    List<AsteroidGameObject> replacementAsteroid = new ArrayList<>();
    if (isDestroyed() && !powerupActive()) {
      replacementAsteroid.add(new Asteroid(x, y, vX, vY, getAngle(), collisionRadius, newHp, 0));
    }
    return replacementAsteroid;
  }

  @Override
  boolean isDestroyed() {
    return !powerupActive() || super.isDestroyed();
  }

  /**
   * Returns whether or not this powerup is still active.
   *
   * @return true iff this powerup is active.
   */
  boolean powerupActive() {
    return lifetimeRemaining > 0;
  }
}
