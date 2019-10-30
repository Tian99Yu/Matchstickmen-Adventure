package com.example.game.gamecode.Asteroids;

import android.graphics.Bitmap;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

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
  /** Spawn protection available. */
  private static final int spawnProtectionTime = 150;
  /** Spawn protection left. */
  private int spawnProtectionLeft = spawnProtectionTime;

  /** appearance */
  static Bitmap appearance;
  /** color of asteroid game object */
  static Paint paint;

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
        Log.i("da", String.valueOf(targetDirection)+ ',' + String.valueOf(angle)+ ',' + String.valueOf(AngleUtils.signedAngularDifference(targetDirection, angle)));
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
    spawnProtectionLeft = spawnProtectionTime;
  }

  @Override
  void resolveCollision(AsteroidGameObject other) {
    if (spawnProtectionLeft == 0 && other instanceof Asteroid) {
      destroyed = true;
    }
  }

  @Override
  public void draw(Canvas canvas) {
    drawRotatedBitmap(canvas, appearance);
  }
}
