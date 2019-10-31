package com.example.game.leaderboardcode;

import com.example.game.Games;

import java.io.IOException;

public interface LeaderboardInterface {
    void setGames(String[] games);
    void showScores(String game) throws IOException;
    String[] getGames();
    Games getCurrentGame();
}
