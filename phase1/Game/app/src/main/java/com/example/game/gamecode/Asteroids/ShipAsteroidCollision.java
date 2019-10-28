package com.example.game.gamecode.Asteroids;

class ShipAsteroidCollision extends CollisionStrategy {
  ShipAsteroidCollision(AsteroidGameObject first, AsteroidGameObject second) {
    super(first, second);
  }

  @Override
  void resolve() {
    ((Ship) first).setDestroyed();
  }
}
