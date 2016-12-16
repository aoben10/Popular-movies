package com.snoop.movies.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.snoop.movies.R;
import com.snoop.movies.extras.Constants;
import com.snoop.movies.rest.model.MovieItem;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.snoop.movies.extras.UrlEndpoints.URL_POSTER_PREFIX;

/**
 * Created by galaxywizkid on 12/9/16.
 */

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.RecHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<MovieItem> movieItemList = new ArrayList<>();
    private DateFormat dateFormat = new SimpleDateFormat("yyyy", Locale.US);
    private RecyclerOnItemClickListener mItemClickListener;

    public RecAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public RecHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.movie_custom_row, parent, false);
        return new RecHolder(view);
    }

    @Override
    public void onBindViewHolder(RecHolder holder, int position) {
        MovieItem currentMovie = movieItemList.get(position);

        holder.movieTitle.setText(currentMovie.getTitle());

        if (currentMovie.getDate() != null) {
            String formattedDate = "(" + dateFormat.format(currentMovie.getDate()) + ")";
            holder.releaseDate.setText(formattedDate);
        } else {
            holder.releaseDate.setText(Constants.NA);
        }

        String posterURLSuffix = currentMovie.getPosterURL();
        if (posterURLSuffix != null && !posterURLSuffix.isEmpty()) {
            String urlThumbnail = URL_POSTER_PREFIX + posterURLSuffix;
            holder.posterIcon.setScaleType(ImageView.ScaleType.FIT_XY);

            // Fetch images using Picasso library and set them to corresponding ImageView
            Picasso.with(context)
                    .load(urlThumbnail)
                    .into(holder.posterIcon);
        }else{
            holder.posterIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);
            Drawable profileDrawable = ContextCompat.getDrawable(context, R.drawable.im_image_shutter);
            holder.posterIcon.setImageDrawable(profileDrawable);
        }

    }

    @Override
    public int getItemCount() {
        return movieItemList.size();
    }

    public void setMovieItemList(List<MovieItem> movieItemList){
        this.movieItemList = movieItemList;
    }

    public void addItems(List<MovieItem> items){
        final int positionStart = movieItemList.size() + 1;
        movieItemList.addAll(items);
        notifyItemRangeInserted(positionStart, items.size());
    }

    class RecHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_movie_title) TextView movieTitle;
        @BindView(R.id.tv_release_date) TextView releaseDate;
        @BindView(R.id.im_movie_poster_icon) ImageView posterIcon;

        public RecHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public void setCommunicator(final RecyclerOnItemClickListener recyclerOnItemClickListener) {
        this.mItemClickListener = recyclerOnItemClickListener;
    }

    //The interface will allow us to communicate with the Activity, without the RecAdapter class
    //having to hold the Activity in memory
    public interface RecyclerOnItemClickListener {
        /**
         * Called when an item is clicked.
         *
         * @param position Position of the item that was clicked.
         */
        void onItemClick(int position);
    }
}
