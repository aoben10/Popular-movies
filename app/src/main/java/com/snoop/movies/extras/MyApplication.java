package com.snoop.movies.extras;

import android.app.Application;
import android.content.Context;

/**
 * Created by galaxywizkid on 12/8/16.
 */

public class MyApplication extends Application {

    public static final String API_KEY_THE_MOVIE_DATABASE = "Replace with Personal API Key";
    private static MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }
}
