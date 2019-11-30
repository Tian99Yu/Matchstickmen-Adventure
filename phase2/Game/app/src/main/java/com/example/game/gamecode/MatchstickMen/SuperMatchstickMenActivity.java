package com.example.game.gamecode.MatchstickMen;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.game.Games;
import com.example.game.R;
import com.example.game.gamecode.GameActivity;
import com.example.game.gamecode.GameView;
import com.example.game.leaderboardcode.LeaderboardManager;
import com.example.game.leaderboardcode.Saver;
import com.example.game.settingscode.CustomizableGame;
import com.example.game.settingscode.SettingsManager;

public abstract class SuperMatchstickMenActivity extends GameActivity
        implements CustomizableGame, Saver {
    /**
     * A flag recording whether the data of this game is saved.
     */
    protected boolean saved = false;

    protected int level = 0;
    protected int color = Color.WHITE;
    protected String character = "circle";
    protected MatchstickMenCustomization matchstickMenCustomization = new MatchstickMenCustomization();
    protected ProgressBar pgBar;
    protected CountDownTimer timer;


    /**
     * Make the customizations.
     *
     * @param level     the level of this game
     * @param theme     the theme of this game
     * @param character the character that will be shown on the screen
     */
    public void customization(String level, String theme, String character) {
        //    this.level = level;
        //    this.color = color;
        //    this.character = character;

        setDifficulty(level);
        setTheme(theme);
        setCharacter(character);
    }

    public int getLevel() {
        return level;
    }

    public int getColor() {
        return color;
    }

    public String getCharacter() {
        return character;
    }

    //  protected ProgressBar pgBar;
    protected TextView timeleft;
    protected Button restart;
    //    private Button btnAdd, btnMinus, btnDone, restart, nextLevel;
    private int num = 0;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void saveScore() {
        if (!saved) {
            String[] stats = {"Count", "Score", "Time"};
            String[] values = {
                    String.valueOf(((MatchstickMenBackend) gameView.gameBackend).getCount()),
                    String.valueOf((gameView.gameBackend).getCurrentScore()),
                    String.valueOf(((MatchstickMenBackend) gameView.gameBackend).getTimeUsed())
            };
        }
    }

    @Override
    protected GameView setView() {
        return new MatchstickMenView(this);
    }


    public int getTotalTime() {
        int totalTime;
        switch (matchstickMenCustomization.getDifficulty()) {
            case 0:
                totalTime = 10;
                break;
            case 1:
                totalTime = 7;
                break;
            case 2:
                totalTime = 5;
                break;
            default:
                totalTime = 10;
        }
        ;
        return totalTime;
    }

    @Override
    public void setDifficulty(String difficulty) {
        this.matchstickMenCustomization.setDifficulty(difficulty);
    }

    @Override
    public void setCharacter(String character) {
        this.matchstickMenCustomization.setCharacter(character);
    }


    @Override
    public void setTheme(String theme) {
        this.matchstickMenCustomization.setTheme(theme);
    }

    abstract void setButtons();

    public CountDownTimer setTimers(final ProgressBar pgBar, final TextView timeleft) {
        final int totalTime = getTotalTime();

        CountDownTimer timer =
                new CountDownTimer(totalTime * 1000, 1000) {
                    int i = 0;

                    @Override
                    public void onTick(long l) {
                        i += (int) (100 / totalTime);

                        timeleft.setText(Float.toString(l / 1000) + "secs");
                        if (l < 1000) {
                            pgBar.setProgress(100);
                        } else {
                            pgBar.setProgress(i, true);
                        }
                    }

                    @Override
                    public void onFinish() {
                        timeleft.setText("Time's up!");
                        pgBar.setProgress(100);
                        ((MatchstickMenBackend) gameView.gameBackend).setOver(true);
                        if (!saved) {
                            String count =
                                    Integer.toString(((MatchstickMenBackend) gameView.gameBackend).getCount());
                            String score = Integer.toString(gameView.gameBackend.getCurrentScore());
                            String timeUsed =
                                    Integer.toString(((MatchstickMenBackend) gameView.gameBackend).getTimeUsed());
                            String[] stats = {"Count", "Score", "Time used"};
                            String[] values = {count, score, timeUsed};
                            leaderboardManager.saveData(Games.MATCHSTICKMEN, username, stats, values);
                        }
                    }
                };

        return timer;
    }

    abstract void setOnclickListeners();

    abstract void setCount();

    public void setUpTimer() {
        pgBar.setProgress(0);


        timer = setTimers(pgBar, timeleft);
        timer.start();

    }

    @Override
    protected void saveData() {
        if (!saved) {
            String[] stats = {"Count", "Score", "Time"};
            String[] values = {
                    String.valueOf(((MatchstickMenBackend) gameView.gameBackend).getCount()),
                    String.valueOf((gameView.gameBackend).getCurrentScore()),
                    String.valueOf(((MatchstickMenBackend) gameView.gameBackend).getTimeUsed())
            };
        }
    }

    protected void sendToIntent(Intent intent) {
        intent.putExtra("username", username);
        intent.putExtra("leaderboardManager", leaderboardManager);
        intent.putExtra("settingsManager", settingsManager);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    protected void onResume() {
        super.onResume();
    }


}
