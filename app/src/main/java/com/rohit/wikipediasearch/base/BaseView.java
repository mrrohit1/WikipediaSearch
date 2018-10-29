package com.rohit.wikipediasearch.base;

public interface BaseView {

    void updateProgressBar(int visibility);

    void showSnackBarMessage(String message, boolean hasRetry);

}
