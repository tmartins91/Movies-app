package com.tmartins.task.moviesapp.ui.movieDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    @BindView(R.id.title_text_view)
    TextView titleTextView;
    @BindView(R.id.content_text_view)
    TextView contentTextView;
    @BindView(R.id.collection_text_view)
    TextView collectionContentTextView;
    @BindView(R.id.paralax_image)
    ImageView paralaxImage;
    @BindView(R.id.scroll_content)
    RelativeLayout scrollContent;

    @Inject
    MovieDetailPresenter presenter;

    private MovieDetailCollectionAdapter collectionAdapter;
    private long currentMovieId;

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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return  true;
        }
        return super.onOptionsItemSelected(item);

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
        collectionAdapter.setOnItemClickListener(
                (view, position) -> openDetailActivity(collectionAdapter.getItem(position).getId())
        );
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
        titleTextView.setVisibility(View.GONE);
        contentTextView.setVisibility(View.GONE);
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
        currentMovieId = getIntent().getLongExtra("MOVIE_ID", -1);
        if (currentMovieId != -1){
            presenter.loadMovieDetail(currentMovieId);
        }
    }

    private void showNotificationMessage(String message) {
        contentTextView.setVisibility(View.VISIBLE);
        contentTextView.setText(message);
    }

    private void bindData(Movie movie){
        Glide.with(paralaxImage)
                .load(movie.getBackgroundImage(Movie.W500))
                .into(paralaxImage);

        titleTextView.setVisibility(View.VISIBLE);
        titleTextView.setText(movie.getOriginalTitle());

        contentTextView.setVisibility(View.VISIBLE);
        contentTextView.setText(movie.getOverview());

        loadCollectionIfIsPart(movie);
    }

    private void loadCollectionIfIsPart(Movie movie){
        if (movie.getCollection() != null){
            collectionRecyclerView.setVisibility(View.VISIBLE);
            presenter.loadCollectionDetails(movie.getCollection().getId());

            collectionContentTextView.setVisibility(View.VISIBLE);
        }
    }

    private void openDetailActivity(long id){
        if (currentMovieId != id){
            Intent detailIntent = new Intent(getBaseContext(), MovieDetailActivity.class);
            detailIntent.putExtra("MOVIE_ID", id);
            startActivity(detailIntent);
        }else {
            Snackbar.make(scrollContent, R.string.movie_already_selected, Snackbar.LENGTH_SHORT).show();
        }
    }

}
