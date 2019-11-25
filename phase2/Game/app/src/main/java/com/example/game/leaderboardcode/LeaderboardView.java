package com.example.game.leaderboardcode;

import com.example.game.Games;

public interface LeaderboardView {
    void setGames(String[] games);
    void showScores(String game);
    Games getCurrentGame();
}
