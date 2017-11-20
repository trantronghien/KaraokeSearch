package com.example.admin.karaokesearch.util;

/**
 * Created by admin on 17/10/2017.
 */

public final class UtilHelper {
    public static final int FRAGMENT_SONG_LIST = 1;  // --->100
    public static final int FRAGMENT_SEARCH = 101;
    public static final int FRAGMENT_FAVORITE = 201;
    public static final int FRAGMENT_UPDATE= 301;

    public static final int ERROR_CODE_MODEL = 1;
    public static final int ERROR_CODE_MANAGER = 10;
    public static final int ERROR_CODE_PRESENTER = 20;
    public static final int ERROR_CODE_VIEW = 30;
    public static final String VN_LANGUAGE = "vn";
    public static final String EN_LANGUAGE = "en";

    public static String getStringRes(int stringId,String parameter){
        return App.getContext().getResources().getString(stringId , parameter);
    }

    public static String getStringRes(int stringId){
        return App.getContext().getResources().getString(stringId);
    }
    public static int getColorRes(int idColor){
        return App.getContext().getResources().getColor(idColor);
    }
}
