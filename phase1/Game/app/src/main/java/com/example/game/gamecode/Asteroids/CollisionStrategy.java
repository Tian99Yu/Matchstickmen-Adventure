package com.example.game.gamecode.Asteroids;

abstract class CollisionStrategy {
  /** The two colliding objects. */
  AsteroidGameObject first, second;

  CollisionStrategy(AsteroidGameObject first, AsteroidGameObject second) {
    this.first = first;
    this.second = second;
  }

  /** Resolves the collision between first and second. */
  abstract void resolve();
}
