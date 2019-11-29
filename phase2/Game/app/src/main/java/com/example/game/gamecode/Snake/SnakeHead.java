package com.example.game.gamecode.Snake;

import java.util.HashMap;

public class SnakeHead extends SnakeComponent {
  /** whether this snake is alive/ whether the gameBackend is still running */
  private boolean isDead = false;


  private HashMap<Direction, DirectionChanger> directionAllocator;

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
    setType(SnakeObjectType.SNAKE_HEAD);
    createDirectionAllocator();
  }

  /** turn this snake left. */
  void turn(TurnDirection turnDirection) {
    DirectionChanger directionChanger = directionAllocator.get(this.direction);
    if (directionChanger != null) {
      this.direction = directionChanger.getDirection(turnDirection);
    }
  }

  /**
   * Return if this snake head is at the position
   *
   * @param x the x coordinate of the position
   * @param y the y coordinate of the position
   * @return true if this snake head is at the position, false otherwise
   */
  boolean atPosition(int x, int y) {
    return (this.x == x && this.y == y);
  }

  /** Mark this snake is dead */
  void setDead() {
    this.isDead = true;
  }

  /**
   * Generate and store direction changers to this.directionChangers.
   */
  private void createDirectionAllocator() {
    HashMap<Direction, DirectionChanger> directionAllocator = new HashMap<>();
    directionAllocator.put(Direction.UP, new DirectionChanger(Direction.LEFT, Direction.RIGHT));
    directionAllocator.put(Direction.DOWN, new DirectionChanger(Direction.RIGHT, Direction.LEFT));
    directionAllocator.put(Direction.LEFT, new DirectionChanger(Direction.DOWN, Direction.UP));
    directionAllocator.put(Direction.RIGHT, new DirectionChanger(Direction.UP, Direction.DOWN));
    this.directionAllocator = directionAllocator;
  }
}
