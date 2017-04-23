package com.example.admin.karaokesearch.presenter;

import com.example.admin.karaokesearch.models.Entities.SVol;

import java.util.ArrayList;

/**
 * Created by admin on 3/10/2017.
 */

public interface ISongPresenter {
    void queryData(CharSequence charSequence);

    void getSongList(int limit, int idFisrt, String vol, String alphabet, String language);

    void getSongListWhenReload(int limit, int idFisrt, String vol, String alphabet, String language);

    void getSongListFollowAuthor(String authorName, String lang);

    void updateFavorite(long id, int favorite);
}
