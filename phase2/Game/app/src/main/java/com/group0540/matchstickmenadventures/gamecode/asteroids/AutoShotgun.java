package com.group0540.matchstickmenadventures.gamecode.asteroids;

import java.util.ArrayList;
import java.util.List;

class AutoShotgun extends WeaponSystem<Projectile> {
  /** Number of projectiles fired. */
  final private int numProjectiles;
  /** Range of projectiles fired. */
  final private int range;
  /** Damage each projectile fired by this weapon inflicts. */
  final private int damage;

  AutoShotgun(
      double muzzleVelocity,
      double spread,
      int cooldown,
      double projectileSize,
      int range,
      int damage,
      int numProjectiles) {
    super(muzzleVelocity, spread, cooldown, projectileSize);
    this.numProjectiles = Math.max(2, numProjectiles);
    this.range = range;
    this.damage = damage;
  }

  @Override
  List<Projectile> attemptFire(
      double x, double y, double vX, double vY, double shipAngle, boolean weaponActive) {
    double weaponVelocity = Math.sqrt(vX * vX + vY * vY);
    List<Projectile> newProjectiles = new ArrayList<>();
    if (cooldownState == 0 && weaponActive) {
      double startAngle = shipAngle - spread / 2;
      for (int i = 0; i < numProjectiles; i++) {
        double angle =
            startAngle
                + i * spread / (numProjectiles - 1)
                + 0.1 * spread * Math.random()
                - 0.05 * spread;
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
      }
      cooldownState = cooldown;
    } else if (cooldownState > 0) {
      cooldownState--;
    }
    return newProjectiles;
  }
}
