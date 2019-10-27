package com.example.game.gamecode.Asteroids;

import com.example.game.gamecode.GameObject;

abstract class AsteroidGameObject extends GameObject {
  /** time interval to approximate movement*/
  final static double dt = 1/60;
  /** velocity in the x and y direction of the AsteroidGameObject */
  double vX, vY;
  /** position of AsteroidGameObject */
  double x, y;
  /** angle AsteroidGameObject is pointing with respect to horizontal */
  double angle;
  /** hitbox of AsteroidGameObject assuming perfect ball */
  double collisionRadius;

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
    x = x + vX*dt;
    y = y + vY*dt;
  }
}
