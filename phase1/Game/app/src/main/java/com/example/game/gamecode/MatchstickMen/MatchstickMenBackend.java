package com.example.game.gamecode.MatchstickMen;

import com.example.game.gamecode.GameBackend;

public class MatchstickMenBackend extends GameBackend {

    //Answer from user.
    private int answer;

    //Actual number of matchstick men.
    private int sum;

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    @Override
    public void update() {

    }
}
