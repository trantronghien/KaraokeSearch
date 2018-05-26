package com.example.admin.karaokesearch.views.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admin.karaokesearch.R;
import com.example.admin.karaokesearch.models.SongTable;
import com.example.admin.karaokesearch.presenter.BaseSongPresenter;
import com.example.admin.karaokesearch.util.ConfigSongTable;
import com.example.admin.karaokesearch.util.UtilHelper;
import com.example.admin.karaokesearch.views.BaseAbstractView;
import com.example.admin.karaokesearch.views.activity.DetailItemActivity;
import com.example.admin.karaokesearch.views.activity.MainActivity;
import com.example.admin.karaokesearch.views.adapter.SongRecyclerAdapter;

/**
 * Created by admin on 3/10/2017.
 */

public abstract class BaseFragment<T extends SongTable> extends BaseUtilFragment implements
        SongRecyclerAdapter.OnItemClickListener ,
        SongRecyclerAdapter.OnCheckedChangeListener ,
        BaseAbstractView {

    public MainActivity activity;
    protected BaseSongPresenter presenter;
    protected SongRecyclerAdapter adapter;
    private LinearLayoutManager layoutManagerRecy;
    private String TAG = "BaseFragment";

    protected BaseFragment(){}
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = (MainActivity) getActivity();
    }

    protected MainActivity getActivitys(){
        return activity;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(getLayoutResource(), container, false);
    }

    /**
     * khởi tạo hay bắt sự kiện phải trong hàm onViewCreated BaseFragment
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewCreated(view);
    }

    protected void onViewCreated(View view){
        inItView(view);
        inItData();
        presenter = getPresenter();
        presenter.setNotifyFavoriteChangeListener(activity);
        adapter = getAdapter();
        listener();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    protected void showSnackbar(String content){
        Snackbar.make(getView(), content, Snackbar.LENGTH_LONG).show();
    }

    //============================================================================
    //                              todo Adapter implements
    //============================================================================
    // using position when check parent fragment
    protected int positionChecked;
    @Override
    public void onCheckedChanged(final boolean isChecked, final int positionAt, final long idSong) {
        this.getActivitys().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int favorite = isChecked ? ConfigSongTable.FAVORITE : ConfigSongTable.UNFAVORITE;
                positionChecked = positionAt;
                presenter.updateFavorite(idSong, favorite , positionAt);
            }
        });
    }
    //============================================================================
    //                              implement View pattent
    //============================================================================
    @Override
    public void isUpdatedCheckBox(int favorite, int positionAt) {
        if (adapter != null)
            Log.i("DEG", "check: " + favorite
                    + "\t" + adapter.getItem(positionAt).getZSNAME()
                    + "\t pos: " + positionAt
                    + "\t list size: " + adapter.getItemCount());
        String songName = adapter.getItem(positionAt).getZSNAME();
        String textSnack = "";
        if (favorite == 0)
            textSnack = UtilHelper.getStringRes(R.string.uncheck_favorite, songName);
        else
            textSnack = UtilHelper.getStringRes(R.string.check_favorite, songName);
        showSnackbar(textSnack);
    }

    /**
     * item long click item RecycleView
     * @param position
     */
    @Override
    public void onItemLongClick(int position) {
        Toast.makeText(activity, "LongClick "+ position, Toast.LENGTH_SHORT).show();
    }

    /**
     * item click item RecycleView
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        T object = (T) adapter.getItem(position);
        Intent intent = new Intent(getActivity().getBaseContext(), DetailItemActivity.class);
        intent.putExtra("object", object);
        activity.startActivity(intent);
    }

    protected LinearLayoutManager getLayoutManager(){
        layoutManagerRecy = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        layoutManagerRecy.setAutoMeasureEnabled(false);
        return layoutManagerRecy;
    }

    protected abstract void inItView(View view);
    protected abstract void inItData();
    protected abstract SongRecyclerAdapter getAdapter();
    protected abstract BaseSongPresenter getPresenter();
    protected abstract void listener();

    /**
     * change presenter when change Table
     * @param presenter
     */
    protected void notifyChangePresenter(BaseSongPresenter presenter){
        this.presenter = presenter;
    }
    // for override
    protected int getIdTable(){
        return 1;
    }

    // test
    @Override
    public void notifyDataCheckChange() {
        super.notifyDataCheckChange();
        adapter.notifyDataSetChanged();
    }
}
