package com.example.game.gamecode.Asteroids;

import android.graphics.Color;

public class AsteroidCustomizations {
  /** Asteroid splitting ratio. */
  static int splittingRatio = 3;
  /** Theme index. */
  static int themeIndex = 0;
  /** Projectile colors. */
  static final int[] projectileColor = {Color.BLUE, Color.RED};
  /** Asteroid colors. */
  static final int[] asteroidColor = {Color.rgb(139, 69, 19), Color.LTGRAY};
  /** Ship colors. */
  static final int[] shipColor = {Color.rgb(192, 192, 192), Color.rgb(248, 248, 255)};
  /** Background colors */
  static final int[] backgroundColor = {Color.BLACK, Color.rgb(10,10,100)};
  /** The weapon selected. */
  static int weaponOption = 0;

  private AsteroidCustomizations() {}
}
