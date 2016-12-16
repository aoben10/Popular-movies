package com.snoop.movies.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by galaxywizkid on 12/10/16.
 */

public class ProdCompObj {

    @SerializedName("name")
    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
