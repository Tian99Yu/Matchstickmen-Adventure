package com.example.game.gamecode.Snake;

import java.util.ArrayList;

public class SnakeHead extends SnakeComponent {
  /** whether this snake is alive/ whether the game is still running */
  private boolean isDead = false;

  /**
   * Constructs a new snake head.
   *
   * @param x the initial x coordinate of this snake head
   * @param y the initial y coordinate of this snake head
   * @param size the side length of this snake head
   * @param shape the shape of this snake head
   */
  SnakeHead(int x, int y, int size, SnakeShape shape) {
    super(x, y, size, shape);
  }

  /** turn this snake left. */
  void turn(TurnDirection turnDirection) {
    switch (turnDirection) {
      case LEFT:
        switch (this.direction) {
          case LEFT:
            this.direction = Direction.DOWN;
            break;
          case UP:
            this.direction = Direction.LEFT;
            break;
          case DOWN:
            this.direction = Direction.RIGHT;
            break;
          case RIGHT:
            this.direction = Direction.UP;
        }
        break;
      case RIGHT:
        switch (this.direction) {
          case LEFT:
            this.direction = Direction.UP;
            break;
          case UP:
            this.direction = Direction.RIGHT;
            break;
          case DOWN:
            this.direction = Direction.LEFT;
            break;
          case RIGHT:
            this.direction = Direction.DOWN;
        }
        break;
    }
  }

  boolean atPosition(int x, int y) {
    return (this.x == x && this.y == y);
  }

  void setDead(boolean dead) {
    this.isDead = dead;
  }

  @Override
  public void move() {
    super.move();
  }
}
