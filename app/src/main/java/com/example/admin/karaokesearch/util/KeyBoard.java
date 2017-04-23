package com.example.admin.karaokesearch.util;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by admin on 3/31/2017.
 */

public class KeyBoard {

    public static void show(Activity activity){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY); // show
    }

    public static void hide(Activity activity){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); // hide
        imm.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void toggle(Activity activity){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm.isActive()){
            hide(activity);
        } else {
            show(activity);
        }
    }
}
