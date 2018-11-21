package com.tmartins.task.moviesapp.ui.base;

import android.support.v7.widget.RecyclerView;

import io.reactivex.annotations.NonNull;

public abstract class BaseRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecyclerViewListener.OnItemClickListener itemClickListener;
    private RecyclerViewListener.OnItemLongClickListener itemLongClickListener;

    public void setOnItemClickListener(
            @NonNull RecyclerViewListener.OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setOnItemLongClickListener(
            @NonNull RecyclerViewListener.OnItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (itemClickListener != null) {
            viewHolder.itemView.setOnClickListener(view -> itemClickListener.OnItemClick(view, i));
        }
        if (itemLongClickListener != null) {
            viewHolder.itemView.setOnLongClickListener(view -> {
                itemLongClickListener.OnItemLongClick(view, i);
                return true;
            });
        }
    }

}
