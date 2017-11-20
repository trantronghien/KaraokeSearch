package com.example.admin.karaokesearch.manager;

import com.example.admin.karaokesearch.models.SongArirang;
import com.example.admin.karaokesearch.models.SongArirangDao;
import com.example.admin.karaokesearch.util.App;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by admin on 3/10/2017.
 */

public class SongArirangManager extends BaseHelperManager<SongArirangDao> {

    private final String TAG_LOG = "SongArirangManager";

    @Override
    public SongArirangDao getDaoSession() {
        return App.getNewSession().getSongArirangDao();
    }

    /**
     * idFisrt
     *
     * @param idFisrt
     */
    @Override
    public List getSongList(int idFisrt) {
        QueryBuilder<SongArirang> queryBuilder = daoSession.queryBuilder();
        queryBuilder.limit(limit)
                .offset(idFisrt)
                .orderAsc(SongArirangDao.Properties.Id)
                .build();
        return queryBuilder.list();
    }

    @Override
    public List getSongList(int idFisrt, String vol, String language) {
        QueryBuilder<SongArirang> queryBuilder = daoSession.queryBuilder();
        queryBuilder.where(SongArirangDao.Properties.ZSLANGUAGE.eq(language), SongArirangDao.Properties.ZSVOL.eq(vol))
                .limit(limit)
                .offset(idFisrt)
                .orderAsc(SongArirangDao.Properties.Id)
                .build();
        return queryBuilder.list();
    }

    @Override
    public List getSongList(int idFisrt, String vol, String charter, String language) {
        if (language.isEmpty()) language = "vn";
        if (charter.equalsIgnoreCase("all")) charter = "";
        QueryBuilder<SongArirang> queryBuilder = daoSession.queryBuilder();
        queryBuilder.where(SongArirangDao.Properties.ZSLANGUAGE.eq(language),
                SongArirangDao.Properties.ZSVOL.eq(vol),
                SongArirangDao.Properties.ZSNAMECLEAN.like(charter + "%"))
                .limit(limit)
                .orderAsc(SongArirangDao.Properties.Id)
                .offset(idFisrt)
                .build();
        return queryBuilder.list();
    }

    @Override
    public List queryFollowAuthor(String authorName, String language, long CurrentId) {
        QueryBuilder<SongArirang> queryBuilder = daoSession.queryBuilder();
        queryBuilder.where(
                SongArirangDao.Properties.ZSLANGUAGE.eq(language),
                SongArirangDao.Properties.ZSMETACLEAN.eq(authorName),
                SongArirangDao.Properties.Id.notEq(CurrentId)
        ).orderDesc(SongArirangDao.Properties.ZSVOL).build();
        return queryBuilder.list();
    }

    @Override
    public int updateFavorite(long id, int favorite) {
        try {
            SongArirang songArirang = (SongArirang) daoSession.queryBuilder().where(SongArirangDao.Properties.Id.eq(id)).unique();
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
        query.where(SongArirangDao.Properties.ZSLANGUAGE.eq(language)
        ).whereOr(
                SongArirangDao.Properties.ZSNAME.like(likeQueryEnd),
                SongArirangDao.Properties.ZSNAMECLEAN.like(likeQueryEnd)
        ).orderAsc(SongArirangDao.Properties.ZSNAME)
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
                SongArirangDao.Properties.ZSLANGUAGE.eq(language),
                SongArirangDao.Properties.ZSABBR.like(likeQueryEnd)
        ).orderAsc(SongArirangDao.Properties.ZSABBR)
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
                SongArirangDao.Properties.ZSLANGUAGE.eq(language)
        ).whereOr(
                SongArirangDao.Properties.ZSMETA.like(authorName),
                SongArirangDao.Properties.ZSMETACLEAN.like(authorName))
                .orderAsc(SongArirangDao.Properties.ZSMETA)
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
                        SongArirangDao.Properties.ZSLANGUAGE.eq(language)
                ).whereOr(
                        SongArirangDao.Properties.ZSLYRIC.like(authorName),
                        SongArirangDao.Properties.ZSLYRICCLEAN.like(authorName))
                .orderAsc(SongArirangDao.Properties.ZSLYRIC)
                .build();
        return query.list();
    }


    /**
     * get danh sách yêu thích
     * @return
     */
    @Override
    public List getFavoriteList() {
        QueryBuilder<SongArirang> query = daoSession.queryBuilder();
        query.where(
                SongArirangDao.Properties.FAVORITE.eq(1)
        ).build();
        return query.list();
    }
}