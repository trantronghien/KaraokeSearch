package com.example.admin.karaokesearch.views;

import com.example.admin.karaokesearch.models.Entities.SVol;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 3/10/2017.
 */

public interface ListSongView <T> {
    void reload(List<T> listReload);

    void showNoData();

    void showError();

    // spinner = button
    void loadDataForRequestFromSpinner(List<T> listSong);
}
