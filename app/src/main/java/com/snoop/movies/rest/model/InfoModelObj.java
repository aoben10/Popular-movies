package com.snoop.movies.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by galaxywizkid on 12/10/16.
 */

public class InfoModelObj {

    @SerializedName("title")
    private String title;
    @SerializedName("status")
    private String status;
    @SerializedName("tagline")
    private String tagLine;
    @SerializedName("runtime")
    private int runTime;
    @SerializedName("homepage")
    private String homePageURL;
    @SerializedName("backdrop_path")
    private String backdropSuffix;
    @SerializedName("poster_path")
    private String posterSuffix;
    @SerializedName("vote_count")
    private int voteCount;
    @SerializedName("vote_average")
    private float voteAvg;
    @SerializedName("release_date")
    private Date releaseDate;
    @SerializedName("genres")
    private List<GenreObj> genres;
    @SerializedName("production_countries")
    private List<ProdCountryObj> productionCountries;
    @SerializedName("production_companies")
    private List<ProdCompObj> productionCompanies;
    @SerializedName("videos")
    private VideosObj videos;

    public float getVoteAvg() {
        return voteAvg;
    }

    public void setVoteAvg(float voteAvg) {
        this.voteAvg = voteAvg;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public String getHomePageURL() {
        return homePageURL;
    }

    public void setHomePageURL(String homePageURL) {
        this.homePageURL = homePageURL;
    }


    public String getBackdropSuffix() {
        return backdropSuffix;
    }

    public void setBackdropSuffix(String backdropSuffix) {
        this.backdropSuffix = backdropSuffix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterSuffix() {
        return posterSuffix;
    }

    public void setPosterSuffix(String posterSuffix) {
        this.posterSuffix = posterSuffix;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<GenreObj> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreObj> genres) {
        this.genres = genres;
    }

    public List<ProdCountryObj> getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(List<ProdCountryObj> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public List<ProdCompObj> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<ProdCompObj> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public VideosObj getVideos() {
        return videos;
    }

    public void setVideos(VideosObj videos) {
        this.videos = videos;
    }
}
