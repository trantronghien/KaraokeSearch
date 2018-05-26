package com.example.admin.karaokesearch.views.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.admin.karaokesearch.R;
import com.example.admin.karaokesearch.util.UtilHelper;

/**
 * Created by admin on 3/7/2017.
 */

public class UpdateFragment extends BaseUtilFragment implements View.OnClickListener{

    private Button btnUpdate;
    private Button btnCheckVersion;
    private View view;
//    private static UpdateFragment updateFragment;
//
//    private UpdateFragment(){}
//    public static UpdateFragment getInstance(){
//        if(updateFragment != null){
//            return updateFragment;
//        }
//        return new UpdateFragment();
//    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_update;
    }

    @Override
    public int getIdFragment() {
        return UtilHelper.FRAGMENT_UPDATE;
    }
}
