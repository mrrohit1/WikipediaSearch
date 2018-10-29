package com.rohit.wikipediasearch.detailwebview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.rohit.wikipediasearch.R;
import com.rohit.wikipediasearch.base.BaseFragment;
import com.rohit.wikipediasearch.constant.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailWebViewFragment extends BaseFragment<DetailWebViewPresenter> implements DetailWebViewContract.View {

    private int pageId;

    @BindView(R.id.webViewDetail)
    WebView webView;

    @BindView(R.id.progressBarDetail)
    ProgressBar progressBar;

    public static DetailWebViewFragment newInstance(int pageId) {
        Bundle args = new Bundle();
        args.putInt("PageId", pageId);
        DetailWebViewFragment fragment = new DetailWebViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_page, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public DetailWebViewPresenter createPresenter() {
        if (getArguments() != null) {
            pageId = getArguments().getInt("PageId");
        }
        return new DetailWebViewPresenter(this, pageId);
    }

    @Override
    public void loadWebViewUrl(int pageId) {
        String url = Constant.UrlConstant.DETAIL_PAGE_URL + pageId;
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                mPresenter.onWebLoadComplete(View.GONE);
            }
        });
    }


    @Override
    public void updateProgressBar(int visibility) {
        progressBar.setVisibility(visibility);
    }

    @Override
    public void showSnackBarMessage(String message, boolean hasRetry) {

    }
}
