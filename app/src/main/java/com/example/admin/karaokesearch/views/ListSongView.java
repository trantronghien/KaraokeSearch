package com.example.admin.karaokesearch.views;

import java.util.List;

/**
 * Created by admin on 3/10/2017.
 */

public interface ListSongView <T> extends BaseAbstractView {
    void reloadOnLoadMore(List<T> listReload);

    void showNoData();

    void showError(String ErrorContent);

    // spinner = button
    void loadDataFromRequestSpinner(List<T> listSong);
}
