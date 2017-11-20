package com.example.admin.karaokesearch.presenter;

import com.example.admin.karaokesearch.R;
import com.example.admin.karaokesearch.manager.BaseHelperManager;
import com.example.admin.karaokesearch.manager.SongArirangManager;
import com.example.admin.karaokesearch.models.SongArirang;
import com.example.admin.karaokesearch.models.SongTable;
import com.example.admin.karaokesearch.util.StringUtils;
import com.example.admin.karaokesearch.util.UtilHelper;
import com.example.admin.karaokesearch.views.BaseAbstractView;
import com.example.admin.karaokesearch.views.DetailActivitylView;
import com.example.admin.karaokesearch.views.ListSongView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 3/10/2017.
 */

public class SongArirangPresenter extends BaseSongPresenter {

    public SongArirangPresenter(BaseAbstractView view,BaseHelperManager manager) {
        super(view , manager);
    }

    /**
     *
     * @param charSequence
     */
    @Override
    public void queryData(final CharSequence charSequence) {
        super.queryData(charSequence);
        String search = StringUtils.trimSpace(charSequence.toString());
        new TaskSearch<SongArirang>(search) {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                searchView.onSearching();
            }

            @Override
            protected List<SongArirang> doInBackground(String... params) {
                List<SongArirang> listResuft;
                if (params[0].isEmpty()) return null;
                if (StringUtils.countWords(params[0]) >= StringUtils.SIXWORD){
                    //tên bài hát
                    listResuft = manager.searchFollowSongName(params[0] , QueryLanguage);
                    // lơi bài hát
                    listResuft.addAll(manager.searchFollowLyrics(params[0] , QueryLanguage));
                    return listResuft;
                }else {
                    listResuft = manager.searchFollowSongNameAcronym(params[0] , QueryLanguage);
                    listResuft.addAll(manager.searchFollowSongName(params[0], QueryLanguage));
                    listResuft.addAll(manager.searchFollowAuthor(params[0], QueryLanguage));
                }
                return listResuft;
            }

            @Override
            protected void onPostExecute(List<SongArirang> list) {
                super.onPostExecute(list);
                searchView.onSearchFinished(list);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                searchView.onSearchFailed(UtilHelper.getStringRes(R.string.error_search_E100));
            }
        };
    }
}
