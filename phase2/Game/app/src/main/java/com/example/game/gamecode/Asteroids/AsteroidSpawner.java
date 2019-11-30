package com.example.game.gamecode.Asteroids;

import java.util.List;

public class AsteroidSpawner extends Asteroid {
  /** The launcher this spawner uses to make asteroids. */
  private WeaponSystem<Asteroid> asteroidLauncher;
  /** Angle this asteroid spawner turns towards. */
  private double targetAngle;
  /** Fixed amount of acceleration this asteroid spawner has when thruster is on. */
  private double thrust;
  /** Fixed rate ship can turn. */
  private double turnRate;
  /** Maximum velocity this asteroid spawner can travel. */
  private double maxVelocity;

  AsteroidSpawner(
      double x,
      double y,
      double vX,
      double vY,
      double angle,
      double collisionRadius,
      double thrust,
      double turnRate,
      double maxVelocity,
      int hitPoints,
      int level,
      WeaponSystem<Asteroid> asteroidLauncher) {
    super(x, y, vX, vY, angle, collisionRadius, hitPoints, level);
    this.thrust = thrust;
    this.turnRate = turnRate;
    this.maxVelocity = maxVelocity;
    this.asteroidLauncher = asteroidLauncher;
    targetAngle = Math.random() * 2 * Math.PI;
  }

  @Override
  void move() {
    if (Math.random() < 0.01) {
      targetAngle = Math.random() * 2 * Math.PI;
    }
    double angle = getAngle();
    if (angle != targetAngle) {
      if (Math.abs(AngleUtils.signedAngularDifference(targetAngle, angle)) < turnRate * dt) {
        setAngle(targetAngle);
      } else {
        setAngle(
            angle
                + Math.copySign(
                    turnRate * dt, AngleUtils.signedAngularDifference(targetAngle, angle)));
      }
    }
    if (Math.random() < 0.4) {
      double aX = thrust * Math.cos(angle);
      double aY = thrust * Math.sin(angle);
      double newVX = vX + aX * dt;
      double newVY = vY + aY * dt;
      if (newVX * newVX + newVY * newVY <= maxVelocity * maxVelocity) {
        vX = newVX;
        vY = newVY;
      }
    }
    super.move();
  }

  @Override
  int getValue() {
    return 500 + (int) (Math.random() * 500);
  }

  List<Asteroid> attemptSpawnAsteroid() {
    return asteroidLauncher.attemptFire(x, y, vX, vY, getAngle(), true);
  }
}
