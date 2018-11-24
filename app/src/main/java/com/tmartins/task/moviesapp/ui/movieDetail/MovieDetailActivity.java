package com.tmartins.task.moviesapp.ui.movieDetail;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tmartins.task.moviesapp.R;
import com.tmartins.task.moviesapp.core.model.MovieDetail;
import com.tmartins.task.moviesapp.ui.base.BaseActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        initActionBar();

        ButterKnife.bind(this);

        initializePresenter();
        setTitle(getString(R.string.title_activity_movie_details));
        initViews();

        loadDetailsForMovie();
    }

    @Override
    public void showMovieDetail(MovieDetail movieDetail) {
        bindData(movieDetail);
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

    private void bindData(MovieDetail movieDetail){
        Glide.with(paralaxImage)
                .load(movieDetail.getBackgroundImage())
                .into(paralaxImage);

        textView.setText(movieDetail.getOverview());
        textView.setVisibility(View.VISIBLE);
    }

}
