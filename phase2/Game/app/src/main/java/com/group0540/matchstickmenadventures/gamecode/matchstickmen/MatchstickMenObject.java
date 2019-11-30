package com.group0540.matchstickmenadventures.gamecode.matchstickmen;

public abstract class MatchstickMenObject {
  /** The x-coordinate of this MatchstickMenObject */
  public int x;
  /** The y-coordinate of this matchstick men object */
  public int y;
  /** The character that will appear on the screen. */
  private MatchstickMenType manType;
  /** Indicates whether this MatchstickMenObject is moving right. */
  private boolean goingRight;

  /** The width of displaying area. */
  private int gridWidth;

  /** The height of displaying area. */
  private int gridHeight;

    /**
     * Constructs a new MatchstickMen object.
     *
     * @param x the initial x coordinate of this MatchstickMen object
     * @param y the initial y coordinate of this MatchstickMen object
     */
    MatchstickMenObject(int x, int y, int gridWidth, int gridHeight) {
        this.x = x;
        this.y = y;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.manType = MatchstickMenType.EXCITED_MAN;
        this.goingRight = true;
    }

  /**
   * Gets the width of the displaying area.
   *
   * @return the gridWidth
   */
  int getGridWidth() {
    return gridWidth;
  }

  /**
   * Gets the height of the displaying area.
   *
   * @return the gridHeight
   */
  int getGridHeight() {
    return gridHeight;
  }

  /**
   * Gets the type of this MatchstickMenObject.
   *
   * @return the type of this MatchstickMenObject
   */
  MatchstickMenType getManType() {
    return manType;
  }

  /** Turns this MatchstickMenObject around, causing it to reverse direction. */
  void turnAround() {
    goingRight = !goingRight;
  }

  boolean isGoingRight() {
    return goingRight;
  }

  /** Move this MatchstickMenObject. */
  abstract void move();
}
