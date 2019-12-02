package com.group0540.matchstickmenadventures.gamecode.asteroids;

import java.util.List;

class Ship extends AsteroidGameObject {
  /** Fixed amount of acceleration ship has when thruster is on. */
  private double thrust;
  /** Fixed rate ship can turn. */
  private double turnRate;
  /** Maximum velocity ship can travel. */
  private double maxVelocity;
  /** Whether or not the thruster is on. */
  private boolean thrusterActive = false;
  /** Whether or not the main weapon is firing. */
  private boolean weaponActive = false;
  /** The weapon this ship is using. */
  private WeaponSystem<Projectile> mainArmament;
  /** The weapon this ship uses when it collects a weapon powerup. */
  private WeaponSystem<Projectile> secondaryArmament;
  /** Angle player wants ship to face. */
  private double targetAngle;
  /** Starting position and angle of ship. */
  private double startX, startY, startAngle;
  /** Whether or not this ship is destroyed. */
  private boolean destroyed;
  /** Spawn protection available. */
  private static final int spawnProtectionTime = 150;
  /** Spawn protection left. */
  private int spawnProtectionLeft = spawnProtectionTime;
  /** Powerup time left. */
  private int powerupTime = 0;

  Ship(
      double x,
      double y,
      double vX,
      double vY,
      double angle,
      double collisionRadius,
      double thrust,
      double turnRate,
      double maxVelocity,
      WeaponSystem<Projectile> mainArmament,
      WeaponSystem<Projectile> secondaryArmament) {
    super(x, y, vX, vY, angle, collisionRadius);
    this.thrust = thrust;
    this.turnRate = turnRate;
    this.maxVelocity = maxVelocity;
    this.mainArmament = mainArmament;
    this.secondaryArmament = secondaryArmament;
    startX = x;
    startY = y;
    startAngle = angle;
    this.targetAngle = angle;
  }

  @Override
  void move() {
    turn(targetAngle, turnRate);
    if (thrusterActive) {
      accelerate(thrust, maxVelocity);
    }
    if (spawnProtectionLeft > 0) {
      spawnProtectionLeft--;
    }
    if (powerupTime > 0) {
      powerupTime--;
    }
    super.move();
  }

  @Override
  boolean isDestroyed() {
    return destroyed;
  }

  /**
   * Tries to fire the main weapon system. It will fire iff weaponActive.
   *
   * @return the projectiles that just got fired.
   */
  List<Projectile> attemptFireMainArmament() {
    if (powerupTime <= 0) {
      return mainArmament.attemptFire(x, y, vX, vY, getAngle(), weaponActive);
    } else {
      return secondaryArmament.attemptFire(x, y, vX, vY, getAngle(), weaponActive);
    }
  }

  void setThrusterActive(boolean thrusterActive) {
    this.thrusterActive = thrusterActive;
  }

  void setWeaponActive(boolean weaponActive) {
    this.weaponActive = weaponActive;
  }

  void setTargetAngle(double targetAngle) {
    this.targetAngle = AngleUtils.normalize(targetAngle);
  }

  /** Returns ship to starting location */
  void reset() {
    x = startX;
    y = startY;
    vX = 0;
    vY = 0;
    setAngle(startAngle);
    targetAngle = startAngle;
    destroyed = false;
    spawnProtectionLeft = spawnProtectionTime;
  }

  boolean hasSpawnProtection() {
    return spawnProtectionLeft > 0;
  }

  @Override
  void resolveCollision(AsteroidGameObject other) {
    if (spawnProtectionLeft == 0 && other instanceof Asteroid) {
      destroyed = true;
    }
  }

  void setPowerupTime(int powerupTime) {
    this.powerupTime = powerupTime;
  }
}
