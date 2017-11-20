package com.example.admin.karaokesearch.manager;

import java.util.List;

/**
 * Created by admin on 21/10/2017.
 */

public interface IHelperManager {
    List getSongList(int idFisrt);
    List getSongList(int idFisrt, String vol, String language);
    List getSongList(int idFisrt, String vol, String charter, String language);
    List queryFollowAuthor(String authorName ,String language , long currentId);
    int updateFavorite(long id,int favorite);

    List searchFollowSongName(String search,String lang);
    List searchFollowAuthor(String authorName ,String language);
    List searchFollowSongNameAcronym(String search,String language);
    List searchFollowLyrics(String search ,String language);

    List getFavoriteList();
}
