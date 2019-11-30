package com.example.game.gamecode.Asteroids;

import java.util.ArrayList;
import java.util.List;

class AutoCannon extends WeaponSystem<Projectile> {
  /** Range of projectiles fired. */
  final private int range;
  /** Damage each projectile fired by this weapon inflicts. */
  final private int damage;

  AutoCannon(
      double muzzleVelocity,
      double spread,
      int cooldown,
      double projectileSize,
      int range,
      int damage) {
    super(muzzleVelocity, spread, cooldown, projectileSize);
    this.range = range;
    this.damage = damage;
  }

  @Override
  List<Projectile> attemptFire(
      double x, double y, double vX, double vY, double shipAngle, boolean weaponActive) {
    double weaponVelocity = Math.sqrt(vX * vX + vY * vY);
    List<Projectile> newProjectiles = new ArrayList<>();
    if (cooldownState == 0 && weaponActive) {
      double angle = shipAngle + Math.random() * spread - spread / 2;
      newProjectiles.add(
          new Projectile(
              x,
              y,
              (muzzleVelocity + weaponVelocity) * Math.cos(angle),
              (muzzleVelocity + weaponVelocity) * Math.sin(angle),
              angle,
                  ammoSize,
              range,
              damage));
      cooldownState = cooldown;
    } else if (cooldownState > 0) {
      cooldownState--;
    }
    return newProjectiles;
  }
}
