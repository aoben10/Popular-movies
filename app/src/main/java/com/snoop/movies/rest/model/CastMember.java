package com.snoop.movies.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by galaxywizkid on 12/9/16.
 */

public class CastMember {

    @SerializedName("name")
    private String name;
    @SerializedName("character")
    private String characterPlayed;
    @SerializedName("profile_path")
    private String profileURL;
    @SerializedName("id")
    private int personId;

    public String getCharacterPlayed() {
        return characterPlayed;
    }

    public void setCharacterPlayed(String characterPlayed) {
        this.characterPlayed = characterPlayed;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
