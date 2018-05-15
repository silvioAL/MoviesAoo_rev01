package com.example.silvio.moviesaoo.data.entity;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by silvio on 25/12/2017.
 */


public class MovieData implements Parcelable {

    private Integer dbId;
    public static final Creator<MovieData> CREATOR = new Creator<MovieData>() {
        @Override
        public MovieData createFromParcel(Parcel in) {
            return new MovieData(in);
        }

        @Override
        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };
    @SerializedName("adult")
    private boolean adult;
    @SerializedName("backdrop_path")
    private String backdrop_path;
    @SerializedName("genre_ids")
    private ArrayList<String> genre_ids;
    @SerializedName("original_language")
    private String original_language;
    @SerializedName("original_title")
    private String original_title;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String release_date;
    @SerializedName("poster_path")
    private String poster_path;
    @SerializedName("popularity")
    private String popularity;
    @SerializedName("title")
    private String title;
    @SerializedName("video")
    private String video;
    @SerializedName("vote_average")
    private String vote_average;
    @SerializedName("vote_count")
    private String vote_count;
    private Boolean isFavorite;
    @SerializedName("id")
    private String movieId;

    public MovieData() {

    }

    public MovieData(boolean adult, String backdrop_path, ArrayList<String> genre_ids, String movieId, String original_language, String original_title, String overview, String release_date, String poster_path, String popularity, String title, String video, String vote_average, String vote_count) {
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

    protected MovieData(Parcel in) {
        if (in.readByte() == 0) {
            dbId = null;
        } else {
            dbId = in.readInt();
        }
        movieId = in.readString();
        adult = in.readByte() != 0;
        backdrop_path = in.readString();
        genre_ids = in.createStringArrayList();
        original_language = in.readString();
        original_title = in.readString();
        overview = in.readString();
        release_date = in.readString();
        poster_path = in.readString();
        popularity = in.readString();
        title = in.readString();
        video = in.readString();
        vote_average = in.readString();
        vote_count = in.readString();
        byte tmpIsFavorite = in.readByte();
        isFavorite = tmpIsFavorite == 0 ? null : tmpIsFavorite == 1;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        if (dbId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(dbId);
        }
        dest.writeString(movieId);
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(backdrop_path);
        dest.writeStringList(genre_ids);
        dest.writeString(original_language);
        dest.writeString(original_title);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(poster_path);
        dest.writeString(popularity);
        dest.writeString(title);
        dest.writeString(video);
        dest.writeString(vote_average);
        dest.writeString(vote_count);
        dest.writeByte((byte) (isFavorite == null ? 0 : isFavorite ? 1 : 2));
    }


}
