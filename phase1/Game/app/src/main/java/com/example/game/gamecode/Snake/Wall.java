package com.example.game.gamecode.Snake;

/** A wall in Snake. */
class Wall extends SnakeObject {
  /**
   * Constructs a new wall.
   *
   * @param x the initial x coordinate of this snake object
   * @param y the initial y coordinate of this snake object
   * @param size the side length of this snake object
   */
  Wall(int x, int y, int size) {
    super(x, y, size);
  }
}
