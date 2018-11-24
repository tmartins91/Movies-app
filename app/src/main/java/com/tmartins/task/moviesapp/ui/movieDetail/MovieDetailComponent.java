package com.tmartins.task.moviesapp.ui.movieDetail;

import com.tmartins.task.moviesapp.core.MoviesRepositoryComponent;
import com.tmartins.task.moviesapp.helpers.schedulers.SchedulerModule;
import com.tmartins.task.moviesapp.ui.base.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(
        modules = {MovieDetailPresenterModule.class, SchedulerModule.class},
        dependencies = {MoviesRepositoryComponent.class}
)
public interface MovieDetailComponent {

    void inject(MovieDetailActivity movieDetailActivity);

}