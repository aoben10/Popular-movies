package com.snoop.movies.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by galaxywizkid on 12/9/16.
 */

public class CastJsonModel {

    @SerializedName("cast")
    private List<CastMember> results = null;

    public List<CastMember> getResults() {
        return results;
    }

    public void setResults(List<CastMember> results) {
        this.results = results;
    }
}
