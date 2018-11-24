package com.tmartins.task.moviesapp.ui.movieDetail;

import com.tmartins.task.moviesapp.core.model.MovieDetail;
import com.tmartins.task.moviesapp.ui.base.BasePresenter;

public interface MovieDetailContract {

    interface View {
        void showMovieDetail(MovieDetail movieDetail);

        void showErrorMessage(String error);

        void stopLoadingIndicator();
    }

    interface Presenter extends BasePresenter<MovieDetailContract.View> {
        void loadMovieDetail(long id);
    }

}
