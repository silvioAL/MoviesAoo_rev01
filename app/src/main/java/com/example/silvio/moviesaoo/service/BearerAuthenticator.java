package com.example.silvio.moviesaoo.service;

import android.support.annotation.Nullable;

import com.example.silvio.moviesaoo.data.model.BearerHolder;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Created by silvio on 19/12/2017.
 */

public class BearerAuthenticator implements Authenticator {

    private static final String AUTHORIZATION = "Authorization";

    @Nullable
    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        String signBerarer = BearerHolder.getInstance().getTempKey();

        String credential = Credentials.basic(AUTHORIZATION, signBerarer);
        if (credential.equals(response.request().header("Authorization"))) {
            return null; // If we already failed with these credentials, don't retry.
        }if (response.header(AUTHORIZATION) != null && response.header(AUTHORIZATION).length() > 0){
            return null;
        }if(credential == null){
            return null;
        }
        return response.request().newBuilder()
                .header(AUTHORIZATION, signBerarer != null ? "Bearer " + signBerarer : "")
                .build();
    }
}
