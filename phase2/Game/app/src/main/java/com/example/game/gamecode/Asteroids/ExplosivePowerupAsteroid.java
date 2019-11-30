package com.example.game.gamecode.Asteroids;

import java.util.ArrayList;
import java.util.List;

class ExplosivePowerupAsteroid extends PowerupAsteroid {
  /** The number of projectiles this explodes into. */
  private int shrapnelCount = 1;

  private WeaponSystem<Projectile> shrapnelGenerator = ProjectileLauncherFactory.getProjectileLauncher(ProjectileLauncherType.SHRAPNEL_GENERATOR);

  ExplosivePowerupAsteroid(
      double x,
      double y,
      double vX,
      double vY,
      double angle,
      double collisionRadius,
      int shrapnelCount) {
    super(x, y, vX, vY, angle, collisionRadius);
    if (shrapnelCount > 0) {
      this.shrapnelCount = shrapnelCount;
    }
  }

  @Override
  List<AsteroidGameObject> split(
          int newHp, LivesManager livesManager, WeaponPowerupUnlocker weaponPowerupUnlocker) {
    if (powerupActive()) {
      List<AsteroidGameObject> shrapnel = new ArrayList<>();
      double initialAngle = Math.random() * 2 * Math.PI;
      double angleDifference = 2 * Math.PI / shrapnelCount;
      for (int i = 0; i < shrapnelCount; i++) {
        double shrapnelAngle = initialAngle + i * angleDifference;
        shrapnel.addAll(shrapnelGenerator.attemptFire(x, y, 0, 0, shrapnelAngle, true));
      }
      return shrapnel;
    } else {
      return super.split(newHp, livesManager, weaponPowerupUnlocker);
    }
  }
}
