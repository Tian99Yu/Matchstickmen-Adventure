package com.example.game.gamecode.MatchstickMen;

import android.graphics.Color;

/** A class storing the customization of the matchstick men game. */
public class MatchstickMenCustomization {
  /** The difficulty of the game */
  private int difficulty = 1;

  /** The theme of the game */
  private int theme = Color.WHITE;

  /** The shape of the character of the game */
  private MatchstickMenType character = MatchstickMenType.HAPPY_MAN;

  /** Create an empty snake customization */
  MatchstickMenCustomization() {}

  /**
   * Update the level of this game to difficulty
   *
   * @param difficulty the difficulty of this game, can be easy, medium, or hard.
   */
  public void setDifficulty(String difficulty) {
    if (difficulty.equals("easy")) {
      this.difficulty = 0;
    } else if (difficulty.equals("medium")) {
      this.difficulty = 1;
    } else {
      this.difficulty = 2;
    }
  }

  /**
   * Set the shape of the character of this game
   *
   * @param character the character of this game, can be one or two
   */
  public void setCharacter(String character) {
    if (character.equals("one")) {
      this.character = MatchstickMenType.HAPPY_MAN;
    } else {
      this.character = MatchstickMenType.EXCITED_MAN;
    }
  }

  /**
   * Set the theme of this game.
   *
   * @param theme the theme of this game.
   */
  public void setTheme(String theme) {
    if (theme.equals("dark")) {
      this.theme = Color.BLACK;
    } else {
      this.theme = Color.DKGRAY;
    }
  }

  /**
   * Return the difficulty of this game
   *
   * @return The difficulty of this game
   */
  public int getDifficulty() {
    return difficulty;
  }

  /**
   * Set the color theme of this game
   *
   * @return the color theme of this game
   */
  public int getTheme() {
    return theme;
  }

  /**
   * Set the shape of the character of this game
   *
   * @return the character of this game
   */
  public MatchstickMenType getCharacter() {
    return character;
  }
}
