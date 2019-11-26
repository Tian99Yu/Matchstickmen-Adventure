package com.example.game.gamecode.MatchstickMen;

import com.example.game.gamecode.GameBackend;

import java.util.ArrayList;

public class MatchstickMenBackend extends GameBackend<MatchstickMenObject> {

  /** Actual number of matchstick men generated. */
  private int answer;

  /** The color of the character. */
  private int color;

  /** The level of this game. */
  private int level;

  /** The character that will appear on the screen. */
  private String character;

  /** The time of this game. */
  private int totalTime;

  /** Count from user. (1st statistic) */
  private int count;

  /** The score of the user. (2nd statistic) */
  int score;

  /** The time the user used, in secs. (3rd statistic) */
  int timeUsed;

  /** A flag showing if the gameBackend is over. */
  private boolean over;

  /**
   * Gets the count of this game.
   *
   * @return the count of this game.
   */
  public int getCount() {
    return count;
  }

  /** Increments the count. */
  public void addCount() {
    this.count += 1;
  }

  /** Decreases the count by 1. */
  public void minusCount() {
    this.count -= 1;
  }

  /**
   * Sets the answer.
   *
   * @param answer the answer of this game
   */
  public void setAnswer(int answer) {
    this.answer = answer;
  }

  /**
   * Gets the answer.
   *
   * @return the answer
   */
  public int getAnswer() {
    return answer;
  }

  /**
   * Sets the over.
   *
   * @param over a flag indicating whether this game is over
   */
  void setOver(boolean over) {
    this.over = over;
  }

  /**
   * Gets the game objects.
   *
   * @return an arraylist of game objects
   */
  public ArrayList<MatchstickMenObject> getGameObjects() {
    return gameObjects;
  }

  /**
   * Adds one MatchstickObject to the gameObjects.
   *
   * @param m the MatchstickMen object
   */
  private void addMatchstickMenObj(MatchstickMenObject m) {
    this.gameObjects.add(m);
  }

  /**
   * Gets the time used by the user.
   *
   * @return the time used by the user
   */
  public int getTimeUsed() {
    return timeUsed;
  }

  /**
   * Sets the time used by the user.
   *
   * @param timeUsed the time used by the user
   */
  public void setTimeUsed(String timeUsed) {
    this.timeUsed = totalTime - Integer.parseInt(timeUsed);
  }

  /** Constructor for MatchStickMenBackend. */
  MatchstickMenBackend() {
    this.answer = 0;
    this.over = false;
    gameObjects = new ArrayList<>();
  }

  /**
   * Compare the input string to the answer of this game.
   *
   * @param r the string that will be compared to answer of this game
   * @return the boolean result of the comparison
   */
  public boolean compare(String r) {
    return r.equals(Integer.toString(getAnswer()));
  }

  /** Update and refresh the game status. */
  @Override
  public void update() {}

  /** Initialize and create all the objects when stating the game */
  void createObjects() {
      gameObjects.add(new MatchstickMenObject(1, 1, MatchstickMenType.CIRCLE));
  }
    //TODO: chage the last parameter

  public void inject(int color, int level, String character) {
    this.color = color;
    this.level = level;
    this.character = character;
    switch (level) {
      case 0:
        totalTime = 30;
        break;
      case 1:
        totalTime = 20;
        break;
      case 2:
        totalTime = 10;
        break;
      default:
        totalTime = 10;
    }
  }

  /**
   * Gets the over.
   *
   * @return over
   */
  @Override
  public boolean isGameOver() {
    return over;
  }

  /**
   * Gets the current score.
   *
   * @return the score
   */
  @Override
  public int getCurrentScore() {
    return score;
  }

}
