package com.example.silvio.moviesaoo.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import com.example.silvio.moviesaoo.R;
import com.example.silvio.moviesaoo.data.entity.MovieData;
import com.example.silvio.moviesaoo.data.local.dao.DAOService;
import com.example.silvio.moviesaoo.data.model.GetMoviesGenresResponseModel;
import com.example.silvio.moviesaoo.data.model.GetSelectedGenreMoviesListResponseModel;
import com.example.silvio.moviesaoo.data.model.MovieGenre;
import com.example.silvio.moviesaoo.data.model.SearchMovieResponseModel;
import com.example.silvio.moviesaoo.inject.scopes.MoviesAppScope;
import com.example.silvio.moviesaoo.interfaces.ContextInteraction;
import com.example.silvio.moviesaoo.interfaces.GenericNotification;
import com.example.silvio.moviesaoo.interfaces.GenericStringInteraction;
import com.example.silvio.moviesaoo.interfaces.HomeInteraction;
import com.example.silvio.moviesaoo.service.APIError;
import com.example.silvio.moviesaoo.service.AccountApp;
import com.example.silvio.moviesaoo.service.AccountServices;
import com.example.silvio.moviesaoo.util.PreferenceStorageUtil;
import com.example.silvio.moviesaoo.view.LoginActivity;
import com.example.silvio.moviesaoo.view.MoviesListActivity;

import java.util.ArrayList;

import javax.inject.Inject;

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
 * Created by silvio on 24/12/2017.
 */

@MoviesAppScope
public class HomeViewModel extends ViewModel {

    public ObservableField<String> movieName = new ObservableField<String>();
    public ObservableField<String> movieGenre = new ObservableField<String>();
    private ObservableField<Boolean> getByRating = new ObservableField<>(true);
    private AccountServices services;
    private GenericNotification notification;
    private GenericStringInteraction stringInteraction;
    private ContextInteraction contextInteraction;
    private HomeInteraction interaction;
    private GetMoviesGenresResponseModel genreslist;
    private DAOService daoService;

    @Inject
    public HomeViewModel(AccountServices services, DAOService daoService) {
        this.services = services;
        this.daoService = daoService;
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

    public void loadMovieGenres() {

        AccountApp accountApp = services.retriveUserAccountLocalSaved();
        services.getGenres(accountApp.getUSER_API_KEY(), stringInteraction.getStringFromResource(R.string.app_lenguage)).enqueue(new Callback<GetMoviesGenresResponseModel>() {
            @Override
            public void onResponse(Call<GetMoviesGenresResponseModel> call, Response<GetMoviesGenresResponseModel> response) {
                if (response.isSuccessful()) {
                    notification.hideLoading();
                    genreslist = response.body();
                    interaction.fetchGenres(genreslist);
                } else {
                    APIError error = services.parseError(response);
                    notification.hideLoading();
                    notification.openAlertDialog(error.toString());
                }
            }

            @Override
            public void onFailure(Call<GetMoviesGenresResponseModel> call, Throwable t) {
                notification.hideLoading();
                redirectOnError(t);
            }
        });
    }

    public void onCheckStateSet() {

        if (getByRating.get()) {
            getByRating.set(false);
        } else {
            getByRating.set(true);
        }
    }

    public void onSearchClick() {

        //do not query
        if (movieGenre.get().isEmpty() && movieName.get() == null) {
            Toast.makeText(contextInteraction.getContext()
                    , stringInteraction
                            .getStringFromResource(R.string.select_a_title_or_choose_genre)
                    , Toast.LENGTH_LONG).show();
        } else
            //query for genre if there is not text
            if (!movieGenre.get().isEmpty() && movieName.get() == null) {
                String genreId = extractSelectedGenreId(interaction.getGenreSelection());
                getGenreSelection(genreId);
            } else

                //query for selected text
                if (movieName.get() != null) {
                    getSelection();
                }
    }

    private void getSelection() {

        notification.showLoading();
        if (!getByRating.get()) {
            getMoviesByPopularity();
        } else {
            getMoviesByRating();
        }
    }

    private void getMoviesByPopularity() {
        AccountApp accountApp = services.retriveUserAccountLocalSaved();
        services.getPopularMovies(accountApp.getUSER_API_KEY(), movieName.get()).enqueue(new Callback<SearchMovieResponseModel>() {
            @Override
            public void onResponse(Call<SearchMovieResponseModel> call, Response<SearchMovieResponseModel> response) {

                if (response.isSuccessful()) {
                    notification.hideLoading();
                    ArrayList<MovieData> listOfMovies = (ArrayList<MovieData>) response.body().getMovieDataList();
                    if (listOfMovies.isEmpty()) {
                        Toast.makeText(contextInteraction.getContext()
                                , stringInteraction.getStringFromResource(R.string.could_not_find_movies_genre)
                                , Toast.LENGTH_SHORT).show();
                        notification.hideLoading();
                    } else {
                        notification.hideLoading();
                        redirectToFoundMoviesList(listOfMovies);
                    }

                } else {
                    APIError error = services.parseError(response);
                    notification.hideLoading();
                    notification.openAlertDialog(error.toString());
                }
            }

            @Override
            public void onFailure(Call<SearchMovieResponseModel> call, Throwable t) {
                notification.hideLoading();
                redirectOnError(t);
            }
        });
    }

    private void getMoviesByRating() {
        AccountApp accountApp = services.retriveUserAccountLocalSaved();
        services.getTopRatedMovies(accountApp.getUSER_API_KEY(), movieName.get()).enqueue(new Callback<SearchMovieResponseModel>() {
            @Override
            public void onResponse(Call<SearchMovieResponseModel> call, Response<SearchMovieResponseModel> response) {

                if (response.isSuccessful()) {
                    notification.hideLoading();
                    ArrayList<MovieData> listOfMovies = (ArrayList<MovieData>) response.body().getMovieDataList();
                    if (listOfMovies.isEmpty()) {
                        Toast.makeText(contextInteraction.getContext()
                                , stringInteraction.getStringFromResource(R.string.could_not_find_movies_genre)
                                , Toast.LENGTH_SHORT).show();
                        notification.hideLoading();
                    } else {
                        notification.hideLoading();
                        redirectToFoundMoviesList(listOfMovies);
                    }

                } else {
                    APIError error = services.parseError(response);
                    notification.hideLoading();
                    notification.openAlertDialog(error.toString());
                }
            }

            @Override
            public void onFailure(Call<SearchMovieResponseModel> call, Throwable t) {
                notification.hideLoading();
                redirectOnError(t);
            }
        });
    }

    public void getGenreSelection(String genreId) {

        notification.showLoading();
        AccountApp app;
        app = services.retriveUserAccountLocalSaved();
        services.getGenreSelection(app.getUSER_API_KEY(), stringInteraction.getStringFromResource(R.string.app_lenguage), genreId).enqueue(new Callback<GetSelectedGenreMoviesListResponseModel>() {
            @Override
            public void onResponse(Call<GetSelectedGenreMoviesListResponseModel> call, Response<GetSelectedGenreMoviesListResponseModel> response) {

                if (response.isSuccessful()) {
                    notification.hideLoading();
                    ArrayList<MovieData> listOfMovies = (ArrayList<MovieData>) response.body().getResults();
                    if (listOfMovies.isEmpty()) {
                        Toast.makeText(contextInteraction.getContext()
                                , stringInteraction.getStringFromResource(R.string.could_not_find_movies_genre)
                                , Toast.LENGTH_SHORT).show();
                        notification.hideLoading();
                        redirectToFoundMoviesList(listOfMovies);
                    }

                } else {
                    APIError error = services.parseError(response);
                    notification.hideLoading();
                    notification.openAlertDialog(error.toString());
                }
            }

            @Override
            public void onFailure(Call<GetSelectedGenreMoviesListResponseModel> call, Throwable t) {
                notification.hideLoading();
                redirectOnError(t);
            }
        });
    }

    private boolean checkPreferences() {
        PreferenceStorageUtil preferenceStorageUtil = new PreferenceStorageUtil(contextInteraction.getContext());
        return preferenceStorageUtil.retrieveFilteringMode();
    }


    private void redirectToFoundMoviesList(ArrayList<MovieData> listOfMovies) {

        Intent intent = new Intent(contextInteraction.getContext(), MoviesListActivity.class);
        Bundle bundle = new Bundle();


        if (checkPreferences()) {
            final Observable generate = Observable.create((ObservableOnSubscribe) e -> {
                Cursor cursors = daoService.getAllMovies();

                cursors.moveToFirst();

                ArrayList<MovieData> filtered = new ArrayList<>();

                while (cursors.moveToNext()) {

                    String movieTile = cursors.getString(cursors.getColumnIndex("title"));

                    for (MovieData movieData : listOfMovies) {

                        if (movieData.getTitle().contentEquals(movieTile) || movieData.getOriginal_title().contentEquals(movieTile)) {
                            filtered.add(movieData);
                        }
                    }
                }

                bundle.putSerializable("movies", filtered);
                intent.putExtra("bundle", bundle);

                contextInteraction.getContext().startActivity(intent);

            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

            generate.subscribe(new Observer() {
                @Override
                public void onSubscribe(Disposable d) {
                    Log.d(">>>", "SUBSCRIBED");
                }

                @Override
                public void onNext(Object o) {
                    Log.d(">>>", "NEXT");
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onComplete() {
                    Log.d(">>>", "COMPLETED");
                }
            });

        } else {

            bundle.putSerializable("movies", listOfMovies);
            intent.putExtra("bundle", bundle);

            contextInteraction.getContext().startActivity(intent);
        }
    }

    private String extractSelectedGenreId(String selectedTitle) {
        for (MovieGenre genre : genreslist.getGenreList()) {

            if (selectedTitle.equals(genre.getName())) {
                return genre.getId();
            }
        }
        return "";
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
