package com.rohit.wikipediasearch.home;

import com.rohit.wikipediasearch.base.BasePresenter;
import com.rohit.wikipediasearch.base.BaseView;
import com.rohit.wikipediasearch.datasource.model.Page;

import java.util.List;

public interface HomeContract {

    interface Presenter extends BasePresenter {

        void onSearchViewClosed();

        void onSearchViewTextChanged();

        void onSearchItemClick(Page page);

        void onSearchViewClick();

        void onScrollToEnd();

    }

    interface View extends BaseView {

        void showSearchResults(List<Page> pages);

        void clearSearchResult();

        void animateAndCloseSearchView();

        void animateAndOpenSearchView();

        String getSearchViewText();

        void openDetailScreen(Page page);

    }
}
