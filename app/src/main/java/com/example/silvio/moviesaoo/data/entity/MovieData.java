package com.example.silvio.moviesaoo.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.example.silvio.moviesaoo.data.local.AppContract;
import com.example.silvio.moviesaoo.util.RoomTypeConverter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by silvio on 25/12/2017.
 */

@Entity(tableName = AppContract.TABLE_NAME, indices = {@Index(value = "dbId", unique = true)})
@TypeConverters({RoomTypeConverter.class})
public class MovieData implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "dbId")
    private Integer dbId;
    @SerializedName("id")
    @ColumnInfo(name = "movie_id")
    private Integer movieId;
    @SerializedName("adult")
    @ColumnInfo(name = "adult")
    private boolean adult;
    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    private String backdrop_path;
    @SerializedName("genre_ids")
    @ColumnInfo(name = "genre_ids")
    @TypeConverters({RoomTypeConverter.class})
    private ArrayList<String> genre_ids;
    @SerializedName("original_language")
    @ColumnInfo(name = "original_language")
    private String original_language;
    @SerializedName("original_title")
    @ColumnInfo(name = "original_title")
    private String original_title;
    @SerializedName("overview")
    @ColumnInfo(name = "overview")
    private String overview;
    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    private String release_date;
    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    private String poster_path;
    @SerializedName("popularity")
    @ColumnInfo(name = "popularity")
    private String popularity;
    @SerializedName("title")
    @ColumnInfo(name = "title")
    private String title;
    @SerializedName("video")
    @ColumnInfo(name = "video")
    private String video;
    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    private String vote_average;
    @SerializedName("vote_count")
    @ColumnInfo(name = "vote_count")
    private String vote_count;
    @ColumnInfo(name = "isFavorite")
    private Boolean isFavorite;


    public MovieData(boolean adult, String backdrop_path, ArrayList<String> genre_ids, Integer movieId, String original_language, String original_title, String overview, String release_date, String poster_path, String popularity, String title, String video, String vote_average, String vote_count) {
        this.adult = adult;
        this.backdrop_path = backdrop_path;
        this.genre_ids = genre_ids;
        this.movieId = movieId;
        this.original_language = original_language;
        this.original_title = original_title;
        this.overview = overview;
        this.release_date = release_date;
        this.poster_path = poster_path;
        this.popularity = popularity;
        this.title = title;
        this.video = video;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.genre_ids = genre_ids;
        /*
        *
        * MUST NOT BE FAVORTIE BY DEFAULT
        */
        this.isFavorite = false;
    }

    @Ignore
    public MovieData() {

    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getDbId() {
        return dbId;
    }

    public void setDbId(Integer dbId) {
        this.dbId = dbId;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    public ArrayList<String> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(ArrayList<String> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public Integer getId() {
        return movieId;
    }

    public void setId(Integer id) {
        this.movieId = id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

}
