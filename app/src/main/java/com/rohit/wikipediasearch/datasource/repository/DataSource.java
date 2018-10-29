package com.rohit.wikipediasearch.datasource.repository;

import android.content.Context;

import com.rohit.wikipediasearch.datasource.model.Response;
import com.rohit.wikipediasearch.dependencies.ContextModule;
import com.rohit.wikipediasearch.dependencies.DaggerMainComponent;

import javax.inject.Inject;

import retrofit2.Call;

public class DataSource implements DataSourceContract {

    private Context mContext;

    @Inject
    WikiClient wikiClient;

    public DataSource(Context mContext) {
        this.mContext = mContext;
        DaggerMainComponent.builder()
                .contextModule(new ContextModule(mContext))
                .build().inject(this);
    }


    @Override
    public Call<Response> onFetchSearchResult(String searchKeyword, int offset) {
        return wikiClient.getResult(searchKeyword, offset);
    }

}
