package com.tmartins.task.moviesapp.ui.movieDetail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tmartins.task.moviesapp.R;
import com.tmartins.task.moviesapp.core.model.Part;
import com.tmartins.task.moviesapp.ui.base.BaseRecyclerViewAdapter;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;

public class MovieDetailCollectionAdapter extends
        BaseRecyclerViewAdapter<MovieDetailCollectionAdapter.CollectionItemViewHolder> {

    private List<Part> parts;

    public MovieDetailCollectionAdapter(@NonNull List<Part> parts) {
        this.parts = parts;
    }

    @Override
    public MovieDetailCollectionAdapter.CollectionItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_collection_part, viewGroup, false);
        return new MovieDetailCollectionAdapter.CollectionItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        super.onBindViewHolder(viewHolder, i);
        MovieDetailCollectionAdapter.CollectionItemViewHolder partViewHolder =
                (MovieDetailCollectionAdapter.CollectionItemViewHolder) viewHolder;
        Part part = getItem(i);

        Glide.with(partViewHolder.movieImage)
                .load(part.getBackgroundImage())
                .into(partViewHolder.movieImage);
        partViewHolder.votesText.setText(
                String.format(Locale.getDefault(), "%d votes", part.getVoteCount()));
    }

    @Override
    public int getItemCount() {
        return parts.size();
    }

    public void updateData(List<Part> partList) {
        this.parts.clear();
        this.parts.addAll(partList);
        notifyDataSetChanged();
    }

    public Part getItem(int position) {
        if (position < 0 || position >= parts.size()) {
            throw new InvalidParameterException("Invalid item index");
        }

        return parts.get(position);
    }

    class CollectionItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_movie_votes)
        TextView votesText;
        @BindView(R.id.iv_movie)
        ImageView movieImage;

        public CollectionItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

}
