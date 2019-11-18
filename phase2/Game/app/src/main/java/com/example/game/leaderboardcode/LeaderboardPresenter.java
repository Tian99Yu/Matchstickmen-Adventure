package com.example.game.leaderboardcode;

import com.example.game.Games;

import java.io.IOException;

class LeaderboardPresenter {
    private LeaderboardActivity leaderboardActivity;
    private LeaderboardManager leaderboardManager;

    LeaderboardPresenter(LeaderboardActivity leaderboardActivity, LeaderboardManager leaderboardManager) {
        this.leaderboardActivity = leaderboardActivity;
        this.leaderboardManager = leaderboardManager;
        leaderboardActivity.setGames(leaderboardManager.getGames());

    }

    void showScores() {
        try {
            leaderboardActivity.showScores(leaderboardActivity.getCurrentGame().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String[] getScoreData(Games game) {
        String[] ret = leaderboardManager.getGameStatistics(game);
        return ret;
    }
}
