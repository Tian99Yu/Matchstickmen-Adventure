package com.group0540.matchstickmenadventures.gamecode.asteroids;

import android.graphics.Color;

public class AsteroidCustomizations {
  /** Asteroid splitting ratio. */
  static int splittingRatio = 3;
  /** Theme index. */
  static int themeIndex = 0;
  /** Projectile colors. */
  static final int[] projectileColor = {Color.BLUE, Color.RED};
  /** Background colors */
  static final int[] backgroundColor = {Color.BLACK, Color.rgb(10, 10, 100)};
  /** The weapon selected. */
  static int weaponOption = 0;

  private AsteroidCustomizations() {}
}
