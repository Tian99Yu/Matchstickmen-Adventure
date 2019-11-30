package com.group0540.matchstickmenadventures.gamecode.snake;

/** A generic object in Snake */
abstract class SnakeObject {
  /** The x-coordinate of this snake object */
  public int x;

  /** The y-coordinate of this snake object */
  public int y;

  /** The size of this snake object */
  int size;

  /** The shape of this snake object */
  private SnakeShape shape;

  /** The type of this snake object */
  private SnakeObjectType type;

  /**
   * Constructs a new snake object.
   *
   * @param x the initial x coordinate of this snake object
   * @param y the initial y coordinate of this snake object
   * @param size the side length of this snake object
   */
  SnakeObject(int x, int y, int size, SnakeShape shape) {
    this.x = x;
    this.y = y;
    this.size = size;
    this.shape = shape;
    this.type = null;
  }

  /**
   * Return the shape of this snake object
   *
   * @return the shape of this snake object
   */
  SnakeShape getShape() {
    return shape;
  }

  /**
   * Set the shape of this snake object
   *
   * @param shape the shape that this snake object will have.
   */
  void setShape(SnakeShape shape) {
    this.shape = shape;
  }

  /**
   * Return the type of this snake object
   *
   * @return the type of this snake object
   */
  SnakeObjectType getType() {
    return this.type;
  }

  /**
   * Set the type of this snake object to type
   *
   * @param type the type of the snake object
   */
  void setType(SnakeObjectType type) {
    this.type = type;
  }
}
