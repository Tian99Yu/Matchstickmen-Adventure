package com.example.game.gamecode.Asteroids;

import java.util.ArrayList;
import java.util.List;

class AutoCannon extends WeaponSystem {

  AutoCannon(
      double muzzleVelocity,
      double spread,
      int cooldown,
      double projectileSize,
      int range,
      int damage) {
    super(muzzleVelocity, spread, cooldown, projectileSize, range, damage);
  }

  @Override
  List<Projectile> attemptFire(
      double x, double y, double vX, double vY, double shipAngle, boolean weaponActive) {
    double weaponVelocity = Math.sqrt(vX * vX + vY * vY);
    List<Projectile> newProjectiles = new ArrayList<>();
    if (cooldownState == 0 && weaponActive) {
      double angle = AngleUtils.normalize(shipAngle + Math.random() * spread - spread / 2);
      newProjectiles.add(
          new Projectile(
              x,
              y,
              (muzzleVelocity + weaponVelocity) * Math.cos(angle),
              (muzzleVelocity + weaponVelocity) * Math.sin(angle),
              angle,
              projectileSize,
              range,
              damage));
      cooldownState = cooldown;
    } else if (cooldownState > 0) {
      cooldownState--;
    }
    return newProjectiles;
  }
}
