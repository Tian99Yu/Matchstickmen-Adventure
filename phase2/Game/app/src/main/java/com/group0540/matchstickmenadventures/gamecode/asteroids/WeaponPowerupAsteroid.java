package com.group0540.matchstickmenadventures.gamecode.asteroids;

import java.util.ArrayList;
import java.util.List;

class WeaponPowerupAsteroid extends PowerupAsteroid {

  WeaponPowerupAsteroid(
      double x,
      double y,
      double vX,
      double vY,
      double angle,
      double collisionRadius) {
    super(x, y, vX, vY, angle, collisionRadius);
  }

  @Override
  List<AsteroidGameObject> split(
          int newHp, LivesManager livesManager, WeaponPowerupUnlocker weaponPowerupUnlocker) {
    if (powerupActive()) {
      weaponPowerupUnlocker.unlockPlayerWeaponPowerup();
      return new ArrayList<>();
    } else {
      return super.split(newHp, livesManager, weaponPowerupUnlocker);
    }
  }
}
