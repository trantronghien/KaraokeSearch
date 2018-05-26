package com.example.admin.karaokesearch.views;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 3/15/2017.
 */

public interface SearchCallbackView<T> extends BaseAbstractView{
    void onSearching();
    void onSearchFinished(List<T> list);
    void onSearchFailed(String error);
}
