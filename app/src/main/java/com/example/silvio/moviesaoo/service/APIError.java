package com.example.silvio.moviesaoo.service;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by silvio on 18/12/2017.
 */

public class APIError {

    @SerializedName("error")
    private List<APIErrorDetail> errorDetail;

    public APIErrorDetail getErrorDetail() {
        return errorDetail.get(0);
    }

    public APIError(String message) {
        errorDetail = new ArrayList<>();
        errorDetail.add(new APIErrorDetail(message));
        errorDetail.get(0);
    }

    public class  APIErrorDetail {

        @SerializedName("code")
        private String code;

        @SerializedName("message")
        private String message;

        public APIErrorDetail(String mensagem) {
            message = mensagem;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}

