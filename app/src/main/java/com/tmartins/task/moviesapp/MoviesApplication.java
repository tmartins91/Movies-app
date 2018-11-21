package com.tmartins.task.moviesapp;

import android.app.Application;

import com.tmartins.task.moviesapp.core.DaggerMoviesRepositoryComponent;
import com.tmartins.task.moviesapp.core.MoviesRepositoryComponent;

import timber.log.Timber;

public class MoviesApplication extends Application {

    private MoviesRepositoryComponent repositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initializeDependencies();

        Timber.plant(new Timber.DebugTree());
    }

    private void initializeDependencies() {
        repositoryComponent = DaggerMoviesRepositoryComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    public MoviesRepositoryComponent getMoviesRepositoryComponent() {
        return repositoryComponent;
    }

}
