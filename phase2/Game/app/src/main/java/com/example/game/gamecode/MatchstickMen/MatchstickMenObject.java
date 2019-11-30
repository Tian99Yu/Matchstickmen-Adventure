package com.example.game.gamecode.MatchstickMen;


public abstract class MatchstickMenObject {
    /**
     * The x-coordinate of this MatchstickMenObjectv
     */
    public int x;

  /** The y-coordinate of this matchstick men object */
  public int y;

  /** The character that will appear on the screen. */
  private MatchstickMenType manType;
    /**
     * Indicates whether this MatchstickMenObject is moving right.
     * */
    private boolean goingRight;

    /**
     * The width of displaying area.
     */
    private int gridWidth;

    /**
     * The height of displaying area.
     */
    private int gridHeight;

    /**
     * Constructs a new MatchstickMen object.
     *
     * @param x the initial x coordinate of this MatchstickMen object
     * @param y the initial y coordinate of this MatchstickMen object
     */
    MatchstickMenObject(int x, int y, int gridWidth, int gridHeight, MatchstickMenType manType) {
        this.x = x;
        this.y = y;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.manType = manType;
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
  public MatchstickMenType getManType() {
    return manType;
  }

  /**
   * Sets the type of this MatchstickMenObject.
   *
   * @param manType the type of this MatchstickMenObject
   */
  public void setManType(MatchstickMenType manType) {
    this.manType = manType;
  }

    /**
     * Turns this MatchstickMenObject around, causing it to reverse direction.
     */
    void turnAround() {
        goingRight = !goingRight;
    }

    boolean isGoingRight() {
        return goingRight;
    }

    /**
     * Move this MatchstickMenObject.
     */
    abstract void move();
}
