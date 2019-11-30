package com.example.game.gamecode.Asteroids;

class ProjectileLauncherFactory {
  private ProjectileLauncherFactory() {}

  static WeaponSystem<Projectile> getProjectileLauncher(ProjectileLauncherType projectileLauncherType) {
    switch (projectileLauncherType) {
      case STANDARD_CANNON:
        return new AutoCannon(900, 0.1, 5, 20, 90, 1);
      case STANDARD_SHOTGUN:
        return new AutoShotgun(600, 0.8, 35, 15, 60, 1, 7);
      case SHRAPNEL_GENERATOR:
        return new AutoShotgun(400, 0.3, 0, 5, 60, 1, 10);
      case POWERUP_CANNON:
        return new AutoShotgun(1000, 1, 3, 10, 20, 1, 15);
      default:
        return null;
    }
  }
}
