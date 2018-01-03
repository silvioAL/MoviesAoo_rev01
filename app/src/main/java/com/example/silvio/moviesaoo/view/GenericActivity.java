package com.example.silvio.moviesaoo.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.silvio.moviesaoo.R;
import com.example.silvio.moviesaoo.interfaces.GenericNotification;
import com.example.silvio.moviesaoo.interfaces.GenericStringInteraction;
import com.example.silvio.moviesaoo.util.AlertUtil;

/**
 * Created by silvio on 19/12/2017.
 */

@SuppressLint("Registered")
public class GenericActivity extends AppCompatActivity implements GenericStringInteraction,
        GenericNotification {
    private ProgressDialog progress;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progress = new ProgressDialog(this);
    }

    @Override
    public String getStringFromResource(int resourceId) {
        return getString(resourceId);
    }

    @Override
    public void showLoading() {
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setCancelable(false);
        progress.show();
    }

    @Override
    public void hideLoading() {
        progress.dismiss();
    }

    @Override
    public void openAlertDialog(String message) {
        AlertUtil.showAlert(this, message);
    }

}
