package com.example.game.gamecode.Asteroids;

import com.example.game.gamecode.GameObject;

abstract class AsteroidGameObject extends GameObject {
  /** time interval to approximate movement */
  static final double dt = 1 / 60;
  /** position of AsteroidGameObject */
  double x, y;
  /** velocity in the x and y direction of the AsteroidGameObject */
  double vX, vY;
  /**
   * angle AsteroidGameObject is pointing with respect to horizontal normalized to be between 0
   * inclusive and 2 pi exclusive
   */
  double angle;
  /** hitbox of AsteroidGameObject assuming perfect ball */
  double collisionRadius;

  AsteroidGameObject(
      double x, double y, double vX, double vY, double angle, double collisionRadius) {
    this.x = x;
    this.y = y;
    this.vX = vX;
    this.vY = vY;
    this.angle = angle;
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
      double dx = (x - other.x) * (x - other.x);
      double dy = (y - other.y) * (y - other.y);
      double r = collisionRadius + other.collisionRadius;
      return dx * dx + dy * dy <= r * r;
    }
    return false;
  }

  /** Moves this AsteroidGameObject. */
  void move() {
    updatePosition();
  }

  /** Updates the position of this AsteroidGameObject. */
  void updatePosition() {
    x = x + vX * dt;
    y = y + vY * dt;
  }

  /** Returns true iff this object is destroyed */
  abstract boolean isDestroyed();
}
