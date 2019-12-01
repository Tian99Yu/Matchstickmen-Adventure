package com.group0540.matchstickmenadventures.leaderboardcode;

import com.group0540.matchstickmenadventures.Games;

interface LeaderboardView {
    void setGames();
    void showScores();
    void showScores(String statistic);
    Games getCurrentGame();
}
