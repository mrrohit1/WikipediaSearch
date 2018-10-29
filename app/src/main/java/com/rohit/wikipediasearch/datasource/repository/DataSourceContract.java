package com.rohit.wikipediasearch.datasource.repository;

import com.rohit.wikipediasearch.datasource.model.Response;

import retrofit2.Call;

public interface DataSourceContract {

    Call<Response> onFetchSearchResult(String searchKeyword, final int offset);

}
