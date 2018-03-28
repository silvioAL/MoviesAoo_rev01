package com.example.silvio.moviesaoo.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by silvio on 19/12/2017.
 */

public class LoginResponseModel {

    @SerializedName("success")
    private boolean sucess;
    @SerializedName("expires_at")
    private String expiretionTime;
    @SerializedName("request_token")
    private String bearer;

    public LoginResponseModel(boolean sucess, String expiretionTime, String bearer) {
        this.sucess = sucess;
        this.expiretionTime = expiretionTime;
        this.bearer = bearer;
    }

}
