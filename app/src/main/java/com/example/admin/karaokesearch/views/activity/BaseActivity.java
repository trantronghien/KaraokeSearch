package com.example.admin.karaokesearch.views.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by admin on 25/10/2017.
 */

public abstract class BaseActivity extends AppCompatActivity{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("ActivityDisplay", ": " + this.getClass().getName() + " {" + BaseActivity.class.getCanonicalName() + "}");
    }
}
