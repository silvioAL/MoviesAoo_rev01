package com.example.silvio.moviesaoo.viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModel;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.ObservableField;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.example.silvio.moviesaoo.BuildConfig;
import com.example.silvio.moviesaoo.R;
import com.example.silvio.moviesaoo.data.entity.MovieData;
import com.example.silvio.moviesaoo.data.local.AppContract;
import com.example.silvio.moviesaoo.data.model.GetMovieTrailersResponseBlock;
import com.example.silvio.moviesaoo.data.model.GetMovieTrailersResponseModel;
import com.example.silvio.moviesaoo.data.model.GetMoviesReviewsResponseModel;
import com.example.silvio.moviesaoo.inject.scopes.MoviesAppScope;
import com.example.silvio.moviesaoo.interfaces.ContextInteraction;
import com.example.silvio.moviesaoo.interfaces.GenericNotification;
import com.example.silvio.moviesaoo.interfaces.GenericStringInteraction;
import com.example.silvio.moviesaoo.interfaces.MoviesDetailsInteraction;
import com.example.silvio.moviesaoo.service.AccountServices;
import com.example.silvio.moviesaoo.util.ContentValuesParser;
import com.example.silvio.moviesaoo.util.ProjectionHelper;
import com.example.silvio.moviesaoo.util.StringUtil;
import com.example.silvio.moviesaoo.view.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by silvio on 26/12/2017.
 */

@MoviesAppScope
public class MovieDetailsViewModel extends ViewModel {

    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> originalLenguage = new ObservableField<>();
    public ObservableField<String> originalTitle = new ObservableField<>();
    public ObservableField<String> currentId = new ObservableField<>();
    public ObservableField<String> overview = new ObservableField<>();
    public ObservableField<String> releaseDate = new ObservableField<>();
    public ObservableField<String> popularity = new ObservableField<>();
    public ObservableField<String> voteAvarage = new ObservableField<>();
    public ObservableField<String> voteCount = new ObservableField<>();
    public ObservableField<Boolean> hasTrailer = new ObservableField<>();
    public ObservableField<Boolean> hasReview = new ObservableField<>();
    public ObservableField<String> movieImgUrl = new ObservableField<>();

    private AccountServices services;
    private GenericStringInteraction stringInteraction;
    private String movieId;
    private GenericNotification notification;
    private ContextInteraction contextInteraction;
    private GetMovieTrailersResponseModel responseModel;
    private MoviesDetailsInteraction moviesDetailsInteraction;
    private MovieData currentMovie;

    @Inject
    public MovieDetailsViewModel(AccountServices accountServices) {
        services = accountServices;
    }

    public void setStringInteraction(GenericStringInteraction stringInteraction) {
        this.stringInteraction = stringInteraction;
    }

    public void setMoviesDetailsInteraction(MoviesDetailsInteraction moviesDetailsInteraction) {
        this.moviesDetailsInteraction = moviesDetailsInteraction;
    }

    public void setNotification(GenericNotification notification) {
        this.notification = notification;
    }

    public void setContextInteraction(ContextInteraction contextInteraction) {
        this.contextInteraction = contextInteraction;
    }

    public void fetchField(MovieData movieData) {
        currentMovie = movieData;
        movieId = movieData.getMovieId();
        title.set(movieData.getTitle());
        originalLenguage.set(movieData.getOriginal_language());
        originalTitle.set(movieData.getOriginal_title());
        overview.set(movieData.getOverview());
        if (!movieData.getRelease_date().isEmpty()) {
            releaseDate.set(StringUtil.formateDateFromServerResource(movieData.getRelease_date()));
        }
        currentId.set(movieData.getMovieId());
        popularity.set(movieData.getPopularity());
        voteAvarage.set(movieData.getVote_average());
        voteCount.set(movieData.getVote_count());
        movieImgUrl.set(stringInteraction.getStringFromResource(R.string.image_service_URL) + movieData.getPoster_path());

    }

    public void checkForRatings() {

        services.getMovieReviews(BuildConfig.API_KEY, movieId).enqueue(new Callback<GetMoviesReviewsResponseModel>() {
            @Override
            public void onResponse(Call<GetMoviesReviewsResponseModel> call, Response<GetMoviesReviewsResponseModel> response) {
                if (response.isSuccessful() && response.body().getResults().size() > 0) {

                    hasReview.set(true);
                    moviesDetailsInteraction.fetchReviewsList(response.body().getResults());
                    notification.hideLoading();

                } else {
                    hasTrailer.set(false);
                    notification.hideLoading();
                }
            }

            @Override
            public void onFailure(Call<GetMoviesReviewsResponseModel> call, Throwable t) {
                notification.hideLoading();
                redirectOnError(t);
            }
        });

    }

    public void checkForTrailers() {
        if (isOnline()) {
            notification.showLoading();
            services.getMovieTrailers(BuildConfig.API_KEY, movieId).enqueue(new Callback<GetMovieTrailersResponseModel>() {
                @Override
                public void onResponse(Call<GetMovieTrailersResponseModel> call, Response<GetMovieTrailersResponseModel> response) {

                    if (response.isSuccessful() && response.body().getBlock().size() > 0) {
                        responseModel = response.body();
                        hasTrailer.set(true);
                        moviesDetailsInteraction.fetchTrailersList(extractKeys());
                        notification.hideLoading();

                    } else {
                        hasTrailer.set(false);
                        notification.hideLoading();
                    }

                }

                @Override
                public void onFailure(Call<GetMovieTrailersResponseModel> call, Throwable t) {

                    notification.hideLoading();
                    redirectOnError(t);
                }
            });
        }
    }

    private List<String> extractKeys() {
        List<String> keys = new ArrayList<>();
        for (GetMovieTrailersResponseBlock block : responseModel.getBlock()) {
            keys.add(block.getKey());
        }
        return keys;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) contextInteraction.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void redirectOnError(Throwable t) {
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

    public void openMovie() {

        String id = responseModel.getBlock().get(0).getKey();

        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));

        try {
            contextInteraction.getContext().startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            contextInteraction.getContext().startActivity(webIntent);
        }
    }


    public void changeStatement() {
        if (isOnline()) {
            queryInBackground().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new Observer<List<MovieData>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            notification.showLoading();
                        }

                        @Override
                        public void onNext(List<MovieData> movieData) {

                            if (isFavorite(movieData)) {
                                unmarkAsFavorite();
                            } else {
                                saveAsFavorite();
                            }

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            notification.openAlertDialog(e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            notification.openAlertDialog(contextInteraction.getContext().getResources().getString(R.string.no_connection_av));
        }
    }

    public void saveAsFavorite() {

        final io.reactivex.Observable save = Observable.create(e -> {

            ContentResolver resolver = contextInteraction.getContext().getContentResolver();
            resolver.insert(AppContract.CONTENT_URI, ContentValuesParser.convertMovieDetailsToDBValues(currentMovie));
            moviesDetailsInteraction.changeFabColor(false);

        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.single().when(flowableFlowable -> new Completable() {
            @Override
            protected void subscribeActual(CompletableObserver s) {
                Toast.makeText(contextInteraction.getContext(), "Salvo localmente", Toast.LENGTH_SHORT).show();
                notification.hideLoading();

            }
        }));
        save.subscribe();


    }

    private void unmarkAsFavorite() {
        final io.reactivex.Observable delete = io.reactivex.Observable.create((ObservableOnSubscribe) e -> {

            ContentResolver resolver = contextInteraction.getContext().getContentResolver();

            resolver.delete(Uri.withAppendedPath(AppContract.CONTENT_URI, currentMovie.getDbId().toString()), null, null);
            moviesDetailsInteraction.changeFabColor(true);

        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.single().when(flowableFlowable -> new Completable() {
            @Override
            protected void subscribeActual(CompletableObserver s) {

                Toast.makeText(contextInteraction.getContext(), "Deletado dos favoritos", Toast.LENGTH_SHORT).show();
                notification.hideLoading();
            }
        }));
        delete.subscribe();
    }

    private Observable<List<MovieData>> queryInBackground() {

        return Observable.create(e -> {

            ContentResolver resolver = contextInteraction.getContext().getContentResolver();
            Cursor cursors = resolver.query(AppContract.CONTENT_URI, ProjectionHelper.getMovieDetailsProjection(), null, null, null);


            List<MovieData> movies = new ArrayList<>();

            if (cursors != null && cursors.moveToFirst()) {

                do {
                    movies.add(ContentValuesParser.extractMovieFromCursor(cursors));
                } while (cursors.moveToNext());

                cursors.close();
            }

            e.onNext(movies);
        });
    }

    @SuppressLint("CheckResult")
    public void setupFabStatement() {
        if (isOnline()) {
            queryInBackground().subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new Observer<List<MovieData>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            notification.showLoading();
                        }

                        @Override
                        public void onNext(List<MovieData> movieData) {
                            notification.hideLoading();
                            if (isFavorite(movieData)) {
                                moviesDetailsInteraction.changeFabColor(false);
                            }

                        }

                        @Override
                        public void onError(Throwable e) {
                            notification.openAlertDialog(e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            notification.openAlertDialog(contextInteraction.getContext().getResources().getString(R.string.no_connection_av));
        }
    }

    private boolean isFavorite(List<MovieData> movieData) {

        boolean isFavorite = false;

        for (MovieData movie : movieData) {
            if (movie.getMovieId().equals(currentId.get())) {
                setupCurrentMovieDatabaseId(movie);
                isFavorite = true;
            }
        }
        return isFavorite;
    }

    private void setupCurrentMovieDatabaseId(MovieData found) {
        currentMovie.setDbId(found.getDbId() - 1);

    }
}
