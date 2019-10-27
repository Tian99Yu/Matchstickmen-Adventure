package com.example.game.gamecode.Asteroids;

import java.util.ArrayList;
import java.util.List;

public class AutoCannon implements WeaponSystem {
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

  public AutoCannon(
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

  public List<Projectile> attemptFire(double x, double y, double shipAngle, boolean weaponActive) {
    ArrayList<Projectile> newProjectiles = new ArrayList<>();
    if (cooldownState == 0 && weaponActive) {
      double angle = shipAngle + Math.random() * spread - spread / 2;
      newProjectiles.add(
          new Projectile(
              x,
              y,
              muzzleVelocity * Math.cos(angle),
              muzzleVelocity * Math.sin(angle),
              angle,
              projectileSize,
              range,
              damage));
    } else if (cooldownState > 0) {
      cooldownState--;
    }
    return newProjectiles;
  }
}
