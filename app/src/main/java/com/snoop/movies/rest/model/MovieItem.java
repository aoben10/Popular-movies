package com.snoop.movies.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by galaxywizkid on 12/9/16.
 */

public class MovieItem {

    @SerializedName("title")
    private String title;
    @SerializedName("release_date")
    private Date date;
    @SerializedName("poster_path")
    private String posterURL;
    @SerializedName("id")
    private int id;
    @SerializedName("overview")
    private String overview;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterSuffix) {
        this.posterURL = posterSuffix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
