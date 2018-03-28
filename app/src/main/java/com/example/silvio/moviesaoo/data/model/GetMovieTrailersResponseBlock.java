package com.example.silvio.moviesaoo.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by silvioallgayertrindade on 06/03/2018.
 */

public class GetMovieTrailersResponseBlock implements Serializable {

    @SerializedName("id")
    private String trailerId;
    @SerializedName("iso_639_1")
    private String iso_639_1;
    @SerializedName("iso_3166_1")
    private String iso_3166_1;
    @SerializedName("name")
    private String name;
    @SerializedName("site")
    private String site;
    @SerializedName("size")
    private String size;
    @SerializedName("key")
    private String key;
    @SerializedName("type")
    private String type;

    public GetMovieTrailersResponseBlock(String trailerId, String iso_639_1, String iso_3166_1, String name, String site, String size, String key, String type) {
        this.trailerId = trailerId;
        this.iso_639_1 = iso_639_1;
        this.iso_3166_1 = iso_3166_1;
        this.name = name;
        this.site = site;
        this.size = size;
        this.key = key;
        this.type = type;
    }

    public String getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(String trailerId) {
        this.trailerId = trailerId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
