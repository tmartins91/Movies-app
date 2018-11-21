package com.tmartins.task.moviesapp.ui.base;

import android.arch.lifecycle.LifecycleRegistry;
import android.support.v7.app.AppCompatActivity;

import com.tmartins.task.moviesapp.MoviesApplication;
import com.tmartins.task.moviesapp.core.MoviesRepositoryComponent;

public class BaseActivity extends AppCompatActivity {

    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    protected MoviesRepositoryComponent getMoviesRepositoryComponent() {
        return ((MoviesApplication) getApplication()).getMoviesRepositoryComponent();
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }

}
