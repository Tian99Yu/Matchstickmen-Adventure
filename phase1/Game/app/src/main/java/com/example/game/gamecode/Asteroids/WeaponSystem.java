package com.example.game.gamecode.Asteroids;

import java.util.List;

abstract class WeaponSystem {
  /** Speed projectiles fired by this weapon travels. */
  double muzzleVelocity;
  /** Spread of projectiles fired by this weapon. */
  double spread;
  /** Cooldown time before weapon can be fired again. */
  int cooldown;
  /** Stage of cooldown, weapon can fire again once 0. */
  int cooldownState;
  /** Size of projectiles fired by this weapon. */
  double projectileSize;
  /** Range of projectiles fired. */
  int range;
  /** Damage each projectile fired by this weapon inflicts. */
  int damage;

  WeaponSystem(
          double muzzleVelocity,
          double spread,
          int cooldown,
          double projectileSize,
          int range,
          int damage) {
    this.muzzleVelocity = muzzleVelocity;
    this.spread = spread;
    this.cooldown = cooldown;
    this.cooldownState = 0;
    this.projectileSize = projectileSize;
    this.range = range;
    this.damage = damage;
  }

  /** Attempts to fire this weapon. */
  abstract List<Projectile> attemptFire(double x, double y, double vX, double vY, double shipAngle, boolean weaponActive);
}
