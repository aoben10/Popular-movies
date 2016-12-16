package com.snoop.movies;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.snoop.movies.fragments.CastFragment;
import com.snoop.movies.fragments.OverviewFragment;

import static com.snoop.movies.extras.Constants.EXTRA_MOVIE_ID;
import static com.snoop.movies.extras.Constants.EXTRA_MOVIE_OVERVIEW;
import static com.snoop.movies.extras.Constants.EXTRA_MOVIE_TITLE;
import static com.snoop.movies.extras.Constants.MAX_NUM_PAGES;
import static com.snoop.movies.extras.Constants.MOVIES_CAST;
import static com.snoop.movies.extras.Constants.MOVIES_INFO;
import static com.snoop.movies.extras.Constants.MOVIES_OVERVIEW;
import static com.snoop.movies.extras.UrlEndpoints.URL_YOUTUBE_PREFIX;

public class MovieDetails extends AppCompatActivity implements InfoFragment.PassDataToFAB {

    private ViewPager viewPager;
    private TabLayout mtabLayout;
    private String movieOverview;
    private int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        initializeScreen();
    }

    private void initializeScreen() {
        String movieTitle = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            movieTitle = extras.getString(EXTRA_MOVIE_TITLE);
            movieOverview = extras.getString(EXTRA_MOVIE_OVERVIEW);
            movieId = extras.getInt(EXTRA_MOVIE_ID);
        }

        setTitle(movieTitle);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(getPagerAdapter());
        viewPager.setOffscreenPageLimit(2);

        mtabLayout = (TabLayout) findViewById(R.id.tab_layout);
        if (mtabLayout != null) {
            mtabLayout.setupWithViewPager(viewPager);
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public PagerAdapter getPagerAdapter() {
        return new DetMoviesPagerAdapter(getSupportFragmentManager());
    }

    @Override
    public void onDataPass(final String homepageURL, final String youtubeKey) {


        android.support.design.widget.FloatingActionButton fab = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fab);
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

        ImageView item1 = new ImageView(this);
        item1.setImageResource(R.drawable.ic_action_video);

        ImageView item2 = new ImageView(this);
        item2.setImageResource(R.drawable.ic_camera_alt);

        ImageView item3 = new ImageView(this);
        item3.setImageResource(R.drawable.ic_home);

        SubActionButton openYoutubeVideoButtn = itemBuilder.setContentView(item1).build();
        SubActionButton button2 = itemBuilder.setContentView(item2).build();
        SubActionButton openURLsubActionButton = itemBuilder.setContentView(item3).build();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            openYoutubeVideoButtn.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
            button2.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
            openURLsubActionButton.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
        } else {
            ViewCompat.setBackgroundTintList(openYoutubeVideoButtn, getResources().getColorStateList(R.color.colorAccent));
            ViewCompat.setBackgroundTintList(button2, getResources().getColorStateList(R.color.colorAccent));
            ViewCompat.setBackgroundTintList(openURLsubActionButton, getResources().getColorStateList(R.color.colorAccent));
        }
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(180, 180);
        openYoutubeVideoButtn.setLayoutParams(params);
        button2.setLayoutParams(params);
        openURLsubActionButton.setLayoutParams(params);

        //attach the sub buttons
        FloatingActionMenu.Builder floatingActionMenu = new FloatingActionMenu.Builder(this);
        if (!homepageURL.isEmpty()) {
            openURLsubActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(homepageURL)));
                }
            });
            floatingActionMenu.addSubActionView(openURLsubActionButton);
        }

        if (!youtubeKey.isEmpty()) {
            openYoutubeVideoButtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String youtbeURL = URL_YOUTUBE_PREFIX + youtubeKey;
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtbeURL)));
                }
            });
            floatingActionMenu.addSubActionView(openYoutubeVideoButtn);
        }

        floatingActionMenu.attachTo(fab).build();

    }

    public class DetMoviesPagerAdapter extends FragmentStatePagerAdapter {

        public DetMoviesPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            switch (position) {
                case MOVIES_INFO:
                    fragment = InfoFragment.newInstance(movieId);
                    break;
                case MOVIES_CAST:
                    fragment = CastFragment.newInstance(movieId);
                    break;
                case MOVIES_OVERVIEW:
                    fragment = OverviewFragment.newInstance(movieOverview);
                    break;
                default:
                    return null;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return MAX_NUM_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.info_string);
                case 1:
                    return getString(R.string.cast_string);
                case 2:
                    return getString(R.string.overview_string);
                default:
                    return getString(R.string.none_string);
            }
        }
    }

}
