package com.example.game.gamecode.Asteroids;

import java.util.ArrayList;
import java.util.List;

class AutoCannon extends WeaponSystem {
  /** Speed projectiles fired by this weapon travels. */
  private double muzzleVelocity;
  /** Spread of projectiles fired by this weapon. */
  private double spread;
  /** Cooldown time before weapon can be fired again. */
  private int cooldown;
  /** Stage of cooldown, weapon can fire again once 0. */
  private int cooldownState;
  /** Size of projectiles fired by this weapon. */
  private double projectileSize;
  /** Range of projectiles fired. */
  private int range;
  /** Damage each projectile fired by this weapon inflicts. */
  private int damage;

  AutoCannon(
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
