package com.example.silvio.moviesaoo.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;

import com.example.silvio.moviesaoo.BuildConfig;
import com.example.silvio.moviesaoo.R;
import com.example.silvio.moviesaoo.data.model.LoginResponseModel;
import com.example.silvio.moviesaoo.inject.scopes.MoviesAppScope;
import com.example.silvio.moviesaoo.interfaces.ContextInteraction;
import com.example.silvio.moviesaoo.interfaces.GenericNotification;
import com.example.silvio.moviesaoo.interfaces.GenericStringInteraction;
import com.example.silvio.moviesaoo.service.APIError;
import com.example.silvio.moviesaoo.service.AccountApp;
import com.example.silvio.moviesaoo.service.AccountServices;
import com.example.silvio.moviesaoo.service.DefaultResponse;
import com.example.silvio.moviesaoo.view.HomeActivity;
import com.example.silvio.moviesaoo.view.LoginActivity;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by silvio on 19/12/2017.
 */

@MoviesAppScope
public class LoginActivityViewModel extends BaseObservable {

    AccountServices services;
    GenericNotification notification;
    GenericStringInteraction stringInteraction;
    ContextInteraction contextInteraction;

    @Inject
    public LoginActivityViewModel(AccountServices services) {
        this.services = services;
    }

    public void setNotification(GenericNotification notification) {
        this.notification = notification;
    }

    public void setStringInteraction(GenericStringInteraction stringInteraction) {
        this.stringInteraction = stringInteraction;
    }

    public void setContextInteraction(ContextInteraction contextInteraction) {
        this.contextInteraction = contextInteraction;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) contextInteraction.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void doLogin() {

        if (isOnline()) {

            notification.showLoading();
            services.doLogin(BuildConfig.API_KEY).enqueue(new Callback<DefaultResponse<LoginResponseModel>>() {
                @Override
                public void onResponse(Call<DefaultResponse<LoginResponseModel>> call, Response<DefaultResponse<LoginResponseModel>> response) {

                    if (response.isSuccessful()) {
                        notification.hideLoading();
                        AccountApp app = new AccountApp(BuildConfig.API_KEY);
                        services.saveLocally(app);
                        redirectToHome();
                    } else {
                        APIError error = services.parseError(response);
                        notification.hideLoading();
                        notification.openAlertDialog(error.toString());
                    }
                }

                @Override
                public void onFailure(Call<DefaultResponse<LoginResponseModel>> call, Throwable t) {

                    notification.hideLoading();
                    redirectOnError(t);
                }
            });
        } else {
            notification.openAlertDialog(contextInteraction.getContext().getResources().getString(R.string.no_connection_av));
        }

    }

    private void redirectToHome() {
        Intent intent = new Intent(contextInteraction.getContext(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        contextInteraction.getContext().startActivity(intent);
    }

    public void redirectOnError(Throwable t) {
        new CountDownTimer(6000, 1000) {

            public void onTick(long millisUntilFinished) {
                notification.openAlertDialog(t.getMessage().toString() + "\n"
                        + stringInteraction.getStringFromResource(R.string.redirecting)
                        + millisUntilFinished / 1000
                        + stringInteraction.getStringFromResource(R.string.seconds));
            }

            public void onFinish() {
                Intent intent = new Intent(contextInteraction.getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                contextInteraction.getContext().startActivity(intent);
            }
        }.start();
    }
}
