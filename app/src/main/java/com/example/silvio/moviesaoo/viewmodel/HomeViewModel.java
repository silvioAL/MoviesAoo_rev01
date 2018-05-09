package com.example.silvio.moviesaoo.viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.example.silvio.moviesaoo.R;
import com.example.silvio.moviesaoo.data.entity.MovieData;
import com.example.silvio.moviesaoo.data.local.AppContract;
import com.example.silvio.moviesaoo.data.model.SearchMovieResponseModel;
import com.example.silvio.moviesaoo.interfaces.ContextInteraction;
import com.example.silvio.moviesaoo.interfaces.GenericNotification;
import com.example.silvio.moviesaoo.interfaces.GenericStringInteraction;
import com.example.silvio.moviesaoo.interfaces.HomeInteraction;
import com.example.silvio.moviesaoo.service.AccountApp;
import com.example.silvio.moviesaoo.service.AccountServices;
import com.example.silvio.moviesaoo.util.ContentValuesParser;
import com.example.silvio.moviesaoo.util.ProjectionHelper;
import com.example.silvio.moviesaoo.view.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by silvio on 24/12/2017.
 */

public class HomeViewModel extends ViewModel {

    private AccountServices services;
    private GenericNotification notification;
    private GenericStringInteraction stringInteraction;
    private ContextInteraction contextInteraction;
    private HomeInteraction interaction;
    MutableLiveData<List<MovieData>> DTOList = new MutableLiveData<>();

    @Inject
    public HomeViewModel(AccountServices services) {
        this.services = services;
    }

    public void setNotification(GenericNotification notification) {
        this.notification = notification;
    }

    public void setInteraction(ContextInteraction interaction) {
        this.contextInteraction = interaction;
    }

    public void setStringInteraction(GenericStringInteraction stringInteraction) {
        this.stringInteraction = stringInteraction;
    }

    public void setHomeInteraction(HomeInteraction interaction) {
        this.interaction = interaction;
    }


    public MutableLiveData<List<MovieData>> getMoviesByPopularity() {
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
                        notification.hideLoading();
                        DTOList.postValue(searchMovieResponseModel.getMovieDataList());
                        interaction.fetchList(DTOList.getValue());
                    }

                    @Override
                    public void onError(Throwable e) {
                        notification.hideLoading();
                        Toast.makeText(contextInteraction.getContext()
                                , stringInteraction.getStringFromResource(R.string.could_not_find_movies_genre)
                                , Toast.LENGTH_SHORT).show();
                        redirectOnError(e);

                    }

                    @Override
                    public void onComplete() {


                    }
                });

        return DTOList;
    }

    public MutableLiveData<List<MovieData>> getMoviesByRating() {
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
                        notification.hideLoading();
                        DTOList.postValue(searchMovieResponseModel.getMovieDataList());
                        interaction.fetchList(DTOList.getValue());
                    }

                    @Override
                    public void onError(Throwable e) {
                        notification.hideLoading();
                        Toast.makeText(contextInteraction.getContext()
                                , stringInteraction.getStringFromResource(R.string.could_not_find_movies_genre)
                                , Toast.LENGTH_SHORT).show();
                        redirectOnError(e);

                    }

                    @Override
                    public void onComplete() {


                    }
                });
        return DTOList;

    }

    @SuppressLint("CheckResult")
    public MutableLiveData<List<MovieData>> getFavorites() {

        queryInBackground().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<List<MovieData>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        notification.showLoading();
                    }

                    @Override
                    public void onNext(List<MovieData> movieData) {
                        notification.hideLoading();
                        DTOList.postValue(movieData);
                        interaction.fetchList(DTOList.getValue());
                    }

                    @Override
                    public void onError(Throwable e) {
                        notification.openAlertDialog(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        return DTOList;

    }

    private Observable<List<MovieData>> queryInBackground() {

        return Observable.create(e -> {

            ContentResolver resolver = contextInteraction.getContext().getContentResolver();
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

    private void redirectOnError(Throwable t) {
        new CountDownTimer(6000, 1000) {

            public void onTick(long millisUntilFinished) {
                notification.openAlertDialog(t.getMessage().toString() + "\n"
                        + stringInteraction.getStringFromResource(R.string.redirecting)
                        + millisUntilFinished / 1000
                        + stringInteraction.getStringFromResource(R.string.seconds));
            }

            public void onFinish() {
                Intent intent = new Intent(contextInteraction.getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                contextInteraction.getContext().startActivity(intent);
            }
        }.start();
    }
}
