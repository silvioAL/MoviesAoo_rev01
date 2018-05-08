package com.example.silvio.moviesaoo.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.databinding.ObservableField;
import android.net.Uri;
import android.os.CountDownTimer;
import android.widget.Toast;

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
import com.example.silvio.moviesaoo.util.StringUtil;
import com.example.silvio.moviesaoo.view.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.ObservableOnSubscribe;
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
    private GetMoviesReviewsResponseModel reviewsResponseModel;
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
        movieId = String.valueOf(movieData.getId());
        title.set(movieData.getTitle());
        originalLenguage.set(movieData.getOriginal_language());
        originalTitle.set(movieData.getOriginal_title());
        overview.set(movieData.getOverview());
        if (!movieData.getRelease_date().isEmpty()) {
            releaseDate.set(StringUtil.formateDateFromServerResource(movieData.getRelease_date()));
        }
        popularity.set(movieData.getPopularity());
        voteAvarage.set(movieData.getVote_average());
        voteCount.set(movieData.getVote_count());
        movieImgUrl.set(stringInteraction.getStringFromResource(R.string.image_service_URL) + movieData.getPoster_path());

    }

    public void checkForRatings() {

        services.getMovieReviews(stringInteraction.getStringFromResource(R.string.API_KEY), movieId).enqueue(new Callback<GetMoviesReviewsResponseModel>() {
            @Override
            public void onResponse(Call<GetMoviesReviewsResponseModel> call, Response<GetMoviesReviewsResponseModel> response) {
                if (response.isSuccessful() && response.body().getResults().size() > 0) {
                    reviewsResponseModel = response.body();
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
        notification.showLoading();
        services.getMovieTrailers(stringInteraction.getStringFromResource(R.string.API_KEY), movieId).enqueue(new Callback<GetMovieTrailersResponseModel>() {
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

    private List<String> extractKeys() {
        List<String> keys = new ArrayList<>();
        for (GetMovieTrailersResponseBlock block : responseModel.getBlock()) {
            keys.add(block.getKey());
        }
        return keys;
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

    public void saveAsFavorite() {

        final io.reactivex.Observable save = io.reactivex.Observable.create((ObservableOnSubscribe) e -> {

            ContentResolver resolver = contextInteraction.getContext().getContentResolver();
            resolver.insert(AppContract.CONTENT_URI, ContentValuesParser.convertMovieDetailsToDBValues(currentMovie));

        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.single().when(flowableFlowable -> new Completable() {
            @Override
            protected void subscribeActual(CompletableObserver s) {

                Toast.makeText(contextInteraction.getContext(), "Salvo localmente", Toast.LENGTH_SHORT).show();
            }
        }));
        save.subscribe();

    }

}
