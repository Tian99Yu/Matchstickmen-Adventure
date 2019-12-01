package com.group0540.matchstickmenadventures.gamecode.matchstickmen;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.group0540.matchstickmenadventures.Games;
import com.example.matchstickmenadventures.R;
import com.group0540.matchstickmenadventures.gamecode.GameActivity;
import com.group0540.matchstickmenadventures.gamecode.GameView;
import com.group0540.matchstickmenadventures.leaderboardcode.Saver;
import com.group0540.matchstickmenadventures.settingscode.CustomizableGame;

public abstract class SuperMatchstickMenActivity extends GameActivity
        implements CustomizableGame, Saver {
    /**
     * A flag recording whether the data of this game is saved.
     */
    protected boolean saved = false;

    /**
     * customization variable, containing customization info
     */
    protected MatchstickMenCustomization matchstickMenCustomization = new MatchstickMenCustomization();

    /**
     * the progress bar on top of the screen
     */
    protected ProgressBar pgBar;

    /**
     * the count down timer shown on top of the screen
     */
    protected CountDownTimer timer;


    /**
     * Make the customizations.
     *
     * @param level     the level of this game
     * @param theme     the theme of this game
     * @param character the character that will be shown on the screen
     */
    public void customization(String level, String theme, String character) {
        setDifficulty(level);
        setTheme(theme);
        setCharacter(character);
    }


    /**
     * the timeleft view shown on top of the screen
     */
    protected TextView timeleft;

    /**
     * button used to restart the game
     */
    protected Button restart;
    /**
     * the number recording how many matchstick men the user has counted
     */
    private int num = 0;

    /**
     * the getter for the num variable mentioned above
     *
     * @return return the number of matchstick men the user has counted
     */
    public int getNum() {
        return num;
    }

    /**
     * the setter for variable num
     *
     * @param num the number of matchstick men the user has counted
     */
    public void setNum(int num) {
        this.num = num;
    }

    /**
     * the onCreate method to create the view, presenter and backend
     *
     * @param savedInstanceState the activity's previously saved state
     */
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * the setView method to create a new MatchstickMenView
     *
     * @return A MatchstickMenView, which is the Canvas, or the black part containing the
     * animation shown on the screen
     */
    @Override
    protected GameView setView() {
        return new MatchstickMenView(this);
    }

    /**
     * get the update interval of the game
     * this is how fast the animation is and also sets up for the total
     * time available for the user to count the number of matchstick men
     *e
     * @return the updateInterval, which is the break between each update in thread, and the
     * total time for the entire game
     */
    public int getUpdateInterval() {
        int updateInterval;
        int difficulty = matchstickMenCustomization.getDifficulty();
        if (difficulty == 1) {
            updateInterval = 7;
        } else if (difficulty == 2) {
            updateInterval = 5;
        } else {
            updateInterval = 10;
        }
        ((MatchstickMenBackend) gameView.gameBackend).setTotalTime(updateInterval);
        return updateInterval;
    }

    /**
     * set the difficulty of the game
     *
     * @param difficulty the difficulty of the game, based on the choice of customization by user
     */
    @Override
    public void setDifficulty(String difficulty) {
        this.matchstickMenCustomization.setDifficulty(difficulty);
    }

    /**
     * set the character the user chosen in customization (the top left botton in mainMenuScreen
     *
     * @param character the character the user has chosen
     */
    @Override
    public void setCharacter(String character) {
        this.matchstickMenCustomization.setCharacter(character);
    }

    /**
     * set the theme for this game
     *
     * @param theme theme for customization (dark or light mode)
     */
    @Override
    public void setTheme(String theme) {
        this.matchstickMenCustomization.setTheme(theme);
    }

    /**
     * link the view widget to the id in the xml file
     */
    abstract void setButtons();

    /**
     * set up the count down timer in the game, it is the one on the top of the screen
     * also save the game data when the game ends
     *
     * @param pgBar    the progress bar used in the game
     * @param timeleft the time left view (next to the progress bar)
     * @return a new CountDownTimer
     */
    public CountDownTimer setTimers(final ProgressBar pgBar, final TextView timeleft) {
        final int totalTime = getUpdateInterval();

        return
                new CountDownTimer(totalTime * 1000, 1000) {
                    int i = 0;

                    @Override
                    public void onTick(long l) {
                        i += (int) (100 / totalTime);
                        String strTimeLeft = Float.toString(l / 1000) + "secs";
                        timeleft.setText(strTimeLeft);
                        if (l < 1000) {
                            pgBar.setProgress(100);
                        } else {
                            pgBar.setProgress(i, true);
                        }
                    }

                    @Override
                    public void onFinish() {
                        timeleft.setText(R.string.timeUp);
                        pgBar.setProgress(100);
                        ((MatchstickMenBackend) gameView.gameBackend).setOver(true);
                    }
                };
    }

    /**
     * set the onClickListeners for all the bottons in the xml files
     */
    abstract void setOnclickListeners();

    /**
     * set the initial for count TextView in xml file
     */
    abstract void setCount();

    /**
     * the set up process for the timer part (all the views on the top of the screen
     */
    public void setUpTimer() {
        pgBar.setProgress(0);


        timer = setTimers(pgBar, timeleft);
        timer.start();

    }

    /**
     * save the game data
     */
    @Override
    protected void saveData() {
        if (!saved) {
            String[] stats = {"Count", "Score", "Time"};
            String[] values = {
                    String.valueOf(((MatchstickMenBackend) gameView.gameBackend).getCount()),
                    String.valueOf((gameView.gameBackend).getCurrentScore()),
                    String.valueOf(((MatchstickMenBackend) gameView.gameBackend).getTimeUsed())
            };
            leaderboardManager.saveData(
                    Games.MATCHSTICKMEN,
                    loginManager.getUsername(),
                    stats,
                    values
            );
        }
    }

    /**
     * send the game info to the next intent, in this case, is to send info between single and
     * double player mode
     *
     * @param intent the message object that is used to communicate between activities
     */
    protected void sendToIntent(Intent intent) {
        intent.putExtra("loginManager", loginManager);
        intent.putExtra("leaderboardManager", leaderboardManager);
        intent.putExtra("settingsManager", settingsManager);
    }

    /**
     * pause the game
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * resume the game
     */
    protected void onResume() {
        super.onResume();
    }


}
