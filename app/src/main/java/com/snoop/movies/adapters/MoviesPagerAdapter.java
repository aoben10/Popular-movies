package com.snoop.movies.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.snoop.movies.UpcomingFragment;
import com.snoop.movies.fragments.NowPlayingFragment;
import com.snoop.movies.fragments.PopularFragment;
import com.snoop.movies.fragments.TopRatedFragment;

import static com.snoop.movies.extras.Constants.MOVIES_NOW_PLAYING;
import static com.snoop.movies.extras.Constants.MOVIES_POPULAR;
import static com.snoop.movies.extras.Constants.MOVIES_UPCOMING;

/**
 * Created by galaxywizkid on 12/7/16.
 */

public class MoviesPagerAdapter extends FragmentStatePagerAdapter {

    public MoviesPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case MOVIES_UPCOMING:
                //fragment = new BlankFragment();
                fragment = new UpcomingFragment();
                break;
            case MOVIES_NOW_PLAYING:
                //fragment = new BlankFragment();

                fragment = new NowPlayingFragment();
                break;
            case MOVIES_POPULAR:
                //fragment = new BlankFragment();

                fragment = new PopularFragment();
                break;
            default:
                //fragment = new BlankFragment();
                fragment = new TopRatedFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "UPCOMING";
            case 1:
                return "NOW PLAYING";
            case 2:
                return "POPULAR";
            case 3:
                return "TOP RATED";
            default:
                return "None";
        }
    }
}
