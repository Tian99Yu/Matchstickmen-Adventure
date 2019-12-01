package com.group0540.matchstickmenadventures.gamecode.matchstickmen;

class RegularManObject extends MatchstickMenObject {
    /**
     * Constructs a new MatchstickMen object.
     *
     * @param x          the initial x coordinate of this MatchstickMen object
     * @param y          the initial y coordinate of this MatchstickMen object
     * @param gridWidth the width of the displaying area
     * @param gridHeight the height of the displaying area
     */
    RegularManObject(int x, int y, int gridWidth, int gridHeight) {
        super(x, y, gridWidth, gridHeight);
    }

    /**
     * Move this RegularMenObject. The RegularManObject only moves horizontally.
     */
    @Override
    void move() {
        if (this.isGoingRight()) {
            if (x >= super.getGridWidth()) {
                turnAround();
            } else {
                x += 1;
            }
        } else {
            if (x <= 0) {
                turnAround();
            } else {
                x -= 1;
            }
        }
    }
}
