package com.example.game.leaderboardcode;

import com.example.game.Games;

interface LeaderboardView {
    void setGames(String[] games);
    void showScores();
    Games getCurrentGame();
}
