package com.example.silvio.moviesaoo.service;

import android.content.Context;
import android.text.TextUtils;

import com.example.silvio.moviesaoo.R;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by silvio on 18/12/2017.
 */

public class ServiceErrorHandler {

    protected final Retrofit retrofit;
    protected final Context context;

    public ServiceErrorHandler(Retrofit retrofit, Context context) {
        this.retrofit = retrofit;
        this.context = context;
    }

    public APIError parseError(Response<?> response) {
        Converter<ResponseBody, APIError> converter =
                retrofit.responseBodyConverter(APIError.class, APIError.class.getAnnotations());

        APIError error;

        try {
            error = converter.convert(response.errorBody());
            if (TextUtils.isEmpty(error.getErrorDetail().getMessage())) {
                return new APIError(context.getString(R.string.error_default));
            }
        } catch (Exception e) {

            return new APIError(context.getString(R.string.error_default));
        }

        return error;
    }
}

