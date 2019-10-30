package com.example.game.gamecode.MatchstickMen;

import android.graphics.Canvas;

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

  /** The third statistic (will decide later). */
  private int gridwidth;

  private int gridheight;

  /** Width of the canvas used to display the matchstick men. */
  private int canvas_w;

  /** Height of the canvas used to display the matchstick men. */
  private int canvas_h;

  public int getGridwidth() {
    return gridwidth;
  }

  public int getGridheight() {
    return gridheight;
  }

  public int getCanvas_w() {
    return canvas_w;
  }

  public int getCanvas_h() {
    return canvas_h;
  }

  public int getCount() {
    return count;
  }

  public void addCount(){
      this.count += 1;
  }

  public void minusCount(){
      this.count -= 1;
  }

  public int getAnswer() {
    return answer;
  }

  public ArrayList<GameObject> getGameObjects() {
    return gameObjects;
  }

  private void addSnakeObj(MatchstickMenObject m) {
    this.gameObjects.add(m);
  }

  public MatchstickMenBackend(int h, int w) {
    this.canvas_w = w;
    this.canvas_h = h;
    Random random = new Random();
    this.answer = random.nextInt(canvas_h * canvas_w);
    gameObjects = new ArrayList<>();
  }

    void draw(Canvas canvas) {
        for (int a = 0; a < gameObjects.size(); a++) {
            if (gameObjects.get(a) != null) {
                gameObjects.get(a).draw(canvas);
            }
        }
    }

    //Check if it's the right answer
    public boolean compare(String r){
      return r.equals(Integer.toString(getAnswer()));
    }
    public void deleteItem(MatchstickMenObject m) {
        gameObjects.remove(m);
    }

  @Override
  public void update() {}
}
