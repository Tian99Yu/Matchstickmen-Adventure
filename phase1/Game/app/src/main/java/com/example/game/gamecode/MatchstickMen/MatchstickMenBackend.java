package com.example.game.gamecode.MatchstickMen;

import com.example.game.gamecode.GameBackend;
import com.example.game.gamecode.GameObject;
import com.example.game.leaderboardcode.LeaderboardManager;

import java.util.ArrayList;

public class MatchstickMenBackend extends GameBackend {

  /** Actual number of matchstick men generated. */
  private int answer;
  private int color, level, totalTime;
  private String character;

  /** Count from user. (1st statistic) */
  private int count;

  /** The score of the user. (2nd statistic) */
  int score;

  /** The time the user used, in secs. (3rd statistic) */
  int timeUsed;

  private LeaderboardManager lm;

  /** A flag showing if the gameBackend is over. */
  private boolean over;

  public int getCount() {
    return count;
  }

  public void addCount() {
    this.count += 1;
  }

  public void minusCount() {
    this.count -= 1;
  }
  public void setAnswer(int answer){
    this.answer = answer;
  }

  public int getAnswer() {
    return answer;
  }

  void setOver(boolean over) {
    this.over = over;
  }

  public void setTimeUsed(String timeUsed) {
    this.timeUsed = totalTime - Integer.parseInt(timeUsed);
  }

  public ArrayList<GameObject> getGameObjects() {
    return gameObjects;
  }

  private void addMatchstickMenObj(MatchstickMenObject m) {
    this.gameObjects.add(m);
  }

  public int getTimeUsed() {
    return timeUsed;
  }

  public MatchstickMenBackend() {
    this.answer = 0;
    this.over = false;
    gameObjects = new ArrayList<>();
  }

  // Check if it's the right answer
  public boolean compare(String r) {
    return r.equals(Integer.toString(getAnswer()));
  }

  public void deleteItem(MatchstickMenObject m) {
    gameObjects.remove(m);
  }

  @Override
  public void update() {
  }


  void createObjects() {
  gameObjects.add(new MatchstickMenObject(1,1, this, color, character));
  }

  public void inject(int color, int level, String character){
    this.color = color;
    this.level = level;
    this.character = character;
    switch (level){
      case 0:
        totalTime = 10;
        break;
      case 1:
        totalTime = 7;
        break;
      case 2:
        totalTime = 5;
        break;
      default:
        totalTime = 10;
    }
  }


  @Override
  public boolean isGameOver() {
    return over;
  }

  @Override
  public int getCurrentScore() {
    return score;
  }
}