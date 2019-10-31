package com.example.game.gamecode.Asteroids;

class WeaponFactory {
  private WeaponFactory() {}

  static WeaponSystem getWeapon(WeaponType weaponType) {
    switch (weaponType) {
      case STANDARD_CANNON:
        return new AutoCannon(900, 0.1, 5, 20, 90, 1);
      case STANDARD_SHOTGUN:
        return new AutoShotgun(600, 0.8, 35, 15, 60, 1, 5);
      default:
        return null;
    }
  }
}
