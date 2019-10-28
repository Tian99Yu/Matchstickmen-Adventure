package com.example.game.gamecode.Asteroids;

class ProjectileAsteroidCollision extends CollisionStrategy {
  ProjectileAsteroidCollision(AsteroidGameObject first, AsteroidGameObject second) {
    super(first, second);
  }

  @Override
  void resolve() {
    ((Projectile) first).destroy();
    ((Asteroid) second).hit(((Projectile) first).getDamage());
  }
}
