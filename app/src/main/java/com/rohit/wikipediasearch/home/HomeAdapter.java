package com.rohit.wikipediasearch.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rohit.wikipediasearch.R;
import com.rohit.wikipediasearch.datasource.model.Page;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.WikipediaSearchViewHolder> {

    private List<Page> pageList = new ArrayList<>();

    private HomeContract.Presenter mPresenter;

    public HomeAdapter(HomeContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @NonNull
    @Override
    public WikipediaSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        return new WikipediaSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WikipediaSearchViewHolder holder, int position) {
        Page page = pageList.get(position);

        if (!TextUtils.isEmpty(page.title)) {
            holder.textViewItemTitle.setText(page.title);
        }

        if (page.terms != null && page.terms.description != null && page.terms.description.size() > 0) {
            holder.textViewItemDescription.setText(page.terms.description.get(0));
        }

        if (page.thumbnail != null && !TextUtils.isEmpty(page.thumbnail.source)) {
            Picasso.get().load(page.thumbnail.source).into(holder.imageViewItemThumbnail);
        } else {
            Picasso.get().load(R.drawable.ic_wikipedia).into(holder.imageViewItemThumbnail);

        }
    }

    @Override
    public int getItemCount() {
        return pageList.size();
    }

    class WikipediaSearchViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageViewItemThumbnail)
        ImageView imageViewItemThumbnail;

        @BindView(R.id.textViewItemDescription)
        TextView textViewItemDescription;

        @BindView(R.id.textViewItemTitle)
        TextView textViewItemTitle;

        WikipediaSearchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.onSearchItemClick(pageList.get(getAdapterPosition()));
                }
            });
        }
    }

    public void clearResult() {
        if (pageList != null) {
            pageList.clear();
            notifyDataSetChanged();
        }
    }

    public void addResult(List<Page> pages) {
        pageList.addAll(pages);
        notifyDataSetChanged();
    }
}
