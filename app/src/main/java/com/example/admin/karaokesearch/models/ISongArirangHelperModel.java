package com.example.admin.karaokesearch.models;

import com.example.admin.karaokesearch.models.Entities.SongArirang;
import com.example.admin.karaokesearch.models.Entities.SongTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 3/18/2017.
 */

public interface ISongArirangHelperModel {
    List getSongArirangList(int limit, int idFisrt);

    List getSongArirangList(int limit, int idFisrt, String vol, String language);

    List getSongArirangList(int limit, int idFisrt, String vol, String charter, String language);

    List querryFollowAuthor(String authorName, String lang);

    void updateFavorite(long id, int favoriteValue);
}
