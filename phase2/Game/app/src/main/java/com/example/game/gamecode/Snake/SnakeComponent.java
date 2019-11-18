package com.example.game.gamecode.Snake;

import androidx.annotation.NonNull;

/** A generic part of the snake character. */
class SnakeComponent extends SnakeObject {
  /** The direction that this component is traveling. */
  Direction direction = Direction.UP;

  /**
   * The snake component that is following this snake component, null if this is the last component.
   */
  private SnakeComponent next;

  /**
   * Constructs a new snake component.
   *
   * @param x the initial x coordinate of this snake component
   * @param y the initial y coordinate of this snake component
   * @param size the side length of this snake component
   * @param shape the shape of this snake component
   */
  SnakeComponent(int x, int y, int size, SnakeShape shape) {
    super(x, y, size, shape, SnakeObjectType.SNAKE_COMPONENT);
  }

  /**
   * Constructs a new snake component.
   *
   * @param x the initial x coordinate of this snake component
   * @param y the initial y coordinate of this snake component
   * @param size the side length of this snake component
   * @param shape the shape of this snake component
   * @param type the type of this snake component
   */
  SnakeComponent(int x, int y, int size, SnakeShape shape, SnakeObjectType type) {
    super(x, y, size, shape, type);
  }

  /**
   * Set the direction of this snake component to direction.
   *
   * @param direction The direction this snake component is going.
   */
  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  /** Move this snake component and all the snake component following this snake component */
  public void move() {
    // Move the next snake component
    if (this.next != null) {
      this.next.move(this.x, this.y);
      this.next.setDirection(this.direction);
    }

    // Move this snake component.
    switch (direction) {
      case UP:
        y -= 1;
        break;
      case DOWN:
        y += 1;
        break;
      case LEFT:
        x -= 1;
        break;
      case RIGHT:
        x += 1;
        break;
    }
  }

  /** Move this snake component and all the snake component following this snake component */
  private void move(int x, int y) {
    // Move the next snake component
    if (this.next != null) {
      this.next.move(this.x, this.y);
      this.next.direction = this.direction;
    }

    // Move this component
    this.x = x;
    this.y = y;
  }

  /**
   * Add a snake component to the end of the snake and return the component added
   * @return the snake component that is just added
   */
  SnakeComponent addComponent() {
    SnakeComponent snakeComponent = this;
    while (snakeComponent.next != null) {
      snakeComponent = snakeComponent.next;
    }
    SnakeComponent added = null;
    switch (snakeComponent.direction) {
      case UP:
        added = new SnakeComponent(snakeComponent.x, snakeComponent.y + 1, this.size, getShape());
        break;
      case DOWN:
        added = new SnakeComponent(snakeComponent.x, snakeComponent.y - 1, this.size, getShape());
        break;
      case LEFT:
        added = new SnakeComponent(snakeComponent.x + 1, snakeComponent.y, this.size, getShape());
        break;
      case RIGHT:
        added = new SnakeComponent(snakeComponent.x - 1, snakeComponent.y, this.size, getShape());
        break;
    }
    snakeComponent.next = added;
    added.setColor(snakeComponent.getColor());
    return added;
  }

  /**
   * Return the string representation of this snake object
   * @return the type, coordinate, color, shape, and direction separated by |.
   */
  @NonNull
  @Override
  public String toString() {
    return super.toString() + "|" + this.direction;
  }
}
