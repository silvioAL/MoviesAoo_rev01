package com.example.silvio.moviesaoo.util;

import android.content.Context;

import com.example.silvio.moviesaoo.data.local.AppContract;

/**
 * Created by silvioallgayertrindade on 30/01/2018.
 */

public class PreferenceStorageUtil {

    public static String PARAM = "SEARCH_PARAM";
    public Context mContext;

    public PreferenceStorageUtil(Context mContext) {
        this.mContext = mContext;
    }

    public void saveFilteringMode(String filter) {
        mContext.getSharedPreferences(AppContract.MOVIES_APPREF, 0).edit().putString(PARAM, filter).apply();
    }

    public String retrieveFilteringMode() {
        return mContext.getSharedPreferences(AppContract.MOVIES_APPREF, 0).getString(PARAM, "");

    }

}
