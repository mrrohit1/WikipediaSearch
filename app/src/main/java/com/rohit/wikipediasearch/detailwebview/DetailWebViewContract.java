package com.rohit.wikipediasearch.detailwebview;

import com.rohit.wikipediasearch.base.BasePresenter;
import com.rohit.wikipediasearch.base.BaseView;

public interface DetailWebViewContract {

    interface Presenter extends BasePresenter {
        void onWebLoadComplete(int visibility);
    }

    interface View extends BaseView {

        void loadWebViewUrl(int pageId);


    }

}
