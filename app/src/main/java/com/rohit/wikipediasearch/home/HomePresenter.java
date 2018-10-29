package com.rohit.wikipediasearch.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.rohit.wikipediasearch.datasource.model.Page;
import com.rohit.wikipediasearch.datasource.model.Response;
import com.rohit.wikipediasearch.datasource.repository.DataSourceContract;
import com.rohit.wikipediasearch.utils.NetworkUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View view;
    private DataSourceContract dataSourceContract;
    private int offset;
    private boolean isFetchEnabled;
    private String searchKey;
    private Context context;

    public HomePresenter(Context context, HomeContract.View view, DataSourceContract dataSourceContract) {
        this.view = view;
        this.dataSourceContract = dataSourceContract;
        this.context = context;
    }

    @Override
    public void onSnackbarRetry() {
        fetchSearchResult(view.getSearchViewText(), offset);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onSearchViewClosed() {
        view.animateAndCloseSearchView();
    }

    @Override
    public void onSearchViewTextChanged() {
        isFetchEnabled = true;
        view.clearSearchResult();
        offset = 0;
        fetchSearchResult(view.getSearchViewText(), offset);
    }

    @Override
    public void onSearchItemClick(Page page) {
        view.openDetailScreen(page);
    }

    @Override
    public void onSearchViewClick() {
        view.animateAndOpenSearchView();
    }

    @Override
    public void onScrollToEnd() {
        if (isFetchEnabled) {
            fetchSearchResult(view.getSearchViewText(), ++offset);
            isFetchEnabled = false;
        }
    }

    private void fetchSearchResult(final String text, final int offset) {
        view.updateProgressBar(View.VISIBLE);
        searchKey = text + "*" + (offset * 10);
        dataSourceContract.onFetchSearchResult(text, offset * 10).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call, @NonNull retrofit2.Response<Response> response) {
                List<Page> pageList;
                if (response.body() != null && searchKey.equals(text + "*" + (offset * 10))) {
                    pageList = response.body().query.pages;
                    isFetchEnabled = !(pageList.size() <= 0);
                    view.showSearchResults(pageList);
                }
                if (response.raw().cacheResponse() != null && response.raw().networkResponse() == null) {
                    view.showSnackBarMessage("Results are from cache, Please turn your internet ON", true);
                }
                view.updateProgressBar(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                if (!NetworkUtils.isInternetAvaialble(context)) {
                    view.showSnackBarMessage("No Internet Connection", true);
                } else {
                    view.showSnackBarMessage("Network Error, Please try after sometime", true);
                }
                view.updateProgressBar(View.GONE);
            }
        });
    }

}
