package com.snoop.movies.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by galaxywizkid on 12/11/16.
 */
public class VideoResult {

    @SerializedName("key")
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
