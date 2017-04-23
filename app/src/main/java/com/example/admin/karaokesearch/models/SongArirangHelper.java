package com.example.admin.karaokesearch.models;

import android.util.Log;

import com.example.admin.karaokesearch.models.Entities.SongArirang;
import com.example.admin.karaokesearch.models.Entities.SongArirangDao;
import com.example.admin.karaokesearch.models.Entities.SongArirangDao.Properties;
import com.example.admin.karaokesearch.models.api.App;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by admin on 3/10/2017.
 */

public class SongArirangHelper implements ISongArirangHelperModel{

    // ZSMANUFACTURE String
    private final String TAG_LOG = "SongArirangHelper";

    SongArirangDao daoSession;

    public SongArirangHelper() {
        daoSession = App.getNewSession().getSongArirangDao();
    }

    /**
     * idFisrt
     * @param limit
     * @param idFisrt
     */
    public List getSongArirangList(int limit, int idFisrt) {
        QueryBuilder<SongArirang> queryBuilder = daoSession.queryBuilder();
         queryBuilder.limit(limit)
                .offset(idFisrt)
                .orderAsc(Properties.Id)
                .build();
        return queryBuilder.list();
    }

    public List getSongArirangList(int limit, int idFisrt, String vol, String language) {

        QueryBuilder<SongArirang> queryBuilder = daoSession.queryBuilder();
        queryBuilder.where(Properties.ZSLANGUAGE.eq(language), Properties.ZSVOL.eq(vol))
                .limit(limit)
                .offset(idFisrt)
                .orderAsc(Properties.Id)
                .build();
        return queryBuilder.list();
    }

    @Override
    public List getSongArirangList(int limit, int idFisrt, String vol, String charter, String language) {
        if (language.isEmpty()) language = "vn";
        if (charter.equalsIgnoreCase("all")) charter = "";
        QueryBuilder<SongArirang> queryBuilder = daoSession.queryBuilder();
        queryBuilder.where(Properties.ZSLANGUAGE.eq(language),
                Properties.ZSVOL.eq(vol) ,
                Properties.ZSNAMECLEAN.like(charter+"%"))
                .limit(limit)
                .offset(idFisrt)
                .orderAsc(Properties.Id)
                .build();
        return queryBuilder.list();
    }

    @Override
    public List querryFollowAuthor(String authorName ,String language) {
        QueryBuilder<SongArirang> queryBuilder = daoSession.queryBuilder();
        queryBuilder.where(Properties.ZSLANGUAGE.eq(language),Properties.ZSMETACLEAN.eq(authorName))
                .orderDesc(Properties.ZSVOL)
                .build();
        return queryBuilder.list();
    }

    @Override
    public void updateFavorite(long id,int favorite) {
        SongArirang songArirang = daoSession.queryBuilder().where(Properties.Id.eq(id)).unique();
        songArirang.setFAVORITE(favorite);
        daoSession.update(songArirang);
    }

    /**
     * Tìm từ gần đúng
     */
    public void Testing(String s){
        Query<SongArirang> query = daoSession.queryBuilder().where(Properties.ZSLYRICCLEAN.like("%" + s +"%")).build();
        SongArirang songArirang = query.uniqueOrThrow();
        Log.i("TAG_TEST" , songArirang.getZSLYRICCLEAN());
        query.setParameter(0 , "%s");
        songArirang = query.uniqueOrThrow();
        Log.i("TAG_TEST" , songArirang.getZSLYRICCLEAN());
        daoSession.queryRawCreate(" Where T.Title Like ? Or T.ViewTitle Like ?", s  , s).listLazy();
    }
}