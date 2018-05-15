package com.example.silvio.moviesaoo.data.repo;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

import com.example.silvio.moviesaoo.data.entity.MovieData;
import com.example.silvio.moviesaoo.data.local.AppContract;
import com.example.silvio.moviesaoo.data.model.SearchMovieResponseModel;
import com.example.silvio.moviesaoo.service.AccountApp;
import com.example.silvio.moviesaoo.service.AccountServices;
import com.example.silvio.moviesaoo.util.ContentValuesParser;
import com.example.silvio.moviesaoo.util.ProjectionHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MoviesRepository {

    private AccountServices services;
    private MutableLiveData<List<MovieData>> movies = new MutableLiveData<>();

    @Inject
    public MoviesRepository(AccountServices accountServices) {
        this.services = accountServices;
    }


    public MutableLiveData<List<MovieData>> getMoviesByRating() {

        AccountApp accountApp = services.retriveUserAccountLocalSaved();
        services.getTopRatedMovies(accountApp.getUSER_API_KEY())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<SearchMovieResponseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SearchMovieResponseModel searchMovieResponseModel) {
                        movies.setValue(searchMovieResponseModel.getMovieDataList());

                    }

                    @Override
                    public void onError(Throwable e) {


                    }

                    @Override
                    public void onComplete() {


                    }
                });
        return movies;
    }

    public MutableLiveData<List<MovieData>> getMoviesByPopularity() {

        AccountApp accountApp = services.retriveUserAccountLocalSaved();
        services.getPopularMovies(accountApp.getUSER_API_KEY())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<SearchMovieResponseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SearchMovieResponseModel searchMovieResponseModel) {
                        movies.setValue(searchMovieResponseModel.getMovieDataList());

                    }

                    @Override
                    public void onError(Throwable e) {


                    }

                    @Override
                    public void onComplete() {


                    }
                });
        return movies;
    }

    private Observable<List<MovieData>> queryInBackground(Context context) {

        return Observable.create(e -> {

            ContentResolver resolver = context.getContentResolver();
            Cursor cursors = resolver.query(AppContract.CONTENT_URI, ProjectionHelper.getMovieDetailsProjection(), null, null, null);

            assert cursors != null;
            cursors.moveToFirst();

            List<MovieData> movies = new ArrayList<>();

            while (cursors.moveToNext()) {

                movies.add(ContentValuesParser.extractMovieFromCursor(cursors));

            }

            e.onNext(movies);

        });
    }


    @SuppressLint("CheckResult")
    public MutableLiveData<List<MovieData>> getFavorites(Context context) {

        queryInBackground(context).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<List<MovieData>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<MovieData> movieData) {

                        movies.setValue(movieData);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        return movies;

    }

}
