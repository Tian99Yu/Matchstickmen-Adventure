package com.example.game.gamecode.MatchstickMen;

class RegularManObject extends MatchstickMenObject {
    /**
     * Constructs a new MatchstickMen object.
     *
     * @param x          the initial x coordinate of this MatchstickMen object
     * @param y          the initial y coordinate of this MatchstickMen object
     * @param gridWidth the width of the displaying area
     * @param gridHeight the height of the displaying area
     */
    RegularManObject(int x, int y, int gridWidth, int gridHeight, MatchstickMenType manType, int sourceId) {
        super(x, y, gridWidth, gridHeight, manType, sourceId);

    }

    /**
     * Move this MatchstickMenObject.
     */
    @Override
    void move() {
//        if (this.x < super.getGridWidth()){
//            x += 1;
//        }else{
//            x = 0;
//        }

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
