package com.tmartins.task.moviesapp.core;

import com.tmartins.task.moviesapp.core.datasource.MoviesDataSource;
import com.tmartins.task.moviesapp.core.datasource.MoviesRemoteDataSource;
import com.tmartins.task.moviesapp.core.datasource.Remote;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MoviesRepositoryModule {

    @Provides
    @Remote
    @Singleton
    public MoviesDataSource provideDataSource(MoviesRemoteDataSource dataSource) {
        return dataSource;
    }

}
