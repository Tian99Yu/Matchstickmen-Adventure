package com.example.game.gamecode.Asteroids;

import java.util.List;

abstract class WeaponSystem<T extends AsteroidGameObject> {
  /** Speed projectiles fired by this weapon travels. */
  final double muzzleVelocity;
  /** Spread of projectiles fired by this weapon. */
  final double spread;
  /** Cooldown time before weapon can be fired again. */
  final int cooldown;
  /** Stage of cooldown, weapon can fire again once 0. */
  int cooldownState;
  /** Size of ammo fired by this weapon. */
  final double ammoSize;

  public WeaponSystem(double muzzleVelocity, double spread, int cooldown, double ammoSize) {
    this.muzzleVelocity = muzzleVelocity;
    this.spread = spread;
    this.cooldown = cooldown;
    this.ammoSize = ammoSize;
  }

  /** Attempts to fire this weapon. */
  abstract List<T> attemptFire(
      double x, double y, double vX, double vY, double shipAngle, boolean weaponActive);
}
