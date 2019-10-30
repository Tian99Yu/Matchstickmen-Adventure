package com.example.game.gamecode.Snake;

/** An apple in Snake. */
class Apple extends SnakeObject {
  /** Indicates if this apple is been eaten. */
  private boolean isEaten;

  /**
   * Constructs a new apple.
   *
   * @param x the initial x coordinate of this snake object
   * @param y the initial y coordinate of this snake object
   * @param size the side length of this snake object
   */
  Apple(int x, int y, int size) {
    super(x, y, size);
    this.isEaten = false;
  }

  /**
   * Return whether this apple is eaten
   *
   * @return true if this apple is eaten, false otherwise.
   */
  boolean isEaten() {
    return isEaten;
  }

  /**
   * Set if this apple is eaten.
   *
   * @param eaten whether this apple is eaten.
   */
  void setIsEaten(boolean eaten) {
    isEaten = eaten;
  }
}
