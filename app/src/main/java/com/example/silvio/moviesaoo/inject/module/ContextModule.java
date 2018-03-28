package com.example.silvio.moviesaoo.inject.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by silvioallgayertrindade on 05/02/2018.
 */

@Module
public class ContextModule {

    Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    Context context() {
        return this.context;
    }
}
