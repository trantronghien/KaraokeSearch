package com.example.admin.karaokesearch.views.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.admin.karaokesearch.R;
import com.example.admin.karaokesearch.models.SongTable;
import com.example.admin.karaokesearch.presenter.BaseSongPresenter;
import com.example.admin.karaokesearch.presenter.RoutePresenter;
import com.example.admin.karaokesearch.util.UtilHelper;
import com.example.admin.karaokesearch.views.FavoriteCallBackView;
import com.example.admin.karaokesearch.views.adapter.SongRecyclerAdapter;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 3/6/2017.
 */

public class FavoriteFragment<T extends SongTable> extends BaseFragment implements
        SuperSwipeRefreshLayout.OnPushLoadMoreListener,
        FavoriteCallBackView
{

    private SongRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private List<SongTable> listSong;
    private Button btnRestore;
    private Button btnBackup;
    private BaseSongPresenter presenter;
    View view;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_favorite;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view != null)
            return view;
        return view = super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void onViewCreated(View view) {
        super.onViewCreated(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void inItView(View view) {
        btnBackup = (Button) view.findViewById(R.id.button_backup_favorite);
        btnRestore = (Button) view.findViewById(R.id.button_restore_favorite);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_favorite);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(getLayoutManager());
    }

    @Override
    protected void inItData() {
        if(listSong == null)
            listSong = new ArrayList<>();
        adapter = new SongRecyclerAdapter<T>((List<T>) listSong, this);
        recyclerView.setAdapter(adapter);
        BaseSongPresenter.getFavoriteList(this);
        presenter = RoutePresenter.RegisterRoutesPresenter(1 ,this);
    }

    @Override
    protected SongRecyclerAdapter getAdapter() {
        return adapter;
    }

    @Override
    protected BaseSongPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void listener() {
        btnRestore.setOnClickListener(this);
        btnBackup.setOnClickListener(this);

    }

    @Override
    public int getIdFragment() {
        return UtilHelper.FRAGMENT_FAVORITE;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_backup_favorite:
                return;
            case R.id.button_restore_favorite:
                return;
        }
    }

    //============================================================================
    //                              todo implements update view
    //============================================================================
//    @Override
//    public void isUpdatedCheckBox(int isUpdated, int positionAt) {
//
//    }

    @Override
    public void UpdateFavoriteList(List listFavorite) {
        if (listFavorite != null || !listFavorite.isEmpty()){
            this.listSong = listFavorite;
            adapter.replaceAll(listSong);
        }
    }
    @Override
    public void onLoadMore() {}
    @Override
    public void onPushDistance(int i) {}
    @Override
    public void onPushEnable(boolean b) {}
}
