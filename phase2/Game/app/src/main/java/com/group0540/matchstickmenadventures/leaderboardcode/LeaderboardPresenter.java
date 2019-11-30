package com.group0540.matchstickmenadventures.leaderboardcode;

import com.group0540.matchstickmenadventures.Games;

class LeaderboardPresenter {
    private final LeaderboardView leaderboardView;
    private final LeaderboardManager leaderboardManager;

    LeaderboardPresenter(LeaderboardView leaderboardView, LeaderboardManager leaderboardManager) {
        this.leaderboardView = leaderboardView;
        this.leaderboardManager = leaderboardManager;
        leaderboardView.setGames(leaderboardManager.getGames());
    }

    void showScores() {
        leaderboardView.showScores();
    }

    String[][] getScoreData(Games game) {
        return leaderboardManager.getGameStatistics(game);
    }
}
