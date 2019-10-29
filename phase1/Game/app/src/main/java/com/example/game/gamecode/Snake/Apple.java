package com.example.game.gamecode.Snake;

/** An apple in Snake. */
public class Apple extends SnakeObject {
  /** Indicates if this apple is been eaten. */
  private boolean isEaten;

  /**
   * Constructs a new apple.
   *
   * @param x the initial x coordinate of this snake object
   * @param y the initial y coordinate of this snake object
   * @param appearance the appearance of this snake object
   * @param size the side length of this snake object
   */
  Apple(int x, int y, String appearance, int size) {
    super(x, y, appearance, size);
    this.isEaten = false;
  }

  /** update the status of this apple, deleting it if it is eaten. */
  void updateApple() {
    if (isEaten) {
      // Add code deleting the apple.
    }
  }

  /**
   * Return whether this apple is eaten
   *
   * @return true if this apple is eaten, false otherwise.
   */
  public boolean isEaten() {
    return isEaten;
  }

  /**
   * Set if this apple is eaten.
   *
   * @param eaten whether this apple is eaten.
   */
  public void setIsEaten(boolean eaten) {
    isEaten = eaten;
  }
}
