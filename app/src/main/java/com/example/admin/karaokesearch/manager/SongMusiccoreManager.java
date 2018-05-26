package com.example.admin.karaokesearch.manager;


import android.util.Log;

import com.example.admin.karaokesearch.models.SongMusiccore;
import com.example.admin.karaokesearch.models.SongMusiccoreDao;
import com.example.admin.karaokesearch.util.App;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by admin on 4/11/2017.
 */

public class SongMusiccoreManager extends BaseHelperManager<SongMusiccoreDao> {

    /**
     * idFisrt
     *
     * @param idFisrt
     */
    @Override
    public List getSongList(int idFisrt) {
        QueryBuilder<SongMusiccore> queryBuilder = daoSession.queryBuilder();
        queryBuilder.limit(limit)
                .offset(idFisrt)
                .orderAsc(SongMusiccoreDao.Properties.Id)
                .build();
        return queryBuilder.list();
    }

    @Override
    public List getSongList(int idFisrt, String vol, String language) {
        QueryBuilder<SongMusiccore> queryBuilder = daoSession.queryBuilder();
        queryBuilder.where(SongMusiccoreDao.Properties.ZSLANGUAGE.eq(language), SongMusiccoreDao.Properties.ZSVOL.eq(vol))
                .limit(limit)
                .offset(idFisrt)
                .orderAsc(SongMusiccoreDao.Properties.Id)
                .build();
        return queryBuilder.list();
    }

    @Override
    public List getSongList(int idFisrt, String vol, String charter, String language) {
        if (language.isEmpty()) language = "vn";
        if (charter.equalsIgnoreCase("all")) charter = "";
        QueryBuilder<SongMusiccore> queryBuilder = daoSession.queryBuilder();
        queryBuilder.where(SongMusiccoreDao.Properties.ZSLANGUAGE.eq(language),
                SongMusiccoreDao.Properties.ZSVOL.eq(vol),
                SongMusiccoreDao.Properties.ZSNAMECLEAN.like(charter + "%"))
                .limit(limit)
                .orderAsc(SongMusiccoreDao.Properties.Id)
                .offset(idFisrt)
                .build();
        Log.i("DEG", "list lấy: " + queryBuilder.list().size());
        return queryBuilder.list();
    }

    @Override
    public List queryFollowAuthor(String authorName, String language, long CurrentId) {
        QueryBuilder<SongMusiccore> queryBuilder = daoSession.queryBuilder();
        queryBuilder.where(
                SongMusiccoreDao.Properties.ZSLANGUAGE.eq(language),
                SongMusiccoreDao.Properties.ZSMETACLEAN.eq(authorName),
                SongMusiccoreDao.Properties.Id.notEq(CurrentId)
        ).orderDesc(SongMusiccoreDao.Properties.ZSVOL).build();
        return queryBuilder.list();
    }

    @Override
    public int updateFavorite(long id, int favorite) {
        try {
            SongMusiccore songArirang = (SongMusiccore) daoSession.queryBuilder().where(SongMusiccoreDao.Properties.Id.eq(id)).unique();
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
        query.where(SongMusiccoreDao.Properties.ZSLANGUAGE.eq(language)
        ).whereOr(
                SongMusiccoreDao.Properties.ZSNAME.like(likeQueryEnd),
                SongMusiccoreDao.Properties.ZSNAMECLEAN.like(likeQueryEnd)
        ).orderAsc(SongMusiccoreDao.Properties.ZSNAME)
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
                SongMusiccoreDao.Properties.ZSLANGUAGE.eq(language),
                SongMusiccoreDao.Properties.ZSABBR.like(likeQueryEnd)
        ).orderAsc(SongMusiccoreDao.Properties.ZSABBR)
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
                SongMusiccoreDao.Properties.ZSLANGUAGE.eq(language)
        ).whereOr(
                SongMusiccoreDao.Properties.ZSMETA.like(authorName),
                SongMusiccoreDao.Properties.ZSMETACLEAN.like(authorName))
                .orderAsc(SongMusiccoreDao.Properties.ZSMETA)
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
                SongMusiccoreDao.Properties.ZSLANGUAGE.eq(language)
        ).whereOr(
                SongMusiccoreDao.Properties.ZSLYRIC.like(authorName),
                SongMusiccoreDao.Properties.ZSLYRICCLEAN.like(authorName))
                .orderAsc(SongMusiccoreDao.Properties.ZSLYRIC)
                .build();
        return query.list();
    }


    /**
     * get danh sách yêu thích
     * @return
     */
    @Override
    public List getFavoriteList() {
        QueryBuilder<SongMusiccore> query = daoSession.queryBuilder();
        query.where(
                SongMusiccoreDao.Properties.FAVORITE.eq(1)
        ).build();
        return query.list();
    }

    @Override
    public SongMusiccoreDao getDaoSession() {
        return App.getNewSession().getSongMusiccoreDao();
    }
}
