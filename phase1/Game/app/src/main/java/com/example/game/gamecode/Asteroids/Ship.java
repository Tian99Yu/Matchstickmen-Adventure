package com.example.game.gamecode.Asteroids;

import android.graphics.Canvas;

import java.util.List;

class Ship extends AsteroidGameObject {
  /** Fixed amount of acceleration ship has when thruster is on. */
  private static final double THRUST = 1;
  /** Fixed rate ship can turn. */
  private static final double TURN_RATE = 0.1;
  /** Maximum velocity ship can travel. */
  private static final double maxVelocity = 10;
  /** Whether or not the thruster is on. */
  private boolean thrusterActive = false;
  /** Whether or not the main weapon is firing. */
  private boolean mainArmamentActive = false;
  /** The weapon this ship is using. */
  private WeaponSystem mainArmament;
  /** Angle player wants ship to face. */
  private double targetDirection;
  /** Starting position and angle of ship. */
  private double startX, startY, startAngle;
  /** Whether or not this ship is destroyed. */
  private boolean destroyed;

  Ship(
      double x,
      double y,
      double vX,
      double vY,
      double angle,
      double collisionRadius,
      WeaponSystem mainArmament) {
    super(x, y, vX, vY, angle, collisionRadius);
    this.mainArmament = mainArmament;
    startX = x;
    startY = y;
    startAngle = angle;
    this.targetDirection = angle;
  }

  @Override
  void move() {
    if (angle != targetDirection) {
      if (Math.abs(AngleUtils.signedAngularDifference(targetDirection, angle)) < TURN_RATE) {
        angle = targetDirection;
      } else {
        angle =
            AngleUtils.normalize(
                angle
                    + Math.copySign(
                        TURN_RATE, AngleUtils.signedAngularDifference(targetDirection, angle)));
      }
    }
    if (thrusterActive) {
      double aX = THRUST * Math.cos(angle);
      double aY = THRUST * Math.sin(angle);
      double newVX = vX + aX * dt;
      double newVY = vY + aY * dt;
      if (newVX * newVX + newVY * newVY <= maxVelocity * maxVelocity) {
        vX = newVX;
        vY = newVY;
      }
    }
    updatePosition();
  }

  @Override
  boolean isDestroyed() {
    return destroyed;
  }

  void setDestroyed() {
    this.destroyed = true;
  }

  /**
   * Tries to fire the main weapon system. It will fire iff mainArmamentActive.
   *
   * @return the projectiles that just got fired.
   */
  List<Projectile> attemptFireMainArmament() {
    return mainArmament.attemptFire(x, y, angle, mainArmamentActive);
  }

  void setThrusterActive(boolean thrusterActive) {
    this.thrusterActive = thrusterActive;
  }

  void setMainArmamentActive(boolean mainArmamentActive) {
    this.mainArmamentActive = mainArmamentActive;
  }

  void setTargetDirection(double targetDirection) {
    this.targetDirection = targetDirection;
  }

  /** Returns ship to starting location */
  void reset() {
    x = startX;
    y = startY;
    vX = 0;
    vY = 0;
    angle = startAngle;
    destroyed = false;
  }

  @Override
  public void draw(Canvas canvas) {}
}
