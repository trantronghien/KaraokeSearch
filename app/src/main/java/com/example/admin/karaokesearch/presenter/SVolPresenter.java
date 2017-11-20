package com.example.admin.karaokesearch.presenter;

import com.example.admin.karaokesearch.models.SVol;
import com.example.admin.karaokesearch.manager.SVolManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 3/19/2017.
 */

public class SVolPresenter {

    private SVolManager volHelper;
    private static SVolPresenter instance;
    private SVolPresenter(){}

    public static SVolPresenter getInstance() {
        if (instance != null)
            return instance;
        else
            return new SVolPresenter();
    }

    public List<SVol> getListVolName(int idTable) {
        if (volHelper == null){
            volHelper = new SVolManager();
        }
        return volHelper.getVolName(idTable);
    }

    public String getNewVol(int idTable) {
        if (volHelper == null){
            volHelper = new SVolManager();
        }
        return volHelper.getNewVol(idTable);
    }
}
