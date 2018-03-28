package com.example.silvio.moviesaoo.interfaces;

import com.example.silvio.moviesaoo.data.model.GetMovieTrailersResponseModel;
import com.example.silvio.moviesaoo.data.model.GetMoviesGenresResponseModel;
import com.example.silvio.moviesaoo.data.model.GetMoviesReviewsResponseModel;
import com.example.silvio.moviesaoo.data.model.GetSelectedGenreMoviesListResponseModel;
import com.example.silvio.moviesaoo.data.model.LoginResponseModel;
import com.example.silvio.moviesaoo.data.model.SearchMovieResponseModel;
import com.example.silvio.moviesaoo.service.DefaultResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by silvio on 19/12/2017.
 */

public interface NetworkServicesInterfaces {

    @GET("3/authentication/token/new")
    Call<DefaultResponse<LoginResponseModel>> doLogin(@Query("api_key") String apikey);

    @GET("3/genre/movie/list")
    Call<GetMoviesGenresResponseModel> getGenres(@Query("api_key") String apikey, @Query("language") String lenguage);

    @GET("3/genre/{genre_id}/movies")
    Call<GetSelectedGenreMoviesListResponseModel> getSelectedGenreList(@Path("genre_id") String genreId, @Query("api_key") String apikey,  @Query("language") String lenguage);

    @GET("3/movie/popular")
    Call<SearchMovieResponseModel> getPopularMovies(@Query("api_key") String apikey, @Query("query") String queryWord);

    @GET("3/movie/top_rated")
    Call<SearchMovieResponseModel> getTopRatedMovies(@Query("api_key") String apikey, @Query("query") String queryWord);

    @GET("3/movie/{movie_id}/videos")
    Call<GetMovieTrailersResponseModel> getMovieTrailers(@Path("movie_id") String movie_id, @Query("api_key") String apikey);

    @GET("3/movie/{movie_id}/reviews")
    Call<GetMoviesReviewsResponseModel> getMovieReviews(@Path("movie_id") String movie_id, @Query("api_key") String apikey);

}
