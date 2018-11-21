package com.tmartins.task.moviesapp.ui.movies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tmartins.task.moviesapp.R;
import com.tmartins.task.moviesapp.core.model.Movie;
import com.tmartins.task.moviesapp.ui.base.BaseRecyclerViewAdapter;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;

public class MoviesAdapter extends BaseRecyclerViewAdapter<MoviesAdapter.MoviesViewHolder> {

    private List<Movie> movies;

    public MoviesAdapter(@NonNull List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_movie, viewGroup, false);
        return new MoviesViewHolder(view);
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        super.onBindViewHolder(viewHolder, i);
        MoviesViewHolder movieViewHolder = (MoviesViewHolder) viewHolder;
        Movie movie = getItem(i);

        Glide.with(movieViewHolder.movieImage)
                .load(movie.getBackgroundImage())
                .into(movieViewHolder.movieImage);
        movieViewHolder.votesText.setText(
                String.format(Locale.getDefault(), "%d votes", movie.getVoteCount()));
    }

    @Override public int getItemCount() {
        return movies.size();
    }

    public void updateData(List<Movie> movieList) {
        this.movies.clear();
        this.movies.addAll(movieList);
        notifyDataSetChanged();
    }

    public Movie getItem(int position) {
        if (position < 0 || position >= movies.size()) {
            throw new InvalidParameterException("Invalid item index");
        }
        return movies.get(position);
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_movie_votes)
        TextView votesText;
        @BindView(R.id.iv_movie)
        ImageView movieImage;

        public MoviesViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

}
