package com.rohit.wikipediasearch.datasource.repository;

import com.rohit.wikipediasearch.constant.Constant;
import com.rohit.wikipediasearch.datasource.model.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WikiClient {

    @GET(Constant.UrlConstant.SEARCH_URL)
    Call<Response> getResult(@Query(Constant.Query.SEARCH_QUERY) String searchKey, @Query(Constant.Query.OFFSET) int offset);
}
