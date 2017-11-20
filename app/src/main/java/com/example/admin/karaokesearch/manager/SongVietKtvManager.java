package com.example.admin.karaokesearch.manager;


import android.util.Log;

import com.example.admin.karaokesearch.models.SongVietktv;
import com.example.admin.karaokesearch.models.SongVietktvDao;
import com.example.admin.karaokesearch.util.App;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by admin on 4/11/2017.
 */

public class SongVietKtvManager extends BaseHelperManager<SongVietktvDao> {

    @Override
    public SongVietktvDao getDaoSession() {
        return App.getNewSession().getSongVietktvDao();
    }
    /**
     * idFisrt
     *
     * @param idFisrt
     */
    @Override
    public List getSongList(int idFisrt) {
        QueryBuilder<SongVietktv> queryBuilder = daoSession.queryBuilder();
        queryBuilder.limit(limit)
                .offset(idFisrt)
                .orderAsc(SongVietktvDao.Properties.Id)
                .build();
        return queryBuilder.list();
    }

    @Override
    public List getSongList(int idFisrt, String vol, String language) {
        QueryBuilder<SongVietktv> queryBuilder = daoSession.queryBuilder();
        queryBuilder.where(SongVietktvDao.Properties.ZSLANGUAGE.eq(language)
                , SongVietktvDao.Properties.ZSVOL.eq(vol))
                .limit(limit)
                .offset(idFisrt)
                .orderAsc(SongVietktvDao.Properties.Id)
                .build();
        return queryBuilder.list();
    }

    @Override
    public List getSongList(int idFisrt, String vol, String charter, String language) {
        if (language.isEmpty()) language = "vn";
        if (charter.equalsIgnoreCase("all")) charter = "";
        QueryBuilder<SongVietktv> queryBuilder = daoSession.queryBuilder();
        queryBuilder.where(SongVietktvDao.Properties.ZSLANGUAGE.eq(language),
                SongVietktvDao.Properties.ZSVOL.eq(vol),
                SongVietktvDao.Properties.ZSNAMECLEAN.like(charter + "%"))
                .limit(limit)
                .orderAsc(SongVietktvDao.Properties.Id)
                .offset(idFisrt)
                .build();
        Log.i("DEG", "list lấy: " + queryBuilder.list().size());
        return queryBuilder.list();
    }

    @Override
    public List queryFollowAuthor(String authorName, String language, long CurrentId) {
        QueryBuilder<SongVietktv> queryBuilder = daoSession.queryBuilder();
        queryBuilder.where(
                SongVietktvDao.Properties.ZSLANGUAGE.eq(language),
                SongVietktvDao.Properties.ZSMETACLEAN.eq(authorName),
                SongVietktvDao.Properties.Id.notEq(CurrentId)
        ).orderDesc(SongVietktvDao.Properties.ZSVOL).build();
        return queryBuilder.list();
    }

    @Override
    public int updateFavorite(long id, int favorite) {
        try {
            SongVietktv songArirang = (SongVietktv) daoSession.queryBuilder()
                    .where(SongVietktvDao.Properties.Id.eq(id)).unique();
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
        query.where(SongVietktvDao.Properties.ZSLANGUAGE.eq(language)
        ).whereOr(
                SongVietktvDao.Properties.ZSNAME.like(likeQueryEnd),
                SongVietktvDao.Properties.ZSNAMECLEAN.like(likeQueryEnd)
        ).orderAsc(SongVietktvDao.Properties.ZSNAME)
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
                SongVietktvDao.Properties.ZSLANGUAGE.eq(language),
                SongVietktvDao.Properties.ZSABBR.like(likeQueryEnd)
        ).orderAsc(SongVietktvDao.Properties.ZSABBR)
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
                SongVietktvDao.Properties.ZSLANGUAGE.eq(language)
        ).whereOr(
                SongVietktvDao.Properties.ZSMETA.like(authorName),
                SongVietktvDao.Properties.ZSMETACLEAN.like(authorName))
                .orderAsc(SongVietktvDao.Properties.ZSMETA)
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
                SongVietktvDao.Properties.ZSLANGUAGE.eq(language)
        ).whereOr(
                SongVietktvDao.Properties.ZSLYRIC.like(authorName),
                SongVietktvDao.Properties.ZSLYRICCLEAN.like(authorName))
                .orderAsc(SongVietktvDao.Properties.ZSLYRIC)
                .build();
        return query.list();
    }


    /**
     * get danh sách yêu thích
     * @return
     */
    @Override
    public List getFavoriteList() {
        QueryBuilder<SongVietktv> query = daoSession.queryBuilder();
        query.where(
                SongVietktvDao.Properties.FAVORITE.eq(1)
        ).build();
        return query.list();
    }
}
