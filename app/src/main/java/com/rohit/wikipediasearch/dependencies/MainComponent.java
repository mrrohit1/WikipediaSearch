package com.rohit.wikipediasearch.dependencies;


import com.rohit.wikipediasearch.datasource.repository.DataSource;

import dagger.Component;

@Component(modules = ClientModule.class)
public interface MainComponent {
    void inject(DataSource dataSource);
}
