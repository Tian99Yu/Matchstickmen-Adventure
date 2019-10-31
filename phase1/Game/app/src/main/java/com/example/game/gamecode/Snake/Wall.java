package com.example.game.gamecode.Snake;

/** A wall in Snake. */
class Wall extends SnakeObject {
  /**
   * Constructs a new wall.
   *
   * @param x the initial x coordinate of this wall
   * @param y the initial y coordinate of this wall
   * @param size the side length of this wall
   * @param shape the shape of this wall
   */
  Wall(int x, int y, int size, SnakeShape shape) {
    super(x, y, size, shape);
  }
}
