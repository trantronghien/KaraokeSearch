package com.example.admin.karaokesearch.views.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by admin on 29/10/2017.
 */

public abstract class BaseUtilFragment extends Fragment implements View.OnClickListener{
    //ko phải fragment trong view page thì set false
    protected boolean fragmentInsidePage = true;

    public String getFragmentName(){
        return this.getClass().getSimpleName()+" {"+BaseFragment.class.getCanonicalName()+"}";
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(getLayoutResource(), container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!fragmentInsidePage)
            Log.i("fragment", "dislay : " + getFragmentName());
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
    }

    protected abstract int getLayoutResource();
    //todo chuyển fragment theo id và show class name fragment dislay
    public abstract int getIdFragment();
    public void notifyDataCheckChange(){}



}
