package com.group0540.matchstickmenadventures.leaderboardcode;

import com.group0540.matchstickmenadventures.Games;

interface LeaderboardView {
    void setGames(String[] games);
    void showScores();
    void showScores(String statistic);
    Games getCurrentGame();
}
