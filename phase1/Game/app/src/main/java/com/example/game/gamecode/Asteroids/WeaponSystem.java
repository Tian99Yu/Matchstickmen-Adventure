package com.example.game.gamecode.Asteroids;

import java.util.List;

abstract class WeaponSystem {
  /** Attempts to fire this weapon. */
  abstract List<Projectile> attemptFire(double x, double y, double vX, double vY, double shipAngle, boolean weaponActive);
}
