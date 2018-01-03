package com.example.silvio.moviesaoo.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.example.silvio.moviesaoo.MainApplication;
import com.example.silvio.moviesaoo.R;
import com.example.silvio.moviesaoo.databinding.ActivityLoginBinding;
import com.example.silvio.moviesaoo.interfaces.ContextInteraction;
import com.example.silvio.moviesaoo.viewmodel.LoginActivityViewModel;

import javax.inject.Inject;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;


public class LoginActivity extends GenericActivity implements ContextInteraction{

    @Inject
    LoginActivityViewModel loginActivityViewModelviewModel;
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        injectDependencies();
        binding.setLoginActivityViewModel(loginActivityViewModelviewModel);
        loginActivityViewModelviewModel.setNotification(this);
        loginActivityViewModelviewModel.setStringInteraction(this);
        loginActivityViewModelviewModel.setContextInteraction(this);
        PulsatorLayout pulsatorLayout = (PulsatorLayout) findViewById(R.id.pulsator);
        pulsatorLayout.start();
    }

    private void injectDependencies() {
        MainApplication.getAppComponent().inject(this);
    }

    @Override
    public Context getContext() {
        return getBaseContext();
    }
}
