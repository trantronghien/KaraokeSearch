package com.example.admin.karaokesearch.views.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by admin on 9/11/2017.
 */

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener  {
    public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();

    private LinearLayoutManager llm;

    public EndlessRecyclerOnScrollListener(LinearLayoutManager sglm) {
        this.llm = sglm;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (!recyclerView.canScrollVertically(1)) {
            onScrolledToEnd(llm.getItemCount());
        }
    }
    public abstract void onScrolledToEnd(int itemCount);

}
