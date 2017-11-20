package com.example.admin.karaokesearch.presenter;

import android.os.AsyncTask;
import android.util.Log;

import com.example.admin.karaokesearch.manager.BaseHelperManager;
import com.example.admin.karaokesearch.manager.SongArirangManager;
import com.example.admin.karaokesearch.manager.SongCaliforniaManager;
import com.example.admin.karaokesearch.manager.SongMusiccoreManager;
import com.example.admin.karaokesearch.manager.SongVietKtvManager;
import com.example.admin.karaokesearch.models.SongTable;
import com.example.admin.karaokesearch.util.ErrorObject;
import com.example.admin.karaokesearch.util.StringUtils;
import com.example.admin.karaokesearch.util.UtilHelper;
import com.example.admin.karaokesearch.views.BaseAbstractView;
import com.example.admin.karaokesearch.views.DetailActivitylView;
import com.example.admin.karaokesearch.views.FavoriteCallBackView;
import com.example.admin.karaokesearch.views.ListSongView;
import com.example.admin.karaokesearch.views.NotifyDataFavoriteChange;
import com.example.admin.karaokesearch.views.SearchCallbackView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 3/10/2017.
 */

public abstract class BaseSongPresenter {
    // todo View Control
    protected ListSongView songView;
    protected SearchCallbackView searchView;
    protected BaseHelperManager manager;
    protected FavoriteCallBackView favoriteView;
    protected DetailActivitylView detailView;
    protected NotifyDataFavoriteChange favoriteChange;

    //util interface
    protected BaseAbstractView view;
    private ErrorObject errorObject;
    protected String QueryLanguage = UtilHelper.VN_LANGUAGE;

    // todo maping using instanceof
    public BaseSongPresenter() {
    }

    public BaseSongPresenter(BaseAbstractView view, BaseHelperManager manager) {
        RouteView(view);
        this.manager = manager;
        this.view = view;
    }
    public BaseSongPresenter(BaseAbstractView view) {
        RouteView(view);
        this.view = view;
    }

    private void RouteView(BaseAbstractView view){
        if (view instanceof ListSongView) {
            this.songView = (ListSongView) view;
            return;
        }if (view instanceof SearchCallbackView){
            this.searchView = (SearchCallbackView) view;
            return;
        }if(view instanceof DetailActivitylView){
            this.detailView = (DetailActivitylView) view;
            return;
        }if(view instanceof FavoriteCallBackView){
            this.favoriteView = (FavoriteCallBackView) view;
            return;
        }
    }
    public void setOnListSongView(ListSongView view){
        this.songView = view;
    }
    //override
    public void queryData(CharSequence charSequence) {}

    public void getSongList(int idFisrt, String vol, String alphabet, String language) {
        List list = manager.getSongList(idFisrt, vol, alphabet, language);
        if (list != null) {
            songView.loadDataFromRequestSpinner(list);
        } else {
            songView.showError("Error manager list null");
        }
    }

    public void getSongListWhenOnLoadMore(int idFisrt, String vol, String alphabet, String language) {
        songView.reloadOnLoadMore(manager.getSongList(idFisrt, vol, alphabet, language));
    }

    public void getSongListFollowAuthor(String authorName, String lang,long CurrentId) {
        String author = StringUtils.removeAccents(authorName);
        List list = manager.queryFollowAuthor(author, lang ,CurrentId);
        detailView.whenItemClick((ArrayList) list);
    }

    //============================================================================
    //                              todo Updata favorite
    //============================================================================

    //update detail Favorite
    public void updateFavorite(final long id, final int favorite) {
        int isUpdated = manager.updateFavorite(id, favorite);
        String isSuccess = isUpdated != 0 ? "updateFavorite thành công" : "updateFavorite thất bại";
    }

    public void updateFavorite(final long id, final int favorite, int positionAt) {
        int isUpdated = manager.updateFavorite(id, favorite);
        String isSuccess = isUpdated != 0 ? "updateFavorite thành công" : "updateFavorite thất bại";
        view.isUpdatedCheckBox(isUpdated , positionAt);
        // notify for activity
//        favoriteChange.notifyDataChange(favorite);
    }

    public static void getFavoriteList(FavoriteCallBackView favoriteView){
        List list = new SongArirangManager().getFavoriteList();
        list.addAll(list.size() , new SongMusiccoreManager().getFavoriteList());
        list.addAll(list.size() , new SongCaliforniaManager().getFavoriteList());
        list.addAll(list.size() , new SongVietKtvManager().getFavoriteList());
        favoriteView.UpdateFavoriteList(list);
    }

    public void setNotifyFavoriteChangeListener(NotifyDataFavoriteChange favoriteChange) {
        this.favoriteChange = favoriteChange;
    }

    // ============================================================================
    //                               Util base
    //=============================================================================
    public abstract class TaskSearch<T extends SongTable> extends AsyncTask<String, Void, List<T>> {
        public TaskSearch(String searchParam) {
            this.execute(searchParam);
        }
    }

    public ErrorObject getErrorPresenter() {
        errorObject.errorCode = UtilHelper.ERROR_CODE_MANAGER;
        errorObject.errorMess = this.getClass().getCanonicalName();
        return errorObject;
    }

    public void showErrorLog() {
        Log.e("error", errorObject.errorMess);
    }

    public void setQueryLanguage(String queryLanguage) {
        this.QueryLanguage = queryLanguage;
    }
}
