package com.tmartins.task.moviesapp.ui.movies;

import com.tmartins.task.moviesapp.core.MoviesRepositoryComponent;
import com.tmartins.task.moviesapp.helpers.schedulers.SchedulerModule;
import com.tmartins.task.moviesapp.ui.base.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(
        modules = {MoviesPresenterModule.class, SchedulerModule.class},
        dependencies = {MoviesRepositoryComponent.class}
        )
public interface MoviesComponent {

    void inject(MoviesActivity moviesActivity);

}
