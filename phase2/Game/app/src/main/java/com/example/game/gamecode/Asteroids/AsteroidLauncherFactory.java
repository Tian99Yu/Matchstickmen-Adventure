package com.example.game.gamecode.Asteroids;

class AsteroidLauncherFactory {
  private AsteroidLauncherFactory() {}

  static WeaponSystem<Asteroid> getAsteroidLauncher(AsteroidLauncherType asteroidLauncherType) {
    switch (asteroidLauncherType) {
      case STANDARD_ASTEROID_LAUNCHER:
        return new AsteroidLauncher(50, 1, 150, 20, 1, 0);
      default:
        return null;
    }
  }
}
