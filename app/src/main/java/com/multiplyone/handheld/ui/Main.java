/*
 * Copyright (C) 2016  Tobias Bielefeld
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * If you want to contact me, send me an e-mail at tobias.bielefeld@gmail.com
 */

package com.multiplyone.handheld.ui;

import static com.multiplyone.handheld.SharedData.NUMBER_OF_GAMES;
import static com.multiplyone.handheld.SharedData.TIMER_PAUSE;
import static com.multiplyone.handheld.SharedData.buttonKeyCodes;
import static com.multiplyone.handheld.SharedData.distanceHeight;
import static com.multiplyone.handheld.SharedData.distanceWidth;
import static com.multiplyone.handheld.SharedData.edit;
import static com.multiplyone.handheld.SharedData.gameView1;
import static com.multiplyone.handheld.SharedData.gameView2;
import static com.multiplyone.handheld.SharedData.getCurrentGame;
import static com.multiplyone.handheld.SharedData.highScores;
import static com.multiplyone.handheld.SharedData.input;
import static com.multiplyone.handheld.SharedData.look;
import static com.multiplyone.handheld.SharedData.mButtonPressed;
import static com.multiplyone.handheld.SharedData.mButtonPressedCounter;
import static com.multiplyone.handheld.SharedData.mainActivity;
import static com.multiplyone.handheld.SharedData.menu;
import static com.multiplyone.handheld.SharedData.params;
import static com.multiplyone.handheld.SharedData.savedData;
import static com.multiplyone.handheld.SharedData.soundList;
import static com.multiplyone.handheld.SharedData.sp;
import static com.multiplyone.handheld.SharedData.vibrate;
import static com.multiplyone.handheld.SharedData.vibration;
import static com.multiplyone.handheld.SharedData.width;
import static com.multiplyone.handheld.classes.Game.FIELD_HEIGHT;
import static com.multiplyone.handheld.classes.Game.FIELD_HEIGHT_2;
import static com.multiplyone.handheld.classes.Game.FIELD_WIDTH;
import static com.multiplyone.handheld.classes.Game.FIELD_WIDTH_2;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.multiplyone.handheld.R;
import com.multiplyone.handheld.classes.Game;
import com.multiplyone.handheld.surfaceViews.GameView1;
import com.multiplyone.handheld.surfaceViews.GameView2;
import com.multiplyone.handheld.ui.about.AboutActivity;

import java.lang.ref.SoftReference;
import java.util.Locale;

/*
 *  Main activity which loads everything, handles the button input and contains the main loop
 */

public class Main extends FragmentActivity implements Runnable, View.OnTouchListener {

    final long BACK_PRESSED_TIME_DELTA = 2000;

    private long mBackPressedTime;

    private Thread mThread = null;
    private Toast mToast;
    private ImageView[] mButton = new ImageView[6];
    private TextView[] mText = new TextView[5];
    private boolean mIsRunning;
    private int mPause;
    private long mLastFrameTime;

    private LinearLayout mLinearLayoutBackground;
    LinearLayout l1;
    LinearLayout linearLayoutMenuButtons;
    LinearLayout linearLayoutGameField;
    LinearLayout linearLayoutTexts;
    LinearLayout linearLayoutGameExtra;
    LinearLayout layoutButtons1;
    FrameLayout layoutButtons2;

    //flag to maintain pause state and ad visible in sync
    private static boolean pauseStateToggled = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_layout);
        createSoundPool();

        mLinearLayoutBackground = (LinearLayout) findViewById(R.id.linearLayoutBackground); //the whole display
        l1 = (LinearLayout) findViewById(R.id.LinearLayout1);
        linearLayoutMenuButtons = (LinearLayout) findViewById(R.id.linearLayoutMenuButtons);
        linearLayoutGameField = (LinearLayout) findViewById(R.id.linearLayoutGameField);
        linearLayoutTexts = (LinearLayout) findViewById(R.id.linearLayoutTexts);
        linearLayoutGameExtra = (LinearLayout) findViewById(R.id.linearLayoutGameExtra);
        layoutButtons1 = (LinearLayout) findViewById(R.id.layoutButtons1);
        layoutButtons2 = findViewById(R.id.layoutButtons2);
        vibration = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        gameView1 = new GameView1(linearLayoutGameField.getContext());
        gameView2 = new GameView2(linearLayoutGameExtra.getContext());

        mButton[0] = (ImageView) findViewById(R.id.button_up);
        mButton[1] = (ImageView) findViewById(R.id.button_down);
        mButton[2] = (ImageView) findViewById(R.id.button_left);
        mButton[3] = (ImageView) findViewById(R.id.button_right);
        mButton[4] = (ImageView) findViewById(R.id.button_action);
        mButton[5] = (ImageView) findViewById(R.id.button_pause);

        mText[0] = (TextView) findViewById(R.id.text_highscore);
        mText[1] = (TextView) findViewById(R.id.text_score);
        mText[2] = (TextView) findViewById(R.id.text_level);
        mText[3] = (TextView) findViewById(R.id.text_speed);
        mText[4] = (TextView) findViewById(R.id.text_pause);

        soundList[0] = sp.load(this, R.raw.beep, 1);
        soundList[1] = sp.load(this, R.raw.boing, 1);
        soundList[2] = sp.load(this, R.raw.explosion, 1);
        soundList[3] = sp.load(this, R.raw.boop, 1);
        soundList[4] = sp.load(this, R.raw.crunch, 1);
        soundList[5] = sp.load(this, R.raw.next, 1);
        soundList[6] = sp.load(this, R.raw.gameover, 1);
        soundList[7] = sp.load(this, R.raw.shoot, 1);

        mainActivity = this;

        /* Load saved SharedData */
        savedData = PreferenceManager.getDefaultSharedPreferences(this);
        edit = savedData.edit();
        for (int i = 1; i <= NUMBER_OF_GAMES; i++)
            highScores[i] = savedData.getInt("HighScore" + Integer.toString(i),
                    0);
        look = savedData.getInt(getString(R.string.prefKeyTextures), 2);
        menu.load();

        Game.setActivitySoftReference(new SoftReference<>(this));
        Game.reset();

        for (int i = 0; i < 5; i++) {
            mButton[i].setOnTouchListener(this);
        }

//        setBackgroundColor();
//        changeButtonColor();

        linearLayoutGameField.addView(gameView1);
        linearLayoutGameExtra.addView(gameView2);

        /* set up the layouts (the layouts will be rearranged to fit the calculated field dimensions)*/
//        mLinearLayoutBackground.post(new Runnable() {
//            @Override
//            public void run() {
//                setUpDimensions();
//            }
//        });

        buttonKeyCodes[0] = savedData.getInt("buttonUp", KeyEvent.KEYCODE_W);
        buttonKeyCodes[1] = savedData.getInt("buttonDown", KeyEvent.KEYCODE_S);
        buttonKeyCodes[2] = savedData.getInt("buttonLeft", KeyEvent.KEYCODE_A);
        buttonKeyCodes[3] = savedData.getInt("buttonRight", KeyEvent.KEYCODE_D);
        buttonKeyCodes[4] = savedData.getInt("buttonAction", KeyEvent.KEYCODE_L);
        buttonKeyCodes[5] = savedData.getInt("buttonClose", KeyEvent.KEYCODE_X);
        buttonKeyCodes[6] = savedData.getInt("buttonReset", KeyEvent.KEYCODE_R);
        buttonKeyCodes[7] = savedData.getInt("buttonPause", KeyEvent.KEYCODE_P);

    }

    @Override
    public void onPause() {                                                                         //pause thread
        super.onPause();
        mIsRunning = false;

        if (Game.sCurrentGame != 0) {                                                               //pause the game, but not when the game menu is currently shown
            startPause();
        }

        try {
            mThread.join();
        } catch (InterruptedException ignored) {
        }
    }

    @Override
    public void onResume() {                                                                        //resume thread
        super.onResume();
        /* set up the layouts (the layouts will be rearranged to fit the calculated field dimensions)*/
        mLinearLayoutBackground.post(new Runnable() {
            @Override
            public void run() {
                setUpDimensions();
            }
        });
        mIsRunning = true;
        mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    private void startAboutActivity() {
        startActivity(new Intent(getApplicationContext(), AboutActivity.class));
    }

    private void startSettingsActivity() {
        startActivity(new Intent(getApplicationContext(), Settings.class));
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {                                         //handle back mButton presses

//        if (keyCode == KeyEvent.KEYCODE_BACK                                                        //if it was the back key and this feature is still activated in the Settings,
//                && savedData.getBoolean(getString(R.string.prefKeyConfirmClosing), true)
//                && (System.currentTimeMillis() - mBackPressedTime > BACK_PRESSED_TIME_DELTA)) {      //and the delta to the last time pressed mButton is over the max time
//
//            showToast(getString(R.string.press_again));                                       //show mToast to press again
//            mBackPressedTime = System.currentTimeMillis();                                           //and save the time as pressed
//            return true;                                                                            //don't exit the game
//        }

        int pressedButtonID = -1;

//        if (keyCode == buttonKeyCodes[5]) {
//            if (savedData.getBoolean(getString(R.string.prefKeyConfirmClosing), true)
//                    && (System.currentTimeMillis() - mBackPressedTime > BACK_PRESSED_TIME_DELTA)) {      //and the delta to the last time pressed mButton is over the max time
//
//                showToast(getString(R.string.press_again));                                             //show toast to press again
//                mBackPressedTime = System.currentTimeMillis();                                           //and save the time as pressed don't exit the game
//            } else {
//                buttonPressClose();
//            }
//            return true;
//        } else if (keyCode == buttonKeyCodes[6]) {
//            buttonPressReset();
//            return true;
//        } else if (keyCode == buttonKeyCodes[7]) {
//            buttonPressPause();
//            return true;
//        } else
        if (keyCode == buttonKeyCodes[0]) {
            pressedButtonID = 0;
        } else if (keyCode == buttonKeyCodes[1]) {
            pressedButtonID = 1;
        } else if (keyCode == buttonKeyCodes[2]) {
            pressedButtonID = 2;
        } else if (keyCode == buttonKeyCodes[3]) {
            pressedButtonID = 3;
        } else if (keyCode == buttonKeyCodes[4]) {
            pressedButtonID = 4;
        }

        if (pressedButtonID != -1 && Game.sEvent == 0 && mPause != 1) {

            if (pressedButtonID < 4) {
                for (int i = 0; i < 4; i++) {
                    mButtonPressed[i] = 0;
                    mButtonPressedCounter[i] = 0;
                }
            }

            mButtonPressed[pressedButtonID] = 1;
            vibrate();
            input = pressedButtonID + 1;
            getCurrentGame().input();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        int releasedButtonID = -1;

        if (keyCode == buttonKeyCodes[0]) {
            releasedButtonID = 0;
        } else if (keyCode == buttonKeyCodes[1]) {
            releasedButtonID = 1;
        } else if (keyCode == buttonKeyCodes[2]) {
            releasedButtonID = 2;
        } else if (keyCode == buttonKeyCodes[3]) {
            releasedButtonID = 3;
        } else if (keyCode == buttonKeyCodes[4]) {
            releasedButtonID = 4;
        }

        if (releasedButtonID != -1) {
            mButtonPressed[releasedButtonID] = 0;
            mButtonPressedCounter[releasedButtonID] = 0;

            return true;
        }

        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void run() {                                                                             //Main loop
        while (mIsRunning) {

            //update game status
            if (mPause != 1) {
                Game.timerTick();
                Game.drawFieldContent();
            }

            //DRAW!!!
            gameView1.draw();
            gameView2.draw();

            long timeThisFrame = (System.currentTimeMillis() - mLastFrameTime);                     //update the frame counts
            long timeToSleep = TIMER_PAUSE - timeThisFrame;

            if (timeToSleep > 0) {                                                                  //sleep specific amount so the fps is looked
                try {
                    Thread.sleep(timeToSleep);
                } catch (InterruptedException ignored) {
                }
            }

            mLastFrameTime = System.currentTimeMillis();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {                                             //for button input, it is with multi touch but my program code doesn't work very good
        int pointerID = event.getPointerId(0) + 1;
        int pointerIndex = event.findPointerIndex(pointerID - 1);

        int[] location1 = new int[2];
        v.getLocationOnScreen(location1);

        float X = event.getX(pointerIndex) + location1[0];
        float Y = event.getY(pointerIndex) + location1[1];

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < 5; i++) {
                    int[] location = new int[2];
                    mButton[i].getLocationOnScreen(location);

                    if (mButtonPressed[i] != pointerID && Game.sEvent == 0 && mPause != 1
                            && X >= location[0]
                            && X <= location[0] + mButton[i].getWidth()
                            && Y >= location[1]
                            && Y <= location[1] + mButton[i].getHeight()) {
                        for (int j = 0; j < 5; j++) {
                            if (mButtonPressed[j] == pointerID) {
                                mButtonPressed[j] = 0;
                                mButtonPressedCounter[j] = 0;
                            }
                        }

                        vibrate();
                        mButtonPressed[i] = pointerID;
                        input = i + 1;
                        getCurrentGame().input();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                for (int i = 0; i < 5; i++) {
                    if (mButtonPressed[i] == pointerID) {
                        mButtonPressed[i] = 0;
                        mButtonPressedCounter[i] = 0;
                    }
                }
                break;
        }

        return true;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        sp = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    protected void createOldSoundPool() {
        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
    }

    public void click1(View view) {
        switch (view.getId()) {
            case R.id.layout_close:
                buttonPressClose();
                break;
            case R.id.layout_pause:
                buttonPressPause();
                break;
            case R.id.layout_menu:
                buttonPressReset();
                break;
            case R.id.layout_overflow:
                buttonOverflow();
                break;
        }

        vibrate();
    }

    private void buttonPressPause() {
        if (mPause == 0)                                                                      //just start the pause
            startPause();
        else if (mPause == 1) {                                                               //this means pause is running and should stop, so set to 2 so the handler doesnt call itself again
            mPause = 2;
            mText[4].setVisibility(View.INVISIBLE);
        } else if (mPause == 2)                                                                 //this means the handler should stop, but the pause button was pressed again, so just set to 1 so it wont stop
            mPause = 1;
    }

    private void buttonPressReset() {
        mText[4].setVisibility(View.INVISIBLE);
        mPause = 0;
        Game.reset();
    }

    private void buttonPressClose() {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    private void buttonOverflow() {
        Context wrapper = new ContextThemeWrapper(this, R.style.PopupMenu);
        PopupMenu popupMenu = new PopupMenu(wrapper, findViewById(R.id.layout_overflow));

        // Inflating popup menu from popup_menu.xml file
        popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.item_about) {
                    startAboutActivity();
                } else if (menuItem.getItemId() == R.id.item_settings) {
                    startSettingsActivity();
                }
                return true;
            }
        });

        // Showing the popup menu
        popupMenu.show();
    }

    public void updateUI() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mText[0].setText(String.format(Locale.getDefault(), "%d0", highScores[Game.sCurrentGame == 0 ? menu.getChoice() : Game.sCurrentGame]));
                mText[1].setText(String.format(Locale.getDefault(), "%d0", Game.sScore));
                mText[2].setText(String.format(Locale.getDefault(), "%d", Game.sCurrentGame == 0 ? menu.getLevelChoice() : Game.sLevel));
                mText[3].setText(String.format(Locale.getDefault(), "%d", Game.sSpeed));
            }
        });
    }

    private void startPause() {
        if (mPause == 0) {
            mPause = 1;
            PauseHandler pauseHandler = new PauseHandler();
            pauseHandler.sendEmptyMessage(0);
        }
    }

    private void createSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNewSoundPool();
        } else {
            createOldSoundPool();
        }
    }

    private void showToast(String text) {                                                     //simple function to show a new mToast text
        if (mToast == null)
            mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);                        //initialize mToast
        else
            mToast.setText(text);

        mToast.show();
    }

    private static class PauseHandler extends Handler {                                             //shows and hides the Pause text when game is paused
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (mainActivity.mPause == 1) {

                if (mainActivity.mText[4].getVisibility() == View.VISIBLE) {
                    mainActivity.mText[4].setVisibility(View.INVISIBLE);
                    mainActivity.mButton[5].setImageDrawable(ContextCompat.getDrawable(mainActivity,
                            R.drawable.ic_pause_svgrepo_com_orange));
                } else {
                    mainActivity.mText[4].setVisibility(View.VISIBLE);
                    mainActivity.mButton[5].setImageDrawable(ContextCompat.getDrawable(mainActivity,
                            R.drawable.ic_pause_svgrepo_com_1_));
                }

                sendEmptyMessageDelayed(0, 500);
            } else {
                pauseStateToggled = true;
                mainActivity.mPause = 0;
                mainActivity.mText[4].setVisibility(View.INVISIBLE);
                mainActivity.mButton[5].setImageDrawable(ContextCompat.getDrawable(mainActivity,
                        R.drawable.ic_pause_svgrepo_com_1_));

            }

        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // When the window loses focus (e.g. the action overflow is shown),
        // cancel any pending hide action. When the window gains focus,
        // hide the system UI.
        if (hasFocus) {
            delayedHide(300);
        } else {
            mHideHandler.removeMessages(0);
        }
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(

                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN

        );

    }

    private void showSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }

    private final Handler mHideHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            hideSystemUI();
        }
    };

    private void delayedHide(int delayMillis) {
        mHideHandler.removeMessages(0);
        mHideHandler.sendEmptyMessageDelayed(0, delayMillis);
    }

    private void setUpDimensions() {
        int marginTop;
        int totalWidth = mLinearLayoutBackground.getWidth();
        int totalHeight = mLinearLayoutBackground.getHeight();

        width = (int) ((totalHeight * 0.6) / (FIELD_HEIGHT));
        params = new LinearLayout.LayoutParams(totalWidth, (FIELD_HEIGHT) * width + distanceHeight * 2);

        linearLayoutMenuButtons.setLayoutParams(new LinearLayout.LayoutParams((totalWidth / 2 - ((FIELD_WIDTH * width) / 2)) - distanceWidth, LinearLayout.LayoutParams.MATCH_PARENT));

        linearLayoutGameField.setLayoutParams(new LinearLayout.LayoutParams(FIELD_WIDTH * width + distanceWidth * 2, LinearLayout.LayoutParams.MATCH_PARENT));

        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(width, 0, 0, 0);
        linearLayoutTexts.setLayoutParams(params);
//        linearLayoutGameExtra.setLayoutParams(
//                new LinearLayout.LayoutParams(
//                        (int) (width * FIELD_WIDTH_2 + distanceWidth * 2),
//                (int) (width * FIELD_HEIGHT_2 + distanceHeight * 2)));

        linearLayoutGameExtra.setLayoutParams(
                new LinearLayout.LayoutParams(
                        (int) (width * FIELD_WIDTH_2 + distanceWidth * 2),
                        (int) (width * FIELD_HEIGHT_2 + distanceHeight * 2)));
    }

    protected String getVersionName() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static class PopupClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }

    @Override
    protected void onDestroy() {
        Game.setActivitySoftReference(null);
        super.onDestroy();
    }
}

