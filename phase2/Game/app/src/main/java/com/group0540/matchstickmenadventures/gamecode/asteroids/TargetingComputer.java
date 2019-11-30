package com.group0540.matchstickmenadventures.gamecode.asteroids;

class TargetingComputer {
  /** The weapon this computer is calibrated for. */
  private WeaponSystem weaponSystem;

  TargetingComputer(WeaponSystem weaponSystem) {
    this.weaponSystem = weaponSystem;
  }

  void setWeaponSystem(WeaponSystem weaponSystem) {
    this.weaponSystem = weaponSystem;
  }

  /**
   * Returns the angle to fire at to hit the target. Does not take into account the velocity of firing
   * platform.
   *
   * @param x the x coordinate of firing platform.
   * @param y the y coordinate of firing platform.
   * @param target the target to try and hit.
   * @return the angle to fire to hit the target.
   */
  double  getLeadAngle(double x, double y, AsteroidGameObject target) {
    double testTime = 0.5;
    double timeStride = 0.1;
    int sign = 1;
    double prevAbsTime = Double.POSITIVE_INFINITY;
    double newTargetX, newTargetY;
    do {
      newTargetX = target.x + testTime * target.vX;
      newTargetY = target.y + testTime * target.vY;
      double distance =
          Math.sqrt((x - newTargetX) * (x - newTargetX) + (y - newTargetY) * (y - newTargetY));
      double absTimeDelta = Math.abs(testTime - distance/weaponSystem.muzzleVelocity);
      testTime += sign * timeStride;
      if (prevAbsTime < absTimeDelta) {
        sign *= -1;
        timeStride /= 2;
      }
      prevAbsTime = absTimeDelta;
    } while (timeStride > 1.0/60.0);
    return AngleUtils.normalize(Math.atan2(newTargetY - y, newTargetX - x));
  }
}
