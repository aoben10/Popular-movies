package com.snoop.movies.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by galaxywizkid on 12/10/16.
 */

public class GenreObj {

    @SerializedName("name")
    private String genreName;

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
