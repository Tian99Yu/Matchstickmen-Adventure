package com.example.game.leaderboardcode;

import com.example.game.Games;

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
