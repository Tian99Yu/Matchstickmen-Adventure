package com.example.game.gamecode.Asteroids;

import java.util.ArrayList;
import java.util.List;

public class AutoShotgun extends WeaponSystem {
  int numProjectiles;

  public AutoShotgun(
      double muzzleVelocity,
      double spread,
      int cooldown,
      double projectileSize,
      int playAreaWidth,
      int playAreaHeight,
      int range,
      int damage,
      int numProjectiles) {
    super(
        muzzleVelocity,
        spread,
        cooldown,
        projectileSize,
        range,
        damage,
        playAreaWidth,
        playAreaHeight);
    this.numProjectiles = numProjectiles;
  }

  @Override
  List<Projectile> attemptFire(
      double x, double y, double vX, double vY, double shipAngle, boolean weaponActive) {
    double weaponVelocity = Math.sqrt(vX * vX + vY * vY);
    List<Projectile> newProjectiles = new ArrayList<>();
    if (cooldownState == 0 && weaponActive) {
      for (int i = 0; i < numProjectiles; i++) {
        double angle = shipAngle + Math.random() * spread - spread / 2;
        newProjectiles.add(
            new Projectile(
                x,
                y,
                (muzzleVelocity + weaponVelocity) * Math.cos(angle),
                (muzzleVelocity + weaponVelocity) * Math.sin(angle),
                angle,
                projectileSize,
                playAreaWidth,
                playAreaHeight,
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
