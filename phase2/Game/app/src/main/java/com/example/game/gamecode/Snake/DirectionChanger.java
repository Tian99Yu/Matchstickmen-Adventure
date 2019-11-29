package com.example.game.gamecode.Snake;

public class DirectionChanger {
    /**
     * The direction to the left of the current direction.
     */
    private Direction leftwardDirection;
    /**
     * The direction to the right of the current direction.
     */
    private Direction rightwardDirection;


    /**
     * Constructor of direction changer
     * @param leftwardDirection the direction to the left of the current direction.
     * @param rightwardDirection the direction to the right of the current direction.
     */
    DirectionChanger(Direction leftwardDirection, Direction rightwardDirection) {
        this.leftwardDirection = leftwardDirection;
        this.rightwardDirection = rightwardDirection;
    }

    /**
     * Return the direction to the turnDirection direction of the current direction
     * @param turnDirection the direction that this object is turning to.
     * @return the direction to the turnDirection direction of the current direction
     */
    public Direction getDirection(TurnDirection turnDirection) {
        switch (turnDirection) {
            case LEFT:
                return leftwardDirection;
            case RIGHT:
                return rightwardDirection;
        }
        return null;
    }
}
