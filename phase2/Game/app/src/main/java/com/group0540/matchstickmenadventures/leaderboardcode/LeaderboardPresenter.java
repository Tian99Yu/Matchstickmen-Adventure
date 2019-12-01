package com.group0540.matchstickmenadventures.leaderboardcode;

class LeaderboardPresenter {
    private final LeaderboardView leaderboardView;

    LeaderboardPresenter(LeaderboardView leaderboardView) {
        this.leaderboardView = leaderboardView;
        leaderboardView.setGames();
    }

    void showScores() {
        leaderboardView.showScores();
    }
}
