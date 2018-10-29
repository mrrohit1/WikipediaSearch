package com.rohit.wikipediasearch.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.rohit.wikipediasearch.R;
import com.rohit.wikipediasearch.base.BaseFragment;
import com.rohit.wikipediasearch.datasource.model.Page;
import com.rohit.wikipediasearch.datasource.repository.DataSource;
import com.rohit.wikipediasearch.detailwebview.DetailWebViewFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {

    @BindView(R.id.searchView)
    SearchView searchView;

    @BindView(R.id.recyclerViewSearch)
    RecyclerView recyclerViewSearch;

    @BindView(R.id.imageViewWikiIcon)
    ImageView imageViewWikiIcon;

    @BindView(R.id.textViewWikipediaSearch)
    TextView textViewWikipediaSearch;

    @BindView(R.id.rootConstraintLayout)
    ConstraintLayout constraintLayout;

    ConstraintSet set;
    HomeAdapter wikipediaSearchAdapter;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private Snackbar snackbar;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        wikipediaSearchAdapter = new HomeAdapter(mPresenter);
        set = new ConstraintSet();

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onSearchViewClick();
            }
        });

        recyclerViewSearch.setLayoutManager(layoutManager);
        recyclerViewSearch.setAdapter(wikipediaSearchAdapter);
        recyclerViewSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && layoutManager.findLastVisibleItemPosition() == wikipediaSearchAdapter.getItemCount() - 1) {
                    mPresenter.onScrollToEnd();
                }
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mPresenter.onSearchViewClosed();
                return false;
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mPresenter.onSearchViewTextChanged();
                wikipediaSearchAdapter.clearResult();
                return false;
            }
        });
    }

    @Override
    public HomePresenter createPresenter() {
        return new HomePresenter(getContext(), this, new DataSource(getContext()));
    }

    @Override
    public void showSearchResults(List<Page> pages) {
        wikipediaSearchAdapter.addResult(pages);
    }

    @Override
    public void clearSearchResult() {
        wikipediaSearchAdapter.clearResult();
    }

    @Override
    public void animateAndCloseSearchView() {
        imageViewWikiIcon.setVisibility(View.VISIBLE);
        textViewWikipediaSearch.setVisibility(View.VISIBLE);
        set.clone(constraintLayout);
        set.clear(R.id.searchView, ConstraintSet.TOP);
        set.connect(R.id.searchView, ConstraintSet.TOP, R.id.imageViewWikiIcon, ConstraintSet.BOTTOM);
        Transition transition = new ChangeBounds();
        transition.setInterpolator(new AnticipateOvershootInterpolator(1.0f));
        transition.setDuration(500);
        TransitionManager.beginDelayedTransition(constraintLayout, transition);
        set.applyTo(constraintLayout);
    }

    @Override
    public void animateAndOpenSearchView() {
        imageViewWikiIcon.setVisibility(View.GONE);
        textViewWikipediaSearch.setVisibility(View.GONE);
        set.clone(constraintLayout);
        set.clear(R.id.searchView, ConstraintSet.TOP);
        set.connect(R.id.searchView, ConstraintSet.TOP, R.id.rootConstraintLayout, ConstraintSet.TOP);
        Transition transition = new ChangeBounds();
        transition.setInterpolator(new AnticipateOvershootInterpolator(1.0f));
        transition.setDuration(500);
        TransitionManager.beginDelayedTransition(constraintLayout, transition);
        set.applyTo(constraintLayout);
    }

    @Override
    public String getSearchViewText() {
        return searchView.getQuery().toString();
    }

    @Override
    public void openDetailScreen(Page page) {
        DetailWebViewFragment detailWebViewFragment = DetailWebViewFragment.newInstance(page.pageid);
        detailWebViewFragment.show(getChildFragmentManager(), "");
    }

    @Override
    public void updateProgressBar(int visibility) {
        progressBar.setVisibility(visibility);
    }

    @Override
    public void showSnackBarMessage(String message, boolean hasRetry) {
        if (snackbar != null && snackbar.isShown()) {
            return;
        }
        int snackBarLength = hasRetry ? Snackbar.LENGTH_INDEFINITE : Snackbar.LENGTH_SHORT;
        snackbar = Snackbar.make(coordinatorLayout, message, snackBarLength);
        View view = snackbar.getView();
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        if (hasRetry) {
            snackbar.setAction("Retry", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                    mPresenter.onSnackbarRetry();
                }
            });
        }
        snackbar.show();
    }
}
