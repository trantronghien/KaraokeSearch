package com.example.admin.karaokesearch.manager;

import android.util.Log;

import com.example.admin.karaokesearch.models.SongCalifornia;
import com.example.admin.karaokesearch.models.SongCaliforniaDao;
import com.example.admin.karaokesearch.util.App;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by admin on 4/11/2017.
 */

public class SongCaliforniaManager extends BaseHelperManager<SongCaliforniaDao> {

    /**
     * idFisrt
     *
     * @param idFisrt
     */
    @Override
    public List getSongList(int idFisrt) {
        QueryBuilder<SongCalifornia> queryBuilder = daoSession.queryBuilder();
        queryBuilder.limit(limit)
                .offset(idFisrt)
                .orderAsc(SongCaliforniaDao.Properties.Id)
                .build();
        return queryBuilder.list();
    }

    @Override
    public List getSongList(int idFisrt, String vol, String language) {
        QueryBuilder<SongCalifornia> queryBuilder = daoSession.queryBuilder();
        queryBuilder.where(SongCaliforniaDao.Properties.ZSLANGUAGE.eq(language), SongCaliforniaDao.Properties.ZSVOL.eq(vol))
                .limit(limit)
                .offset(idFisrt)
                .orderAsc(SongCaliforniaDao.Properties.Id)
                .build();
        return queryBuilder.list();
    }

    @Override
    public List getSongList(int idFisrt, String vol, String charter, String language) {
        if (language.isEmpty()) language = "vn";
        if (charter.equalsIgnoreCase("all")) charter = "";
        QueryBuilder<SongCalifornia> queryBuilder = daoSession.queryBuilder();
        queryBuilder.where(SongCaliforniaDao.Properties.ZSLANGUAGE.eq(language),
                SongCaliforniaDao.Properties.ZSVOL.eq(vol),
                SongCaliforniaDao.Properties.ZSNAMECLEAN.like(charter + "%"))
                .limit(limit)
                .orderAsc(SongCaliforniaDao.Properties.Id)
                .offset(idFisrt)
                .build();
        Log.i("DEG", "list lấy: " + queryBuilder.list().size());
        return queryBuilder.list();
    }

    @Override
    public List queryFollowAuthor(String authorName, String language, long CurrentId) {
        QueryBuilder<SongCalifornia> queryBuilder = daoSession.queryBuilder();
        queryBuilder.where(
                SongCaliforniaDao.Properties.ZSLANGUAGE.eq(language),
                SongCaliforniaDao.Properties.ZSMETACLEAN.eq(authorName),
                SongCaliforniaDao.Properties.Id.notEq(CurrentId)
        ).orderDesc(SongCaliforniaDao.Properties.ZSVOL).build();
        return queryBuilder.list();
    }

    @Override
    public int updateFavorite(long id, int favorite) {
        try {
            SongCalifornia songArirang = (SongCalifornia) daoSession.queryBuilder().where(SongCaliforniaDao.Properties.Id.eq(id)).unique();
            songArirang.setFAVORITE(favorite);
            daoSession.update(songArirang);
            return songArirang.getFAVORITE();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * tìm theo bài hát có dấu or ko dấu
     *
     * @param search
     * @param language
     * @return
     */
    @Override
    public List searchFollowSongName(String search, String language) {
        String likeQueryEnd = search + "%";
        QueryBuilder query = daoSession.queryBuilder();
        query.where(SongCaliforniaDao.Properties.ZSLANGUAGE.eq(language)
        ).whereOr(
                SongCaliforniaDao.Properties.ZSNAME.like(likeQueryEnd),
                SongCaliforniaDao.Properties.ZSNAMECLEAN.like(likeQueryEnd)
        ).orderAsc(SongCaliforniaDao.Properties.ZSNAME)
                .build();
        return query.list();
    }

    /**
     * trả về danh sách với query bài hát viết tắt
     *
     * @param search
     * @return
     */
    @Override
    public List searchFollowSongNameAcronym(String search, String language) {
        String likeQueryEnd = search + "%";
        QueryBuilder query = daoSession.queryBuilder();
        query.where(
                SongCaliforniaDao.Properties.ZSLANGUAGE.eq(language),
                SongCaliforniaDao.Properties.ZSABBR.like(likeQueryEnd)
        ).orderAsc(SongCaliforniaDao.Properties.ZSABBR)
                .build();
        return query.list();
    }

    /**
     * trả về danh sách với query tác giả có dấu hoặc không dấu
     */
    @Override
    public List searchFollowAuthor(String search, String language) {
        String authorName = search + "%";
        QueryBuilder query = daoSession.queryBuilder();
        query.where(
                SongCaliforniaDao.Properties.ZSLANGUAGE.eq(language)
        ).whereOr(
                SongCaliforniaDao.Properties.ZSMETA.like(authorName),
                SongCaliforniaDao.Properties.ZSMETACLEAN.like(authorName))
                .orderAsc(SongCaliforniaDao.Properties.ZSMETA)
                .build();
        return query.list();
    }

    /**
     * tìm theo lời bài hát có dấu hoặc không dấu
     *
     * @param search
     * @param language
     * @return
     */
    @Override
    public List searchFollowLyrics(String search, String language) {
        String authorName = search + "%";
        QueryBuilder query = daoSession.queryBuilder();
        query.where(
                SongCaliforniaDao.Properties.ZSLANGUAGE.eq(language)
        ).whereOr(
                SongCaliforniaDao.Properties.ZSLYRIC.like(authorName),
                SongCaliforniaDao.Properties.ZSLYRICCLEAN.like(authorName))
                .orderAsc(SongCaliforniaDao.Properties.ZSLYRIC)
                .build();
        return query.list();
    }


    /**
     * get danh sách yêu thích
     * @return
     */
    @Override
    public List getFavoriteList() {
        QueryBuilder<SongCalifornia> query = daoSession.queryBuilder();
        query.where(
                SongCaliforniaDao.Properties.FAVORITE.eq(1)
        ).build();
        return query.list();
    }

    @Override
    public SongCaliforniaDao getDaoSession() {
        return App.getNewSession().getSongCaliforniaDao();
    }
}
