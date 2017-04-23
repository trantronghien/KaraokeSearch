package com.example.admin.karaokesearch.presenter;

import com.example.admin.karaokesearch.models.Entities.SVol;
import com.example.admin.karaokesearch.models.SVolHelper;
import com.example.admin.karaokesearch.views.ListSongView;

import java.util.ArrayList;

/**
 * Created by admin on 3/19/2017.
 */

public class SVolPresenter {

    private SVolHelper volHelper;

    public ArrayList<SVol> getListVolName(int idTable) {
        if (volHelper == null){
            volHelper = new SVolHelper();
        }
        return volHelper.getVolName(idTable);
    }
}
