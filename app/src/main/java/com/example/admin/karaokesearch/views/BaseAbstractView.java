package com.example.admin.karaokesearch.views;

import com.example.admin.karaokesearch.models.SongTable;

/**
 * Created by admin on 25/10/2017.
 */

public interface BaseAbstractView<T extends SongTable> {
    void isUpdatedCheckBox(int isUpdated, int positionAt);
}
