package com.example.game.gamecode.Snake;

public interface Edible {
    /**
     * Set the status of this edible to is eaten
     */
    void setEaten();

    /**
     * Return if this edible is eaten
     * @return if this edible is eaten.
     */
    boolean isEaten();
}
