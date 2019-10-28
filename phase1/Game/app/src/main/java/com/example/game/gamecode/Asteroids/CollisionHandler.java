package com.example.game.gamecode.Asteroids;

class CollisionHandler {
  private CollisionHandler() {}

  static void handle(AsteroidGameObject first, AsteroidGameObject second) {
    CollisionStrategy collision;
    if (first instanceof Ship && second instanceof Asteroid) {
      collision = new ShipAsteroidCollision(first, second);
    } else if (first instanceof Projectile && second instanceof Asteroid) {
      collision = new ProjectileAsteroidCollision(first, second);
    } else if (first instanceof Asteroid && second instanceof Asteroid) {
      collision = new AsteroidAsteroidCollision(first, second);
    } else {
      return;
    }
    collision.resolve();
  }
}
