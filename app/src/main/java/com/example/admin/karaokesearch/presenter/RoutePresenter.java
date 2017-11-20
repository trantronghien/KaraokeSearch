package com.example.admin.karaokesearch.presenter;

import com.example.admin.karaokesearch.manager.BaseHelperManager;
import com.example.admin.karaokesearch.manager.SongArirangManager;
import com.example.admin.karaokesearch.manager.SongCaliforniaManager;
import com.example.admin.karaokesearch.manager.SongMusiccoreManager;
import com.example.admin.karaokesearch.manager.SongVietKtvManager;
import com.example.admin.karaokesearch.util.ConfigSongTable;
import com.example.admin.karaokesearch.views.BaseAbstractView;

/**
 * Created by admin on 4/13/2017.
 */

public final class RoutePresenter {
    /**
     * using for multiple Presenter get instance follow idTableSong
     * @param idTableSong
     * @param viewControl
     * @return
     */
    public static BaseSongPresenter RegisterRoutesPresenter(int idTableSong , BaseAbstractView viewControl){
        BaseHelperManager helperManager;
        switch (idTableSong){
            case ConfigSongTable.ID_SONG_ARIRANG:
                helperManager = new SongArirangManager();
                return new SongArirangPresenter(viewControl, helperManager);
            case ConfigSongTable.ID_SONG_CALIFORNIA:
                helperManager = new SongCaliforniaManager();
                return new SongCaliforniaPresenter(viewControl , helperManager);
            case ConfigSongTable.ID_SONG_MUSICCORE:
                helperManager = new SongMusiccoreManager();
                return new SongMusiccorePresenter(viewControl , helperManager);
            case ConfigSongTable.ID_SONG_VIETKTV:
                helperManager = new SongVietKtvManager();
                return new SongVietKtvPresenter(viewControl , helperManager);
        }
        return null;
    }
}
