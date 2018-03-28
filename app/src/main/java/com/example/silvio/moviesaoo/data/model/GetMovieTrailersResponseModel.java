package com.example.silvio.moviesaoo.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by silvioallgayertrindade on 06/03/2018.
 */

public class GetMovieTrailersResponseModel {

    @SerializedName("id")
    private String resId;
    @SerializedName("results")
    private List<GetMovieTrailersResponseBlock> block;

    public GetMovieTrailersResponseModel(String resId, List<GetMovieTrailersResponseBlock> block) {
        this.resId = resId;
        this.block = block;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public List<GetMovieTrailersResponseBlock> getBlock() {
        return block;
    }

    public void setBlock(List<GetMovieTrailersResponseBlock> block) {
        this.block = block;
    }
}
