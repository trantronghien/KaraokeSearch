package com.example.admin.karaokesearch.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admin.karaokesearch.adapter.OnLoadMoreListener;
import com.example.admin.karaokesearch.adapter.SongRecyclerAdapter;
import com.example.admin.karaokesearch.ui.activity.MainActivity;

/**
 * Created by admin on 3/10/2017.
 */

public abstract class BaseFragment extends Fragment implements SongRecyclerAdapter.OnItemClickListener , SongRecyclerAdapter.OnCheckedChangeListener  {

    static MainActivity activity;

    public BaseFragment(){
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = (MainActivity) getActivity();
    }

    protected static MainActivity getActivitys(){
        return activity;
    }

    protected void inIt(View view){}
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(getLayoutResource(), container, false);
    }

    protected abstract int getLayoutResource();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewCreated(view );
    }

    protected void onViewCreated(View view){}

    /**
     * item click item RecycleView
     * @param position
     */

    @Override
    public void onItemClick(int position) {

    }

    /**
     * item long click item RecycleView
     * @param position
     */
    @Override
    public void onItemLongClick(int position) {
        Toast.makeText(activity, "LongClick "+ position, Toast.LENGTH_SHORT).show();
    }
}
