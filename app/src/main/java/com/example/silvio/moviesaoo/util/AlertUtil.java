package com.example.silvio.moviesaoo.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.example.silvio.moviesaoo.R;

/**
 * Created by silvio on 19/12/2017.
 */

public class AlertUtil {

    public static void showAlert(Activity activity, String message) {

    try {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle(activity.getResources().getString(R.string.atention));
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setNegativeButton(activity.getResources().getString(R.string.ok), null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    } catch (Exception e) {
        Log.e(AlertUtil.class.toString(), e.getMessage());
    }
}

        public static void showAlert(Activity activity, String message, DialogInterface.OnClickListener dismissListener) {
            try {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                alertDialogBuilder.setTitle(activity.getResources().getString(R.string.atention));
                alertDialogBuilder.setMessage(message);
                alertDialogBuilder.setNegativeButton(activity.getResources().getString(R.string.ok), dismissListener);

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            } catch (Exception e) {
                Log.e(AlertUtil.class.toString(), e.getMessage());
            }
        }

        public static void showAlert(Activity activity, String title, String message) {
            try {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                alertDialogBuilder.setTitle(title);
                alertDialogBuilder.setMessage(message);
                alertDialogBuilder.setNegativeButton(activity.getResources().getString(R.string.ok), null);

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            } catch (Exception e) {
                Log.e(AlertUtil.class.toString(), e.getMessage());
            }
        }

        public static void showConfirmAlert(Activity activity, String title, String message, DialogInterface.OnClickListener positiveListener) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
            alertDialogBuilder.setTitle(title);
            alertDialogBuilder.setMessage(message);
            alertDialogBuilder.setPositiveButton(activity.getResources().getString(R.string.yes), positiveListener);
            alertDialogBuilder.setNegativeButton(activity.getResources().getString(R.string.no), null);
            alertDialogBuilder.getContext().setTheme(R.style.Theme_AppCompat_Dialog);


            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
}