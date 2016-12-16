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
import com.snoop.movies.rest.model.CastMember;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.snoop.movies.extras.UrlEndpoints.URL_POSTER_PREFIX;

/**
 * Created by galaxywizkid on 12/10/16.
 */
public class CastRecAdapter extends RecyclerView.Adapter<CastRecAdapter.RecHolder> {

    public static final String LOG_TAG = CastRecAdapter.class.getSimpleName();

    private Context context;
    private LayoutInflater inflater;
    private List<CastMember> castMemberList = new ArrayList<>();
    private RecyclerOnItemClickListener mItemClickListener;

    public CastRecAdapter(Context context, List<CastMember> castMemberList, RecyclerOnItemClickListener recyclerOnItemClickListener) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.castMemberList = castMemberList;
        this.mItemClickListener = recyclerOnItemClickListener;
    }

    @Override
    public RecHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cast_custom_row, parent, false);
        return new RecHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecHolder holder, int position) {
        CastMember castMember = castMemberList.get(position);

        holder.castMemberName.setText(castMember.getName());

        if (castMember.getCharacterPlayed() != null) {
            holder.castRole.setText(castMember.getCharacterPlayed());
        } else {
            holder.castRole.setText(Constants.NA);
        }

        String posterURLSuffix = castMember.getProfileURL();
        if (posterURLSuffix != null && !posterURLSuffix.isEmpty()) {
            String urlThumbnail = URL_POSTER_PREFIX + posterURLSuffix;

            // Fetch images using Picasso library and set them to corresponding ImageView
            Picasso.with(context)
                    .load(urlThumbnail)
                    .placeholder(R.drawable.im_image_shutter)
                    .into(holder.castProfileImage);
        } else {
            Drawable profileDrawable = ContextCompat.getDrawable(context, R.drawable.im_image_shutter);
            holder.castProfileImage.setImageDrawable(profileDrawable);
        }

    }

    @Override
    public int getItemCount() {
        return castMemberList.size();
    }

    class RecHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_cast_member) TextView castMemberName;
        @BindView(R.id.tv_cast_role) TextView castRole;
        @BindView(R.id.im_profile_image) ImageView castProfileImage;

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
