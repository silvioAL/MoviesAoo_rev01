package com.example.silvio.moviesaoo.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import javax.inject.Inject;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private HomeViewModel mViewModel;

    @Inject
    public ViewModelFactory(HomeViewModel viewModel) {
        this.mViewModel = viewModel;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) mViewModel;
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}