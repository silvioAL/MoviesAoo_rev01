package com.example.silvio.moviesaoo.service;

import javax.inject.Singleton;

/**
 * Created by silvio on 24/12/2017.
 */

@Singleton
public class AccountApp {

    String USER_API_KEY;

    public AccountApp(String USER_API_KEY) {
        this.USER_API_KEY = USER_API_KEY;
    }

    public String getUSER_API_KEY() {
        return USER_API_KEY;
    }

    public void setUSER_API_KEY(String USER_API_KEY) {
        this.USER_API_KEY = USER_API_KEY;
    }
}
