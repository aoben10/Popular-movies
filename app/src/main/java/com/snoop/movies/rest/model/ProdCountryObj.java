package com.snoop.movies.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by galaxywizkid on 12/10/16.
 */

public class ProdCountryObj {

    @SerializedName("name")
    private String countryName;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
