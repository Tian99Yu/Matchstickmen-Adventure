package com.example.game.gamecode.Asteroids;

import java.util.List;
import java.util.Random;

class AsteroidSpawner extends Asteroid {
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
  /** The targeting computer this ai uses. */
  private TargetingComputer targetingComputer;
  /** The target of this asteroid spawner. */
  private AsteroidGameObject target;
  /** The behaviour of this asteroid spawner. */
  private boolean isAggressive = false;

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
      WeaponSystem<Asteroid> asteroidLauncher,
      AsteroidGameObject target) {
    super(x, y, vX, vY, angle, collisionRadius, hitPoints, level);
    this.thrust = thrust;
    this.turnRate = turnRate;
    this.maxVelocity = maxVelocity;
    this.asteroidLauncher = asteroidLauncher;
    targetAngle = Math.random() * 2 * Math.PI;
    this.targetingComputer = new TargetingComputer(asteroidLauncher);
    this.target = target;
  }

  @Override
  void move() {
    if (Math.random() < 0.01) {
      isAggressive = !isAggressive;
    }
    if (isAggressive) {
      moveAggressively();
    } else {
      moveRandomly();
    }
  }

  private void moveRandomly() {
    if (Math.random() < 0.01) {
      targetAngle = Math.random() * 2 * Math.PI;
    }
    turn(targetAngle, turnRate);
    double angle = getAngle();
    if (Math.random() < 0.4) {
      accelerate(thrust, maxVelocity);
    }
    super.move();
  }

  private void moveAggressively() {
    targetAngle =
        targetingComputer.getLeadAngle(
            x + 2 * collisionRadius * Math.cos(getAngle()),
            y + 2 * collisionRadius * Math.sin(getAngle()),
            target);
    double angle = getAngle();
    if (angle != targetAngle) {
      turn(targetAngle, turnRate);
    }
    super.move();
  }

  @Override
  int getValue() {
    return 500 + (int) (Math.random() * 500);
  }

  List<Asteroid> attemptSpawnAsteroid() {
    return asteroidLauncher.attemptFire(
        x + 2 * collisionRadius * Math.cos(getAngle()),
        y + 2 * collisionRadius * Math.sin(getAngle()),
        vX,
        vY,
        getAngle(),
        true);
  }
}
