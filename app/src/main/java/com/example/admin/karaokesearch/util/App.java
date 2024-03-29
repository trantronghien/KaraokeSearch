package com.example.admin.karaokesearch.util;

import android.app.Application;
import android.content.Context;


import com.example.admin.karaokesearch.manager.DatabaseOpenHelper;
import com.example.admin.karaokesearch.models.DaoMaster;
import com.example.admin.karaokesearch.models.DaoSession;


/**
 * Created by admin on 3/7/2017.
 */

public class App extends Application{
    private static DaoSession daoSession;
    private static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseOpenHelper helper = new DatabaseOpenHelper(this , DatabaseOpenHelper.DATASE_NAME , null);
        helper.openDatabase();
        DaoMaster daoMaster = new DaoMaster(helper.getDatabaseInstance());
        this.daoSession = daoMaster.newSession();
        context = this;

    }

    public static DaoSession getNewSession() {
        return daoSession;
    }

    public static Context getContext() {
        return context;
    }


}
