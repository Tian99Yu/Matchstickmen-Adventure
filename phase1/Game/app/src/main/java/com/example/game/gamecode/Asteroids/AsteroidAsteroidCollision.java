package com.example.game.gamecode.Asteroids;

class AsteroidAsteroidCollision extends CollisionStrategy {
  AsteroidAsteroidCollision(AsteroidGameObject first, AsteroidGameObject second) {
    super(first, second);
  }

  @Override
  void resolve() {
    Asteroid firstAsteroid = (Asteroid) first, secondAsteroid = (Asteroid) second;
    double dx = firstAsteroid.x - secondAsteroid.y;
    double dy = firstAsteroid.y - secondAsteroid.y;
    if (dx == 0 && dy == 0) {
      return;
    }
    // basic collision resolution where objects reflect at the same speed away from each other
    double norm = Math.sqrt(dx * dx + dy * dy);
    double firstVelocity =
        Math.sqrt(firstAsteroid.vX * firstAsteroid.vX + firstAsteroid.vY * firstAsteroid.vY);
    firstAsteroid.vX = firstVelocity * dx / norm;
    firstAsteroid.vY = firstVelocity * dy / norm;
    double secondVelocity =
        Math.sqrt(secondAsteroid.vX * secondAsteroid.vX + secondAsteroid.vY * secondAsteroid.vY);
    secondAsteroid.vX = -secondVelocity * dx / norm;
    secondAsteroid.vY = -secondVelocity * dy / norm;
  }
}
