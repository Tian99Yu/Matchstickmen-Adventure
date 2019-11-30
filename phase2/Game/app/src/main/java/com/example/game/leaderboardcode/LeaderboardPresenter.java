package com.example.game.leaderboardcode;

import com.example.game.Games;

class LeaderboardPresenter {
    private LeaderboardView leaderboardView;
    private LeaderboardManager leaderboardManager;

    LeaderboardPresenter(LeaderboardView leaderboardView, LeaderboardManager leaderboardManager) {
        this.leaderboardView = leaderboardView;
        this.leaderboardManager = leaderboardManager;
        leaderboardView.setGames(leaderboardManager.getGames());
    }

    void showScores() {
        leaderboardView.showScores(leaderboardView.getCurrentGame().toString());
    }

    String[][] getScoreData(Games game) {
        return leaderboardManager.getGameStatistics(game);
    }
}
