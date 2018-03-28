package com.example.silvio.moviesaoo.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.silvio.moviesaoo.R;
import com.example.silvio.moviesaoo.data.model.GetMovieTrailersResponseModel;
import com.example.silvio.moviesaoo.data.model.GetMoviesGenresResponseModel;
import com.example.silvio.moviesaoo.data.model.GetMoviesReviewsResponseModel;
import com.example.silvio.moviesaoo.data.model.GetSelectedGenreMoviesListResponseModel;
import com.example.silvio.moviesaoo.data.model.LoginResponseModel;
import com.example.silvio.moviesaoo.data.model.SearchMovieResponseModel;
import com.example.silvio.moviesaoo.inject.scopes.MoviesAppScope;
import com.example.silvio.moviesaoo.interfaces.NetworkServicesInterfaces;
import com.example.silvio.moviesaoo.util.GenericJsonParser;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by silvio on 19/12/2017.
 */
@MoviesAppScope
public class AccountServices extends ServiceErrorHandler {

    @Inject
    public AccountServices(Retrofit retrofit, Context context) {
        super(retrofit, context);
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(
                context.getString(R.string.PREF_DEF), Context.MODE_PRIVATE);
    }

    public boolean saveLocally(AccountApp accountApp) {
        try {
            AccountApp app = accountApp;
            getSharedPreferences().edit().putString(String.valueOf(R.string.API_KEY), GenericJsonParser.objectToJson(app)).apply();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public AccountApp retriveUserAccountLocalSaved() {
        try {
            String accountSingletonApp = getSharedPreferences().getString(String.valueOf(R.string.API_KEY), null);
            if (accountSingletonApp != null)
                return GenericJsonParser.jsonToObject(accountSingletonApp, AccountApp.class);
            else return new AccountApp(accountSingletonApp);
        } catch (Exception e) {

            return null;
        }
    }

    public Call<DefaultResponse<LoginResponseModel>> doLogin(String APIKEY){
        return retrofit.create(NetworkServicesInterfaces.class).doLogin(APIKEY);
    }

    public Call<GetMoviesGenresResponseModel> getGenres(String APIKEY, String lenguage){
        return retrofit.create(NetworkServicesInterfaces.class).getGenres(APIKEY, lenguage);
    }

    public Call<GetSelectedGenreMoviesListResponseModel> getGenreSelection(String APIKEY, String lenguage, String genreId){
        return retrofit.create(NetworkServicesInterfaces.class).getSelectedGenreList(genreId, APIKEY, lenguage);
    }

    public Call<SearchMovieResponseModel> getPopularMovies(String APIKEY, String word){
        return retrofit.create(NetworkServicesInterfaces.class).getPopularMovies(APIKEY, word);
    }

    public Call<SearchMovieResponseModel> getTopRatedMovies(String APIKEY, String word){
        return retrofit.create(NetworkServicesInterfaces.class).getTopRatedMovies(APIKEY, word);
    }

    public Call<GetMovieTrailersResponseModel> getMovieTrailers(String APIKEY, String movieID) {
        return retrofit.create(NetworkServicesInterfaces.class).getMovieTrailers(movieID, APIKEY);
    }

    public Call<GetMoviesReviewsResponseModel> getMovieReviews(String APIKEY, String movieID) {
        return retrofit.create(NetworkServicesInterfaces.class).getMovieReviews(movieID, APIKEY);
    }

}
