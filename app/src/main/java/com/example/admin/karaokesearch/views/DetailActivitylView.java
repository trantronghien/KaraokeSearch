package com.example.admin.karaokesearch.views;

import com.example.admin.karaokesearch.models.SongTable;

import java.util.ArrayList;

/**
 * Created by admin on 4/7/2017.
 */

public interface DetailActivitylView<T extends SongTable> extends BaseAbstractView{
    void whenItemClick(ArrayList<T> listSong);
}
