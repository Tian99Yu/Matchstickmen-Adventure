package com.example.game.gamecode.Asteroids;

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
