package com.example.game.gamecode.Snake;

import java.util.ArrayList;

public class SnakeHead extends SnakeComponent {
  /** whether this snake is alive/ whether the game is still running */
  private boolean isDead = false;

  /**
   * Constructs a new snake head.
   *
   * @param x the initial x coordinate of this snake object
   * @param y the initial y coordinate of this snake object
   * @param size the side length of this snake object
   */
  SnakeHead(int x, int y, int size) {
    super(x, y, size);
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
