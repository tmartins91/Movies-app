package com.tmartins.task.moviesapp.ui.movies;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tmartins.task.moviesapp.R;
import com.tmartins.task.moviesapp.core.model.Movie;
import com.tmartins.task.moviesapp.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesActivity extends BaseActivity implements MoviesContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView moviesRecyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.text_view)
    TextView textView;

    private MoviesAdapter adapter;

    @Inject
    MoviesPresenter presenter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        ButterKnife.bind(this);

        initializePresenter();
        setTitle(getString(R.string.app_name));
        initViews();
    }

    private void initializePresenter() {
        DaggerMoviesComponent.builder()
                .moviesPresenterModule(new MoviesPresenterModule(this))
                .moviesRepositoryComponent(getMoviesRepositoryComponent())
                .build()
                .inject(this);
    }

    private void initViews() {
        adapter = new MoviesAdapter(new ArrayList<>());
        moviesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        moviesRecyclerView.setAdapter(adapter);
        moviesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(
                (view, position) -> presenter.getMovie(adapter.getItem(position).getId())
        );

        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(() -> presenter.loadMovies());

        textView.setVisibility(View.GONE);
    }

    @Override
    public void showMovies(List<Movie> movies) {
        textView.setVisibility(View.GONE);
        adapter.updateData(movies);
    }

    @Override
    public void showNoDataMessage() {
        showNotificationMessage(getString(R.string.msg_no_data));
    }

    @Override
    public void showErrorMessage(String error) {
        showNotificationMessage(error);
    }

    @Override
    public void stopLoadingIndicator() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showMovieDetail(Movie movie) {
        //TODO Implement movie detail
        Snackbar.make(refreshLayout, movie.getOriginalTitle(), Snackbar.LENGTH_SHORT).show();
    }

    private void showNotificationMessage(String message) {
        textView.setVisibility(View.VISIBLE);
        textView.setText(message);
    }

}
