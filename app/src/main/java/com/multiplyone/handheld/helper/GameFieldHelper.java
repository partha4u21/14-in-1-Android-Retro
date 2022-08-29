package com.multiplyone.handheld.helper;

import android.util.DisplayMetrics;

import com.multiplyone.handheld.R;
import com.multiplyone.handheld.ui.Main;

public class GameFieldHelper {


    private static int getScreenHeightInDp(Main main) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        main.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return (int) (displayMetrics.heightPixels / ((float) main.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private static int getScreenWidthInDp(Main main) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        main.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return (int) (displayMetrics.widthPixels /
                ((float) main.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int getOptimalGameFieldHeight(Main main) {
        return ((getScreenHeightInDp(main) / main.getResources().getInteger(R.integer.game_field_weight_height)) / 4);
    }

    public static int getOptimalGameFieldWidth(Main main) {
        return ((getScreenWidthInDp(main) / main.getResources().getInteger(R.integer.game_field_weight_width)) / 4);
    }
}
