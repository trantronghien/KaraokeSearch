package com.example.admin.karaokesearch.presenter;

import com.example.admin.karaokesearch.R;
import com.example.admin.karaokesearch.manager.BaseHelperManager;
import com.example.admin.karaokesearch.models.SongCalifornia;
import com.example.admin.karaokesearch.models.SongMusiccore;
import com.example.admin.karaokesearch.util.StringUtils;
import com.example.admin.karaokesearch.util.UtilHelper;
import com.example.admin.karaokesearch.views.BaseAbstractView;

import java.util.List;

/**
 * Created by admin on 4/13/2017.
 */

public class SongMusiccorePresenter extends BaseSongPresenter{
    public SongMusiccorePresenter(BaseAbstractView view, BaseHelperManager manager) {
        super(view , manager);
    }

    @Override
    public void queryData(final CharSequence charSequence) {
        super.queryData(charSequence);
        String search = StringUtils.trimSpace(charSequence.toString());
        new TaskSearch<SongMusiccore>(search) {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                searchView.onSearching();
            }

            @Override
            protected List<SongMusiccore> doInBackground(String... params) {
                List<SongMusiccore> listResuft;
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
            protected void onPostExecute(List<SongMusiccore> list) {
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
