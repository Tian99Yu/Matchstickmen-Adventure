package com.example.game.gamecode.Asteroids;

import java.util.List;

public interface WeaponSystem {
  public List<Projectile> attemptFire(double x, double y, double shipAngle, boolean weaponActive);
}
