package com.example.game.gamecode.Asteroids;

import android.graphics.Canvas;

import java.util.List;

public class Ship extends AsteroidGameObject {
  /** Fixed amount of acceleration ship has when thruster is on.*/
  final static double THRUST = 1;
  /** Fixed rate ship can turn.*/
  final static double TURN_RATE = 0.1;
  /** Maximum velocity ship can travel. */
  final static double maxVelocity = 10;
  /** Whether or not the thruster is on. */
  boolean thrusterActive = false;

  /** Whether or not the main weapon is firing. */
  boolean mainArmamentActive = false;
  /** The weapon this ship is using. */
  WeaponSystem mainArmament;
  /** Angle player wants ship to face. */
  double targetDirection;


  public Ship(double x, double y, double vX, double vY, double angle, double collisionRadius, WeaponSystem mainArmament) {
    super(x, y, vX, vY, angle, collisionRadius);
    this.mainArmament = mainArmament;
    this.targetDirection = angle;
  }

  @Override
  void move() {
    if (angle != targetDirection) {
      if (Math.abs(AngleUtils.signedAngularDifference(targetDirection, angle)) < TURN_RATE) {
        angle = targetDirection;
      } else {
        angle = AngleUtils.normalize(angle + Math.copySign(TURN_RATE, AngleUtils.signedAngularDifference(targetDirection, angle)));
      }
    }
    if (thrusterActive) {
      double aX = THRUST *Math.cos(angle);
      double aY = THRUST *Math.sin(angle);
      double newVX = vX + aX*dt;
      double newVY = vY + aY*dt;
      if (newVX*newVX + newVY*newVY <= maxVelocity*maxVelocity) {
        vX = newVX;
        vY = newVY;
      }
    }
    updatePosition();
  }

  /**
   * Tries to fire the main weapon system. It will fire iff mainArmamentActive.
   *
   * @return the projectiles that just got fired.
   */
  public List<Projectile> attemptFireMainArmament() {
    return mainArmament.attemptFire(x, y, angle, mainArmamentActive);
  }

  public void setThrusterActive(boolean thrusterActive) {
    this.thrusterActive = thrusterActive;
  }

  public void setMainArmamentActive(boolean mainArmamentActive) {
    this.mainArmamentActive = mainArmamentActive;
  }

  public void setTargetDirection(double targetDirection) {
    this.targetDirection = targetDirection;
  }

  @Override
  public void draw(Canvas canvas) {

  }
}
