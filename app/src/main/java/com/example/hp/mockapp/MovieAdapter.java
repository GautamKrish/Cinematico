package com.example.hp.mockapp;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by HP on 26-08-2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    static String BASE_URL = "http://image.tmdb.org/t/p/w500/";
    private Context context;
    private ArrayList<MovieAttributes> mMovieAttributes = new ArrayList<>();
    private MovieAdapterOnClickHandler mClickHandler;
    private int lastPosition = -1;

    public MovieAdapter(Context context, MovieAdapterOnClickHandler clickHandler) {
        this.context = context;
        mClickHandler = clickHandler;
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieAdapterViewHolder holder, int position) {
        Glide
                .with(context)
                .load(MovieAdapter.BASE_URL + mMovieAttributes.get(position).get_poster_path())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter().placeholder(R.color.colorAccent)
                .into(holder.androidImage);
        setAnimation(holder.itemView, position);
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_from_bottom);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return mMovieAttributes.size();
    }

    public void setMovieData(ArrayList<MovieAttributes> movieAttributes) {
        mMovieAttributes = movieAttributes;
        notifyDataSetChanged();
    }

    public Cursor swapCursor(Cursor newCursor, Cursor oldCursor) {
        if (oldCursor != null) oldCursor.close();
        if (newCursor != null) {
            this.notifyDataSetChanged();

        }
        return newCursor;
    }

    public interface MovieAdapterOnClickHandler {
        void handleClicks(int position);
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView androidImage;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            androidImage = itemView.findViewById(R.id.movie_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClickHandler.handleClicks(getAdapterPosition());
                }
            });
        }

    }
}
