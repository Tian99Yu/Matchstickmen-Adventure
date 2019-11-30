package com.example.game.gamecode.Asteroids;

import java.util.ArrayList;
import java.util.List;

public class AsteroidLauncher extends WeaponSystem<Asteroid> {
  /** Number of hitpoints asteroids fired by this launcher has. */
  protected int asteroidHitPoints;
  /** The level of the asteroids fired by this launcher. */
  private int asteroidLevel;

  AsteroidLauncher(
      double muzzleVelocity,
      double spread,
      int cooldown,
      double asteroidSize,
      int asteroidHitPoints,
      int asteroidLevel) {
    super(muzzleVelocity, spread, cooldown, asteroidSize);
    this.asteroidHitPoints = asteroidHitPoints;
    this.asteroidLevel = asteroidLevel;
  }

  @Override
  List<Asteroid> attemptFire(
      double x, double y, double vX, double vY, double firingAngle, boolean weaponActive) {
    double weaponVelocity = Math.sqrt(vX * vX + vY * vY);
    List<Asteroid> newAsteroid = new ArrayList<>();
    if (cooldownState == 0 && weaponActive) {
      double angle = firingAngle + Math.random() * spread - spread / 2;
      newAsteroid.add(
          new Asteroid(
              x + 60*Math.cos(angle),
              y + 60*Math.sin(angle),
              (muzzleVelocity + weaponVelocity) * Math.cos(angle),
              (muzzleVelocity + weaponVelocity) * Math.sin(angle),
              angle,
              ammoSize,
              asteroidHitPoints,
              asteroidLevel));
      cooldownState = cooldown;
    } else if (cooldownState > 0) {
      cooldownState--;
    }
    return newAsteroid;
  }
}
