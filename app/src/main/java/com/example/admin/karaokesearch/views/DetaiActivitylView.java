package com.example.admin.karaokesearch.views;

import com.example.admin.karaokesearch.models.Entities.SongTable;

import java.util.ArrayList;

/**
 * Created by admin on 4/7/2017.
 */

public interface DetaiActivitylView <T extends SongTable>{
    void loadDataFollowAuthor(ArrayList<T> listSong);
    void isUpdatedByCheckBox(boolean updated);
}
