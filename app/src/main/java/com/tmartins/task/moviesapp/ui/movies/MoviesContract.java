package com.tmartins.task.moviesapp.ui.movies;

import com.tmartins.task.moviesapp.core.model.Movie;
import com.tmartins.task.moviesapp.ui.base.BasePresenter;

import java.util.List;

public interface MoviesContract {

    interface View {
        void showMovies(List<Movie> moviesList);

        void showNoDataMessage();

        void showErrorMessage(String error);

        void showMovieDetail(Movie movie);

        void stopLoadingIndicator();
    }

    interface Presenter extends BasePresenter<View> {
        void loadMovies();

        void getMovie(long movieId);
    }

}
