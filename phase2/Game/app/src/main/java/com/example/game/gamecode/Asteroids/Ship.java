package com.example.game.gamecode.Asteroids;

import android.graphics.Bitmap;

import android.graphics.Canvas;

import java.util.List;

class Ship extends AsteroidGameObject {
  /** Fixed amount of acceleration ship has when thruster is on. */
  private double thrust;
  /** Fixed rate ship can turn. */
  private double turnRate;
  /** Maximum velocity ship can travel. */
  private double maxVelocity;
  /** Whether or not the thruster is on. */
  private boolean thrusterActive = false;
  /** Whether or not the main weapon is firing. */
  private boolean mainArmamentActive = false;
  /** The weapon this ship is using. */
  private WeaponSystem mainArmament;
  /** Angle player wants ship to face. */
  private double targetAngle;
  /** Starting position and angle of ship. */
  private double startX, startY, startAngle;
  /** Whether or not this ship is destroyed. */
  private boolean destroyed;
  /** Spawn protection available. */
  private static final int spawnProtectionTime = 150;
  /** Spawn protection left. */
  private int spawnProtectionLeft = spawnProtectionTime;

  /** appearance */
  static Bitmap appearance;

  Ship(
      double x,
      double y,
      double vX,
      double vY,
      double angle,
      double collisionRadius,
      double thrust,
      double turnRate,
      double maxVelocity,
      WeaponSystem mainArmament) {
    super(x, y, vX, vY, angle, collisionRadius);
    this.thrust = thrust;
    this.turnRate = turnRate;
    this.maxVelocity = maxVelocity;
    this.mainArmament = mainArmament;
    startX = x;
    startY = y;
    startAngle = angle;
    this.targetAngle = angle;
  }

  @Override
  void move() {
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
    if (thrusterActive) {
      double aX = thrust * Math.cos(angle);
      double aY = thrust * Math.sin(angle);
      double newVX = vX + aX * dt;
      double newVY = vY + aY * dt;
      if (newVX * newVX + newVY * newVY <= maxVelocity * maxVelocity) {
        vX = newVX;
        vY = newVY;
      }
    }
    if (spawnProtectionLeft > 0) {
      spawnProtectionLeft--;
    }
    updatePosition();
  }

  @Override
  boolean isDestroyed() {
    return destroyed;
  }

  /**
   * Tries to fire the main weapon system. It will fire iff mainArmamentActive.
   *
   * @return the projectiles that just got fired.
   */
  List<Projectile> attemptFireMainArmament() {
    return mainArmament.attemptFire(x, y, vX, vY, getAngle(), mainArmamentActive);
  }

  void setThrusterActive(boolean thrusterActive) {
    this.thrusterActive = thrusterActive;
  }

  void setMainArmamentActive(boolean mainArmamentActive) {
    this.mainArmamentActive = mainArmamentActive;
  }

  void setTargetAngle(double targetAngle) {
    this.targetAngle = targetAngle;
  }

  /** Returns ship to starting location */
  void reset() {
    x = startX;
    y = startY;
    vX = 0;
    vY = 0;
    setAngle(startAngle);
    targetAngle = startAngle;
    destroyed = false;
    spawnProtectionLeft = spawnProtectionTime;
  }

  boolean hasSpawnProtection() {
    return spawnProtectionLeft > 0;
  }

  @Override
  void resolveCollision(AsteroidGameObject other) {
    if (spawnProtectionLeft == 0 && other instanceof Asteroid) {
      destroyed = true;
    }
  }
}
