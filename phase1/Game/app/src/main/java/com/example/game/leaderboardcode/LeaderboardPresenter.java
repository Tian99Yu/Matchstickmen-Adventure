package com.example.game.leaderboardcode;

import com.example.game.Games;

import java.io.IOException;

public class LeaderboardPresenter {
    private LeaderboardActivity leaderboardActivity;
    private LeaderboardManager leaderboardManager;

    public LeaderboardPresenter(LeaderboardActivity leaderboardActivity, LeaderboardManager leaderboardManager) {
        this.leaderboardActivity = leaderboardActivity;
        this.leaderboardManager = leaderboardManager;
        leaderboardActivity.setGames(leaderboardManager.getGames());

    }

    public void showScores() {
        try {
            leaderboardActivity.showScores(leaderboardActivity.getCurrentGame().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getScoreData(Games game) throws IOException {
        return leaderboardManager.getGameStatistics(game);
    }
}
