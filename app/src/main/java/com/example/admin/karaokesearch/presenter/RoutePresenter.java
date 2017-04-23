package com.example.admin.karaokesearch.presenter;

import com.example.admin.karaokesearch.util.ConfigSongTable;
import com.example.admin.karaokesearch.views.ListSongView;

/**
 * Created by admin on 4/13/2017.
 */

public final class RoutePresenter {
    public static ISongPresenter RegisterRoutes(int idTableSong , ListSongView listSongView){
        switch (idTableSong){
            case ConfigSongTable.ID_SONG_ARIRANG:
                return new SongArirangPresenter(listSongView);
            case ConfigSongTable.ID_SONG_CALIFORNIA:
                return new SongCaliforniaPresenter(listSongView);
            case ConfigSongTable.ID_SONG_MUSICCORE:
                return new SongMusiccorePresenter(listSongView);
            case ConfigSongTable.ID_SONG_VIETKTV:
                return new SongVietKtvPresenter(listSongView);
        }
        return null;
    }
}
