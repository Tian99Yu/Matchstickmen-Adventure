package com.example.game.gamecode.Snake;

import java.util.ArrayList;

public class SnakeHead extends SnakeComponent {
  /** whether this snake is alive/ whether the game is still running */
  public boolean isDead = false;

  /**
   * Constructs a new snake head.
   *
   * @param x the initial x coordinate of this snake object
   * @param y the initial y coordinate of this snake object
   * @param appearance the appearance of this snake object
   * @param size the side length of this snake object
   */
  SnakeHead(int x, int y, String appearance, int size) {
    super(x, y, appearance, size);
  }

  /** turn this snake left. */
  void turn(TurnDirection turnDirection) {
    Direction[] directions = Direction.values();
    for (int i = 0; i < directions.length; i++) {
      if (this.direction == directions[i]) {
        if (turnDirection.equals(TurnDirection.LEFT)) {
          if (i > 0) {
            this.direction = directions[i - 1];
          } else {
            this.direction = directions[directions.length - 1];
          }
        } else {
          if (i < directions.length - 1) {
            this.direction = directions[i + 1];
          } else {
            this.direction = directions[0];
          }
        }
      }
    }
  }

  boolean atPosition(int x, int y){
    return (this.x == x && this.y == y);
  }

  void setDead(boolean dead){
    this.isDead = dead;
  }

  @Override
  public void move() {
    super.move();
    // Decide if there is an apple or wall or other snake component on the same position.
  }
}
