package com.example.game.gamecode.MatchstickMen;

import android.graphics.Canvas;

import com.example.game.R;
import com.example.game.gamecode.GameBackend;

import java.util.ArrayList;
import java.util.Random;

public class MatchstickMenBackend extends GameBackend<MatchstickMenObject> {

  /** Actual number of matchstick men generated. */
  private int answer;

//  /** The color of the character. */
//  private int color;
//
//  /** The level of this game. */
//  private int difficulty;
//
//  /** The character that will appear on the screen. */
//  private String character;

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
  int timeUsed;

  /** A flag showing if the gameBackend is over. */
  private boolean over;

    /**
     * The width of matchstick men.
     */
    private int gridWidth;

    /**
     * The height of matchstick men.
     */
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

  public void addCount2(){
      this.countP2 += 1;
  }

  public void minusCount2(){
      this.countP2 -= 1;
  }

  /**
   * Sets the answer.
   */
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
    this.timeUsed = totalTime - Integer.parseInt(timeUsed);
  }

  /** Constructor for MatchStickMenBackend. */
  MatchstickMenBackend() {
      this.answer = 0;
      this.over = false;
      gameObjects = new ArrayList<>();
  }

    /**
     * Another constructor for MatchstickMenBackend.
     *
     * @param height the height of the drawing surface
     * @param width  the width of the drawing surface
     */
    MatchstickMenBackend(int height, int width) {
        this.answer = 0;
        this.over = false;
        gameObjects = new ArrayList<>();
        this.gridWidth = width;
        this.gridHeight = height;
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

  public String compare(String r1, String r2){
      String strAnswer = Integer.toString(getAnswer());
      if (r1.equals(strAnswer) && r2.equals(strAnswer)){
          return "you both win";
      }
      else if (r1.equals(strAnswer) && !(r2.equals(strAnswer))){
          return "player1 wins";
      }else if (!r1.equals(strAnswer) && r2.equals(strAnswer) ){
          return "player2 wins";
      } else {
          return "you both lose";
      }


  }

    /**
     * Update and refresh the game status.
     */
  @Override
  public void update() {}

  /** Initialize and create all the objects when stating the game */
  void createObjects() {
      Random random = new Random();
      int range = random.nextInt((int) gridHeight * gridWidth);

      int i = 0;
      int sum = 0;
      while (i < range) {
          int happyX = random.nextInt(gridWidth - 200);
          int happyY = random.nextInt(gridHeight - 200);
          MatchstickMenObject happyMan = new MatchstickMenObject(happyX, happyY, MatchstickMenType.HAPPY_MAN);
          happyMan.setSourceId(R.drawable.happyman);
          //should i set mantype here also or set in the initializer??????????
          addMatchstickMenObj(happyMan);
          // TODO: add excited man, make customizations

          int baldX = random.nextInt(gridWidth - 200);
          int baldY = random.nextInt(gridHeight - 200);
          MatchstickMenObject baldMan = new MatchstickMenObject(baldX, baldY, MatchstickMenType.BALD_MAN);
          baldMan.setSourceId(R.drawable.baldman);
          addMatchstickMenObj(baldMan);

          int increment = random.nextInt(range - 1) + 1;
          i += increment;
      }
  }

//  public void inject(int color, int level, String character) {
//    this.color = color;
//    this.difficulty = level;
//    this.character = character;
//    switch (level) {
//      case 0:
//        totalTime = 30;
//        break;
//      case 1:
//        totalTime = 20;
//        break;
//      case 2:
//        totalTime = 10;
//        break;
//      default:
//        totalTime = 10;
//    }
//  }


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
