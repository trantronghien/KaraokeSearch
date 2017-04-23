package com.example.admin.karaokesearch.models;

import android.util.Log;

import com.example.admin.karaokesearch.models.Entities.SVol;
import com.example.admin.karaokesearch.models.Entities.SVolDao;
import com.example.admin.karaokesearch.models.Entities.SongArirangDao;
import com.example.admin.karaokesearch.models.api.App;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;

/**
 * Created by admin on 3/18/2017.
 */

public class SVolHelper implements IVolHelperModel {

    SVolDao daoSession;
    public SVolHelper() {
        daoSession = App.getNewSession().getSVolDao();
    }

    /**
     * get list name vol of table song and sorted by Descending
     * @param idTable id of table song
     */
    @Override
    public ArrayList<SVol> getVolName(int idTable) {
        QueryBuilder<SVol> queryBuilder = daoSession.queryBuilder();
        queryBuilder.where(SVolDao.Properties.ZSMANUFACTURE.eq(idTable))
                .orderDesc(SVolDao.Properties.ZSVOL)
                .build();
        return (ArrayList) queryBuilder.list();
    }
}
