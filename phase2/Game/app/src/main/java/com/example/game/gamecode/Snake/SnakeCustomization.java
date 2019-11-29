package com.example.game.gamecode.Snake;

import android.graphics.Color;

/**
 * A class storing the customization of the snake game.
 */
public class SnakeCustomization {
    /**
     * The difficulty of the game
     */
    private int difficulty = 5;

    /**
     * The theme of the game
     */
    private int theme = Color.WHITE;

    /**
     * The shape of the character of the game
     */
    private SnakeShape character = SnakeShape.SQUARE;

    /**
     * Create an empty snake customization
     */
    SnakeCustomization() {}

    /**
     * Update the level of this game to difficulty
     *
     * @param difficulty the difficulty of this game, can be easy, medium, or hard.
     */
    public void setDifficulty(String difficulty) {
        if (difficulty.equals("easy")) {
            this.difficulty = 3;
        } else if (difficulty.equals("medium")) {
            this.difficulty = 5;
        } else {
            this.difficulty = 10;
        }
    }

    /**
     * Set the shape of the character of this game
     *
     * @param character the character of this game, can be one or two
     */
    public void setCharacter(String character) {
        if (character.equals("one")) {
            this.character = SnakeShape.CIRCLE;
        } else {
            this.character = SnakeShape.SQUARE;
        }
    }

    /**
     * Set the theme of this game.
     *
     * @param theme the theme of this game.
     */
    public void setTheme(String theme) {
        if (theme.equals("dark")) {
            this.theme = Color.DKGRAY;
        } else {
            this.theme = Color.WHITE;
        }
    }

    /**
     * Return the difficulty of this game
     *
     * @return The difficulty of this game, can be easy, medium, or hard.
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Set the color theme of this game
     *
     * @return  the color theme of this game
     */
    public int getTheme() {
        return theme;
    }

    /**
     * Set the shape of the character of this game
     *
     * @return the character of this game, can be one or two
     */
    public SnakeShape getCharacter() {
        return character;
    }
}
