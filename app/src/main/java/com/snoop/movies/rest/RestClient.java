package com.snoop.movies.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.snoop.movies.extras.UrlEndpoints.URL_TMDB_GENERIC;

/**
 * Created by galaxywizkid on 12/9/16.
 */

public class RestClient {

    private static RestClient sInstance;
    private static MoviesAPI service;

    private RestClient() {

        // Retrofit library is used for making HTTP calls
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_TMDB_GENERIC)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        service = retrofit.create(MoviesAPI.class);
    }

    public static RestClient getInstance(){
        if(sInstance == null){
            sInstance = new RestClient();
        }
        return sInstance;
    }

    public static MoviesAPI getService() {
        return service;
    }


}
