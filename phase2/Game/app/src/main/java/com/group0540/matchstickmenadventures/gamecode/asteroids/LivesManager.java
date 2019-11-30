package com.group0540.matchstickmenadventures.gamecode.asteroids;

public interface LivesManager {
  /**
   * Increments the number of lives the player has.
   */
  void incrementLives();

  /**
   * Decrements the number of lives the player has.
   */
  void decrementLives();
}
