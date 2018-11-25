package com.tmartins.task.moviesapp.ui.movieDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tmartins.task.moviesapp.R;
import com.tmartins.task.moviesapp.core.model.CollectionDetails;
import com.tmartins.task.moviesapp.core.model.Movie;
import com.tmartins.task.moviesapp.ui.base.BaseActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends BaseActivity implements MovieDetailContract.View {

    @BindView(R.id.recycler_view_collection)
    RecyclerView collectionRecyclerView;
    @BindView(R.id.text_view)
    TextView textView;
    @BindView(R.id.paralax_image)
    ImageView paralaxImage;

    @Inject
    MovieDetailPresenter presenter;

    private MovieDetailCollectionAdapter collectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        initActionBar();

        ButterKnife.bind(this);

        initializePresenter();
        initViews();
        setTitle(getString(R.string.title_activity_movie_details));

        loadDetailsForMovie();
    }

    @Override
    public void showMovieDetail(Movie movie) {
        bindData(movie);
    }

    @Override
    public void showCollectionDetails(CollectionDetails collectionDetails) {
        collectionAdapter = new MovieDetailCollectionAdapter(new ArrayList<>());
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false);
        collectionRecyclerView.setLayoutManager(layoutManager);
        collectionRecyclerView.setAdapter(collectionAdapter);
        collectionRecyclerView.setItemAnimator(new DefaultItemAnimator());
        collectionAdapter.updateData(collectionDetails.getParts());
    }

    @Override
    public void showErrorMessage(String error) {
        showNotificationMessage(error);
    }

    @Override
    public void stopLoadingIndicator() {

    }

    private void initializePresenter() {
        DaggerMovieDetailComponent.builder()
                .movieDetailPresenterModule(new MovieDetailPresenterModule(this))
                .moviesRepositoryComponent(getMoviesRepositoryComponent())
                .build()
                .inject(this);
    }

    private void initViews() {
        textView.setVisibility(View.GONE);
        collectionRecyclerView.setVisibility(View.GONE);
    }

    private void initActionBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void loadDetailsForMovie(){
        long movieId = getIntent().getLongExtra("MOVIE_ID", -1);
        if (movieId != -1){
            presenter.loadMovieDetail(movieId);
        }
    }

    private void showNotificationMessage(String message) {
        textView.setVisibility(View.VISIBLE);
        textView.setText(message);
    }

    private void bindData(Movie movie){
        Glide.with(paralaxImage)
                .load(movie.getBackgroundImage())
                .into(paralaxImage);

        textView.setText(movie.getOverview());
        textView.setVisibility(View.VISIBLE);

        loadCollectionIfIsPart(movie);
    }

    private void loadCollectionIfIsPart(Movie movie){
        if (movie.getCollection() != null){
            collectionRecyclerView.setVisibility(View.VISIBLE);
            presenter.loadCollectionDetails(movie.getCollection().getId());
        }
    }

}
