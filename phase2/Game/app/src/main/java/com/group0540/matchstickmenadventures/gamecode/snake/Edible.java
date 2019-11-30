package com.group0540.matchstickmenadventures.gamecode.snake;

public interface Edible {
  /** Set the status of this edible to is eaten */
  void setEaten();

  /**
   * Return if this edible is eaten
   *
   * @return if this edible is eaten.
   */
  boolean isEaten();
}
