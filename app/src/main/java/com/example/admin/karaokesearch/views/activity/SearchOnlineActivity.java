package com.example.admin.karaokesearch.views.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.example.admin.karaokesearch.R;

/**
 * Created by admin on 12/11/2017.
 */

public class SearchOnlineActivity extends BaseActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.search_online_activity);
    }
}
