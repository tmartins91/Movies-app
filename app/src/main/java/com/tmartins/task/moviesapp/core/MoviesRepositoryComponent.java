package com.tmartins.task.moviesapp.core;

import com.tmartins.task.moviesapp.ContextModule;
import com.tmartins.task.moviesapp.core.datasource.MoviesRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { MoviesRepositoryModule.class, ContextModule.class, ApiServiceModule.class})
public interface MoviesRepositoryComponent {

    MoviesRepository provideMoviesRepository();

}
