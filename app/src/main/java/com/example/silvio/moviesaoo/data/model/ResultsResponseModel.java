package com.example.silvio.moviesaoo.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by silvioallgayertrindade on 27/03/2018.
 */

public class ResultsResponseModel {

    @SerializedName("author")
    private String author;
    @SerializedName("content")
    private String content;

    public ResultsResponseModel(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
