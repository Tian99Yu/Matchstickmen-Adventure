//package com.example.gameBackend.gamecode.MatchstickMen;
//
//import android.graphics.Canvas;
//import android.graphics.Paint;
//
//import com.example.gameBackend.gamecode.GameBackend;
//import com.example.gameBackend.gamecode.GameObject;
//
//import java.util.ArrayList;
//import java.util.Random;
//
//public class MatchstickMenBackend extends GameBackend {
//
//  /** Actual number of matchstick men generated. */
//  private int answer;
//
//  /** Count from user. (1st statistic) */
//  private int count;
//
//  /** The score of the user. (2nd statistic) */
//  int score;
//
//  /** The third statistic (will decide later). */
//  private int gridwidth;
//
//  private int gridheight;
//
//  Random random = new Random();
//  private Paint paint = new Paint();
//
//  public int getGridwidth() {
//    return gridwidth;
//  }
//
//  public int getGridheight() {
//    return gridheight;
//  }
//
//  public int getCount() {
//    return count;
//  }
//
//  public void addCount(){
//      this.count += 1;
//  }
//
//  public void minusCount(){
//      this.count -= 1;
//  }
//
//  public int getAnswer() {
//    return answer;
//  }
//
//  public ArrayList<GameObject> getGameObjects() {
//    return gameObjects;
//  }
//
//  private void addSnakeObj(MatchstickMenObject m) {
//    this.gameObjects.add(m);
//  }
//
//  public MatchstickMenBackend() {
//
//    this.answer = 0;
//    gameObjects = new ArrayList<>();
//  }
//
//  void draw(Canvas canvas) {
//    int range = random.nextInt(canvas.getHeight() * canvas.getWidth());
//    int i = 0;
//    while (i < range) {
//      int x = random.nextInt();
//      int y = random.nextInt();
//      canvas.drawCircle(x, y, 1 / 2, paint);
//      int increment = random.nextInt(range);
//      i += increment;
//    }
//  }
//    //Check if it's the right answer
//    public boolean compare(String r){
//      return r.equals(Integer.toString(getAnswer()));
//    }
//    public void deleteItem(MatchstickMenObject m) {
//        gameObjects.remove(m);
//    }
//
//  @Override
//  public void update() {}
//}



package com.example.game.gamecode.MatchstickMen;

import android.graphics.Color;
import android.graphics.Paint;

import com.example.game.gamecode.GameBackend;
import com.example.game.gamecode.GameObject;

import java.util.ArrayList;
import java.util.Random;

public class MatchstickMenBackend extends GameBackend {

  /** Actual number of matchstick men generated. */
  private int answer;

  /** Count from user. (1st statistic) */
  private int count;

  /** The score of the user. (2nd statistic) */
  int score;

  /** The time the user used, in secs. (3rd statistic) */
  int timeUsed;

//  /** Width of the canvas used to display the matchstick men. */
//  private int canvas_w;
//
//  /** Height of the canvas used to display the matchstick men. */
//  private int canvas_h;

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
    this.timeUsed = 10 - Integer.parseInt(timeUsed);
  }

  public ArrayList<GameObject> getGameObjects() {
    return gameObjects;
  }

  private void addSnakeObj(MatchstickMenObject m) {
    this.gameObjects.add(m);
  }

  Random random = new Random();
  private Paint paint = new Paint(Color.WHITE);

  public MatchstickMenBackend() {
    this.answer = 0;
    this.over = false;
    gameObjects = new ArrayList<>();
  }

//  void draw(Canvas canvas) {
//    int range = random.nextInt(canvas.getHeight() * canvas.getWidth());
//    int i = 0;
//    while (i < range) {
//      int x = random.nextInt(canvas.getWidth());
//      int y = random.nextInt(canvas.getHeight());
//      canvas.drawCircle(x, y, 1 / 2, paint);
//      int increment = random.nextInt(range);
//      i += increment;
//    }
//  }

  // Check if it's the right answer
  public boolean compare(String r) {
    return r.equals(Integer.toString(getAnswer()));
  }

  public void deleteItem(MatchstickMenObject m) {
    gameObjects.remove(m);
  }

  @Override
  public void update() {
    setOver(true);
  }


  void createObjects() {
  gameObjects.add(new MatchstickMenObject(1,1, this));
  }


  @Override
  public boolean isGameOver() {
    return over;
  }
}