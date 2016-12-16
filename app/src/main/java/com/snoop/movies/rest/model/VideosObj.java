package com.snoop.movies.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by galaxywizkid on 12/11/16.
 */
public class VideosObj {

    @SerializedName("results")
    private List<VideoResult> videoResults;

    public List<VideoResult> getVideoResults() {
        return videoResults;
    }

    public void setVideoResults(List<VideoResult> videoResults) {
        this.videoResults = videoResults;
    }
}
