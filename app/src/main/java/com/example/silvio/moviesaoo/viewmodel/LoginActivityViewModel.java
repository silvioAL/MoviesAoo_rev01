package com.example.silvio.moviesaoo.viewmodel;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.os.CountDownTimer;

import com.example.silvio.moviesaoo.R;
import com.example.silvio.moviesaoo.interfaces.ContextInteraction;
import com.example.silvio.moviesaoo.interfaces.GenericNotification;
import com.example.silvio.moviesaoo.interfaces.GenericStringInteraction;
import com.example.silvio.moviesaoo.model.LoginResponseModel;
import com.example.silvio.moviesaoo.service.APIError;
import com.example.silvio.moviesaoo.service.AccountApp;
import com.example.silvio.moviesaoo.service.AccountServices;
import com.example.silvio.moviesaoo.service.DefaultResponse;
import com.example.silvio.moviesaoo.view.HomeActivity;
import com.example.silvio.moviesaoo.view.LoginActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by silvio on 19/12/2017.
 */

@Singleton
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

    public void doLogin() {
        notification.showLoading();
        services.doLogin(stringInteraction.getStringFromResource(R.string.API_KEY)).enqueue(new Callback<DefaultResponse<LoginResponseModel>>() {
            @Override
            public void onResponse(Call<DefaultResponse<LoginResponseModel>> call, Response<DefaultResponse<LoginResponseModel>> response) {

                if (response.isSuccessful()) {
                    notification.hideLoading();
                    AccountApp app = new AccountApp(stringInteraction.getStringFromResource(R.string.API_KEY));
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
