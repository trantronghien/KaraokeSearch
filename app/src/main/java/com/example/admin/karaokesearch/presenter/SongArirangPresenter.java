package com.example.admin.karaokesearch.presenter;


import com.example.admin.karaokesearch.models.Entities.SongArirang;
import com.example.admin.karaokesearch.models.ISongArirangHelperModel;
import com.example.admin.karaokesearch.models.SongArirangHelper;
import com.example.admin.karaokesearch.views.DetaiActivitylView;
import com.example.admin.karaokesearch.views.ListSongView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 3/10/2017.
 */

public class SongArirangPresenter implements ISongPresenter {
    private ListSongView view;
    private ISongArirangHelperModel model;
    private DetaiActivitylView detaiView;


    public SongArirangPresenter(ListSongView view, SongArirangHelper model) {
        this.view = view;
        this.model = model;
    }

    public SongArirangPresenter(SongArirangHelper model) {
        this.model = model;
    }

    public SongArirangPresenter(ListSongView view) {
        this.view = view;
    }

    public SongArirangPresenter(DetaiActivitylView view) {
        this.detaiView = view;
    }

    @Override
    public void queryData(CharSequence charSequence) {

    }

    @Override
    public void getSongList(int limit, int idFisrt, String vol, String alphabet, String language) {
        List list;
        if (model == null) {
            model = new SongArirangHelper();
            list = model.getSongArirangList(limit, idFisrt, vol, alphabet, language);
            view.loadDataForRequestFromSpinner(list);
        } else {
            view.loadDataForRequestFromSpinner(model.getSongArirangList(limit, idFisrt, vol, alphabet, language));
        }
    }

    @Override
    public void getSongListWhenReload(int limit, int idFisrt, String vol, String alphabet ,String language) {
        if (model == null) {
            model = new SongArirangHelper();
            view.reload(model.getSongArirangList(limit, idFisrt, vol, alphabet ,language));
        } else {
            view.reload(model.getSongArirangList(limit, idFisrt, vol ,alphabet,language));
        }
    }

    @Override
    public void getSongListFollowAuthor(String authorName, String lang) {
        // StringUtils.removeAccent(String input) loại bỏ dấu
        if (model == null) {
            model = new SongArirangHelper();
            List list = model.querryFollowAuthor(authorName, lang);
            detaiView.loadDataFollowAuthor((ArrayList) list);
        }
    }

    @Override
    public void updateFavorite(long id, int favorite) {
        SongArirang songArirang = new SongArirang();
        songArirang.setFAVORITE(favorite);
        if (model == null) {
            model = new SongArirangHelper();
            model.updateFavorite(id, favorite);
//            detaiView.isUpdatedByCheckBox(true);
//            view.isUpdatedByCheckBox(true);
        } else {
            model.updateFavorite(id, favorite);
//            detaiView.isUpdatedByCheckBox(true);
//            view.isUpdatedByCheckBox(true);
        }
    }
}
