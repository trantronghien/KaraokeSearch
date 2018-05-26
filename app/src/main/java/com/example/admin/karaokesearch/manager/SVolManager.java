package com.example.admin.karaokesearch.manager;

import com.example.admin.karaokesearch.models.SVol;

import com.example.admin.karaokesearch.models.SVolDao;
import com.example.admin.karaokesearch.util.App;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by admin on 3/18/2017.
 */

public class SVolManager{
    SVolDao sVolDao;
    public SVolManager(){
        sVolDao = App.getNewSession().getSVolDao();
    }
    /**
     * get list name vol of table song and sorted by Descending
     * @param idTable id of table song
     */
    public List<SVol> getVolName(int idTable) {
        QueryBuilder<SVol> queryBuilder = sVolDao.queryBuilder();
        queryBuilder.where(SVolDao.Properties.ZSMANUFACTURE.eq(idTable))
                .orderDesc(SVolDao.Properties.ZSVOL)
                .build();
        return queryBuilder.list();
    }

    /**
     * get Vol max
     * @param idTable
     * @return
     */
    public String getNewVol(int idTable) {
        QueryBuilder<SVol> queryBuilder = sVolDao.queryBuilder();
        queryBuilder.where(SVolDao.Properties.ZSMANUFACTURE.eq(idTable),
                SVolDao.Properties.ZSVOL.isNotNull()
        ).orderDesc(SVolDao.Properties.ZSVOL)
                .limit(1)
                .build();
        return String.valueOf(queryBuilder.unique().getZSVOL());
    }
}
