package com.group0540.matchstickmenadventures.gamecode.asteroids;

abstract class AsteroidGameObject {
  /** time interval to approximate movement */
  private final double dt = 1.0 / 60.0;
  /** position of AsteroidGameObject */
  double x, y;
  /** velocity in the x and y direction of the AsteroidGameObject */
  double vX, vY;
  /**
   * angle AsteroidGameObject is pointing with respect to horizontal with 0 being east rotating
   * clockwise restricted to be between 0 inclusive and 2 pi exclusive
   */
  private double angle;

  /**
   * Returns the angle the AsteroidGameObject is facing.
   *
   * @return the angle.
   */
  public double getAngle() {
    return angle;
  }

  /**
   * Sets attribute angle to normalized version of angle.
   *
   * @param angle the new angle the AsteroidGameObject is facing.
   */
  public void setAngle(double angle) {
    this.angle = AngleUtils.normalize(angle);
  }

  /** hitbox of AsteroidGameObject assuming perfect ball */
  double collisionRadius;

  AsteroidGameObject(
      double x, double y, double vX, double vY, double angle, double collisionRadius) {
    this.x = x;
    this.y = y;
    this.vX = vX;
    this.vY = vY;
    this.setAngle(angle);
    this.collisionRadius = collisionRadius;
  }

  /**
   * Returns true iff this object and other object are within collision distance of each other.
   *
   * @param other object with which we are checking for collision with.
   * @return whether or not this has collided with other.
   */
  boolean isColliding(AsteroidGameObject other) {
    if (other != null) {
      double dx = x - other.x;
      double dy = y - other.y;
      double r = collisionRadius + other.collisionRadius;
      return dx * dx + dy * dy <= r * r;
    }
    return false;
  }

  /**
   * Acclerates the asteroid game object.
   * @param thrust the amount of thrust to accelerate at.
   * @param maxVelocity the max velocity.
   */
  void  accelerate(double thrust, double maxVelocity) {
    double aX = thrust * Math.cos(angle);
    double aY = thrust * Math.sin(angle);
    double newVX = vX + aX * dt;
    double newVY = vY + aY * dt;
    if (newVX * newVX + newVY * newVY <= maxVelocity * maxVelocity) {
      vX = newVX;
      vY = newVY;
    }
  }

  /**
   * Turns the asteroid game object.
   * @param targetAngle the angle to turn towards.
   * @param turnRate the turn rate of the asteroid game object.
   */
  void turn(double targetAngle, double turnRate) {
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
  }

  /** Moves this AsteroidGameObject. */
  void move() {
    x = x + vX * dt;
    y = y + vY * dt;
  }

  /** Returns true iff this object is destroyed */
  abstract boolean isDestroyed();

  /**
   * Resolves collision with other
   *
   * @param other object being collided with.
   */
  abstract void resolveCollision(AsteroidGameObject other);
}
