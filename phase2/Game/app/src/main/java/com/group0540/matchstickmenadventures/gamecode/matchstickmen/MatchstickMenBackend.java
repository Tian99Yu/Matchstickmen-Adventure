package com.group0540.matchstickmenadventures.gamecode.matchstickmen;

import com.group0540.matchstickmenadventures.gamecode.GameBackend;

import java.util.ArrayList;
import java.util.Random;

public class MatchstickMenBackend extends GameBackend<MatchstickMenObject> {

  /** Actual number of matchstick men generated. */
  private int answer;

  /** The time of this game. */
  private int totalTime;

  /** Count from user. (1st statistic) */
  private int count;

  private int countP2;

  /** the level of the current game */
  private int levelNum;

  /** The score of the user. (2nd statistic) */
  int score;

  /** The time the user used, in secs. (3rd statistic) */
  private int timeUsed;

  /** A flag showing if the gameBackend is over. */
  private boolean over;

  /** The width of the displaying area. */
  private int gridWidth;

  /** The height of displaying area. */
  private int gridHeight;

  public int getCountP2() {
    return countP2;
  }

  public void setCountP2(int countP2) {
    this.countP2 = countP2;
  }

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

  public void addCount2() {
    this.countP2 += 1;
  }

  public void minusCount2() {
    this.countP2 -= 1;
  }

  /** Sets the answer. */
  public void setAnswer() {
    //    this.answer = answer;
    this.answer = getGameObjects().size();
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
    this.timeUsed = this.totalTime - Integer.parseInt(timeUsed);
  }

  /** Constructor for MatchStickMenBackend. */
  MatchstickMenBackend() {
    this.answer = 0;
    this.over = false;
    gameObjects = new ArrayList<>();
  }

  /**
   * Set the grid height to grid height
   *
   * @param gridHeight the grid height of this backend.
   */
  public void setGridHeight(int gridHeight) {
    this.gridHeight = gridHeight;
  }

  /**
   * Set the grid width to the grid width
   *
   * @param gridWidth the grid width of this backend.
   */
  public void setGridWidth(int gridWidth) {
    this.gridWidth = gridWidth;
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

  public String compare(String r1, String r2) {
    String strAnswer = Integer.toString(getAnswer());
    if (r1.equals(strAnswer) && r2.equals(strAnswer)) {
      return "You both win";
    } else if (r1.equals(strAnswer) && !(r2.equals(strAnswer))) {
      return "Player1 wins";
    } else if (!r1.equals(strAnswer) && r2.equals(strAnswer)) {
      return "Player2 wins";
    } else {
      return "You both lose";
    }
  }

  /** Update and refresh the game status. */
  @Override
  public void update() {
    //      for
    //      move();
    for (MatchstickMenObject matchstickMenObject : gameObjects) {
      if (matchstickMenObject != null) {
        matchstickMenObject.move();
      }
    }
  }

  /** Initialize and create all the objects when stating the game */
  void createObjects() {
    Random random = new Random();
    int range = random.nextInt((int) gridHeight * gridWidth);

      int i = 0;
      int sum = 0;
      while (i < range) {
          int regularX = random.nextInt(gridWidth - 200);
          int regularY = random.nextInt(gridHeight - 200);
          MatchstickMenObject regularMan = new RegularManObject(regularX, regularY, gridWidth, gridHeight);
//          if (character == MatchstickMenType.HAPPY_MAN) {
//              regularMan =

//          } else if (this.character == MatchstickMenType.EXCITED_MAN) {
//              regularMan = new RegularManObject(
//                      regularX, regularY, gridWidth, gridHeight, MatchstickMenType.EXCITED_MAN, R.drawable.excitedman);
//          }

      //          happyMan.setManType(MatchstickMenType.HAPPY_MAN);
      //          regularMan.setSourceId();
      // should i set mantype here also or set in the initializer??????????

          addMatchstickMenObj(regularMan);

          // TODO: add excited man, make customizations

          int baldX = random.nextInt(gridWidth - 200);
          int baldY = random.nextInt(gridHeight - 200);
          MatchstickMenObject baldMan = new ExtraordinaryManObject(baldX, baldY, gridWidth, gridHeight);
//          baldMan.setManType(MatchstickMenType.BALD_MAN);
//          baldMan.setSourceId(R.drawable.baldman);
          addMatchstickMenObj(baldMan);

      int increment = random.nextInt(range - 1) + 1;
      i += increment;
    }
      setAnswer();
  }

  public void setLevelNum(int levelNum) {
    this.levelNum = levelNum;
  }

  public int getLevelNum() {
    return levelNum;
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
