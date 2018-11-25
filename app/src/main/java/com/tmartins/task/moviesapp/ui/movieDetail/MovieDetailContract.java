package com.tmartins.task.moviesapp.ui.movieDetail;

import com.tmartins.task.moviesapp.core.model.CollectionDetails;
import com.tmartins.task.moviesapp.core.model.Movie;
import com.tmartins.task.moviesapp.ui.base.BasePresenter;

public interface MovieDetailContract {

    interface View {
        void showMovieDetail(Movie movie);

        void showCollectionDetails(CollectionDetails collectionDetails);

        void showErrorMessage(String error);

        void stopLoadingIndicator();
    }

    interface Presenter extends BasePresenter<MovieDetailContract.View> {
        void loadMovieDetail(long id);

        void loadCollectionDetails(long id);
    }

}
