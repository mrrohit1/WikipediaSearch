package com.rohit.wikipediasearch.dependencies;


import com.rohit.wikipediasearch.constant.Constant;
import com.rohit.wikipediasearch.datasource.repository.WikiClient;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = NetworkModule.class)
public class ClientModule {

    @Provides
    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl(Constant.UrlConstant.BASE_URL)
                .build();
    }

    @Provides
    public WikiClient provideWikiClient(Retrofit retrofit) {
        return retrofit.create(WikiClient.class);
    }
}
