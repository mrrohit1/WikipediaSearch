package com.rohit.wikipediasearch.detailwebview;

public class DetailWebViewPresenter implements DetailWebViewContract.Presenter {

    private DetailWebViewContract.View view;
    private int pageId;

    public DetailWebViewPresenter(DetailWebViewContract.View view,int pageId) {
        this.view = view;
        this.pageId = pageId;
    }

    @Override
    public void onSnackbarRetry() {

    }

    @Override
    public void onStart() {
        view.loadWebViewUrl(pageId);
    }


    @Override
    public void onWebLoadComplete(int visibility) {
        view.updateProgressBar(visibility);
    }
}
