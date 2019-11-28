package com.example.game.gamecode.Snake;

import java.util.Random;

public class MysteryObject extends SnakeObject implements Edible {
    /**
     * If this mystery object has been eaten.
     */
    private boolean isEaten = false;

    /**
     * Constructs a new mystery object.
     *
     * @param x the initial x coordinate of this mystery object
     * @param y the initial y coordinate of this mystery object
     * @param size the side length of this mystery object
     * @param shape the shape of this mystery object
     */
    MysteryObject(int x, int y, int size, SnakeShape shape) {
        super(x, y, size, shape);
        setType(SnakeObjectType.MYSTERY_OBJECT);
    }

    /** Move this mystery object */
    void move() {
        Random random = new Random();
        //The mystery object will move randomly in both x and y direction.
        int randomInt = random.nextInt(3);
        this.x += randomInt - 1;
        randomInt = random.nextInt(3);
        this.y += randomInt - 1;
    }

    /**
     * Set the status of this mystery object to is eaten
     */
    public void setEaten() {
        isEaten = true;
    }

    /**
     * Return if this mystery object is eaten
     * @return if this mystery object is eaten.
     */
    public boolean isEaten() {
        return isEaten;
    }
}
