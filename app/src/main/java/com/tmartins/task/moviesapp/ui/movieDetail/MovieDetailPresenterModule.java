package com.tmartins.task.moviesapp.ui.movieDetail;

import dagger.Module;
import dagger.Provides;

@Module
public class MovieDetailPresenterModule {

    private MovieDetailContract.View view;

    public MovieDetailPresenterModule(MovieDetailContract.View view) {
        this.view = view;
    }

    @Provides
    public MovieDetailContract.View provideView() {
        return view;
    }

}
