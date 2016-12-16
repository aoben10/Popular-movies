package com.snoop.movies.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by galaxywizkid on 12/9/16.
 */

public class JsonResponseModel {


    @SerializedName("results")
    private List<MovieItem> results = null;

    public List<MovieItem> getResults() {
        return results;
    }

    public void setResults(List<MovieItem> results) {
        this.results = results;
    }
}
