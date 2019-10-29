package com.example.game.gamecode.Asteroids;

class WeaponFactory {
  private WeaponFactory() {}

  static WeaponSystem getWeapon(WeaponType weaponType) {
    switch (weaponType) {
      case DEFAULT_CANNON:
        return new AutoCannon(15, 0.1, 5, 20, 90, 1);
      default:
        return null;
    }
  }
}
