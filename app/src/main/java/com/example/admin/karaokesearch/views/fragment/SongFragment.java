package com.example.admin.karaokesearch.views.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.karaokesearch.R;
import com.example.admin.karaokesearch.models.SVol;
import com.example.admin.karaokesearch.models.SongArirang;
import com.example.admin.karaokesearch.models.SongTable;
import com.example.admin.karaokesearch.presenter.BaseSongPresenter;
import com.example.admin.karaokesearch.presenter.RoutePresenter;
import com.example.admin.karaokesearch.presenter.SVolPresenter;
import com.example.admin.karaokesearch.util.ConfigSongTable;
import com.example.admin.karaokesearch.util.UtilHelper;
import com.example.admin.karaokesearch.views.ListSongView;
import com.example.admin.karaokesearch.views.activity.MainActivity;
import com.example.admin.karaokesearch.views.adapter.DialogListAdapter;
import com.example.admin.karaokesearch.views.adapter.SongRecyclerAdapter;
import com.example.admin.karaokesearch.views.ui.EndlessRecyclerOnScrollListener;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * default get Ariang table and new vol , all char
 * https://github.com/giolaoit/LoadMoreRecyclerView/blob/master/app/src/main/java/net/awpspace/loadmorerecycleview/MainActivity.java
 */
public class SongFragment<T extends SongTable> extends BaseFragment implements ListSongView
        , SuperSwipeRefreshLayout.OnPushLoadMoreListener {

    private final String TAG_LOG = "SongFragment";
    private RecyclerView recyclerView;
    private Button btn_vol, btn_songTable, btn_alphabet;

    private BaseSongPresenter presenter;
    private SVolPresenter volPresenter = SVolPresenter.getInstance();

    public final String DEFAULT_LANGUAGE = "vn";
    private final int LIMIT_NUMBER_LIST = 30;
    private int idTable = ConfigSongTable.ID_SONG_DEFAULT;
    private String vol = "";
    private String alphabet = "All";
    private ArrayList<SVol> listVol;
    boolean isClickButtonSpinner = false;

    private final int ID_BUTTON_VOL = 2;
    private final int ID_BUTTON_SONGTABLE = 1;
    private final int ID_BUTTON_ALPHABET = 3;

    private SongRecyclerAdapter<T> adapter;
    private boolean isDataChange = false;
    private List<T> listSong;
    private SuperSwipeRefreshLayout swipeRefresh;
    private int FisrtItem = 0;  //
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view != null)
            return view;
        return view = super.onCreateView(inflater, container, savedInstanceState);
    }


    private View createFooterView() {
        return LayoutInflater.from(swipeRefresh.getContext()).inflate(R.layout.layout_footer_swipe_refresh, null);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_song;
    }

    @Override
    protected void onViewCreated(View view) {
        super.onViewCreated(view);
        //create Presenter
        setDataDefaultSpinner();
    }

    @Override
    protected void inItData() {
        if (listSong == null)
            listSong = new ArrayList<>();
        adapter = new SongRecyclerAdapter<T>(listSong, this);
        recyclerView.setAdapter(adapter);
        presenter = RoutePresenter.RegisterRoutesPresenter(getIdTable(), this);    // new SongArirangPresenter
        presenter.getSongList(FisrtItem, getVol(), getAlphabet(), DEFAULT_LANGUAGE);
    }

    @Override
    protected SongRecyclerAdapter getAdapter() {
        return adapter;
    }

    @Override
    protected BaseSongPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void inItView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_song);
        recyclerView.setHasFixedSize(false);

//        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(getLayoutManager());
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        btn_vol = (Button) view.findViewById(R.id.button_songTab_vol);
        btn_songTable = (Button) view.findViewById(R.id.button_songTab_tabSong);
        btn_alphabet = (Button) view.findViewById(R.id.button_songTab_alphabet);
        swipeRefresh = (SuperSwipeRefreshLayout) view.findViewById(R.id.swipeRefresh_song_fragment);
        swipeRefresh.setFooterView(createFooterView());
    }

    @Override
    protected void listener() {
        btn_vol.setOnClickListener(this);
        btn_songTable.setOnClickListener(this);
        btn_alphabet.setOnClickListener(this);
        adapter.setCheckBoxChangeListner(this);
        //        adapter.setOnLoadMoreListener(this);

        swipeRefresh.setOnPushLoadMoreListener(this);
        //   ------------- when pull xuống-------------
        swipeRefresh.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onPullDistance(int distance) {
            }

            @Override
            public void onPullEnable(boolean enable) {
            }
        });
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(getLayoutManager()) {
            @Override
            public void onScrolledToEnd(int itemCount) {
                swipeRefresh.setLoadMore(true);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getSongList(FisrtItem, getVol(), getAlphabet(), DEFAULT_LANGUAGE);
    }

    //============================================================================
    //         todo: implement function Interface View from Presenter
    //============================================================================
    @Override
    public void reloadOnLoadMore(final List listSong) {
        if (!listSong.isEmpty()) {
//            Log.i("DEG", "nhận: list size " + listSong.size());
            this.addAllListSong(listSong);
            adapter.addItemMore(listSong);
//            Log.i("DEG", "sau nhận: list size " + this.listSong.size());
        }
        swipeRefresh.setLoadMore(false);
    }

    @Override
    public void showNoData() {
        Log.d(TAG_LOG, "no data");
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getActivitys(), error, Toast.LENGTH_SHORT).show();
    }

    /**
     * set adapter RecyleView when has request from button spinner
     *
     * @param listSong
     */
    @Override
    public void loadDataFromRequestSpinner(final List listSong) {
        setListSong(listSong);
        if (adapter != null) {
            adapter.replaceAll(listSong);
        }
    }

    //============================================================================
    //                            todo Button DiaLog
    //============================================================================
    // set value default item button diglog spinner
    private void setDataDefaultSpinner() {
        listVol = (ArrayList<SVol>) volPresenter.getListVolName(getIdTable());
        if (!isClickButtonSpinner) {
            setVol("");
        }
    }

    @Override
    public void onClick(View v) {
        isDataChange = false;     // when click button spinner set flag false => loadDataForRequestFromSpinner => setListSongDataChange
        switch (v.getId()) {
            case R.id.button_songTab_tabSong:
                ArrayList<String> tableName = new ArrayList();
                tableName.addAll(Arrays.asList(activity.getResources().getStringArray(R.array.table_song)));
                String titleTableName = activity.getResources().getString(R.string.title_dialog_table_song);
                showDialog(tableName, titleTableName, ID_BUTTON_SONGTABLE);
                break;
            case R.id.button_songTab_vol:
                String title = getString(R.string.title_vol_dialog);
                ArrayList<String> listVolName = new ArrayList<>();
                for (SVol item : listVol) {
                    listVolName.add(item.getZSNAME());
                }
                showDialog(listVolName, title, ID_BUTTON_VOL);
                break;
            case R.id.button_songTab_alphabet:
                ArrayList<String> alpabetList = new ArrayList<>();
                alpabetList.addAll(Arrays.asList(activity.getResources().getStringArray(R.array.alphabet)));
                String alphabetTitle = activity.getResources().getString(R.string.title_dialog_alphabet_song);
                showDialog(alpabetList, alphabetTitle, ID_BUTTON_ALPHABET);
                break;
        }
    }

    public void showDialog(ArrayList<String> listDataRes, String title, final int idButton) {
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        dialog.setCancelable(true);
        View view = activity.getLayoutInflater().inflate(R.layout.dialog_layout_list, null);
        TextView txt_title = (TextView) view.findViewById(R.id.text_dialog_title);
        txt_title.setText(title);
        ListView list = (ListView) view.findViewById(R.id.listView_dialog_songTab);
        DialogListAdapter dialogList = new DialogListAdapter(listDataRes, getContext());
        list.setAdapter(dialogList);
        dialog.setView(view);
        dialog.show();
        onItemClickDialog(list, idButton, dialog);
    }

    private void onItemClickDialog(final ListView listView, final int idButton, final AlertDialog alertDialog) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isClickButtonSpinner = true;
                switch (idButton) {
                    case ID_BUTTON_SONGTABLE:
                        btn_songTable.setText((String) listView.getItemAtPosition(position));
                        // notify get list vol when change table and change presenter
                        setIdTable(position + 1);
                        listVol.clear();
                        listVol = (ArrayList<SVol>) volPresenter.getListVolName(getIdTable());

                        // set value btn_vol
                        btn_vol.setText(listVol.get(0).getZSNAME());  // set first item when change table
                        setVol(String.valueOf(listVol.get(0).getZSVOL()));
                        alertDialog.dismiss();
                        break;
                    case ID_BUTTON_VOL:
                        String volTitle = (String) listView.getItemAtPosition(position);
                        int valueVol = listVol.get(position).getZSVOL();
                        setVol(String.valueOf(valueVol));
                        btn_vol.setText(volTitle);
                        alertDialog.dismiss();
                        break;
                    case ID_BUTTON_ALPHABET:
                        String resultAlphabet = (String) listView.getItemAtPosition(position);
                        if (position != 0)
                            setAlphabet(resultAlphabet);
                        else
                            setAlphabet("");
                        btn_alphabet.setText(resultAlphabet);
                        alertDialog.dismiss();
                        break;
                }
                notifyChangePresenter(getIdTable());
                presenter.getSongList(FisrtItem, getVol(), getAlphabet(), DEFAULT_LANGUAGE);
                Log.i(TAG_LOG, "onItemClickDialog: id: " + getIdTable() + "\tvol: " + getVol() + "\tChuoi: " + getAlphabet());
            }
        });
    }
    //============================================================================
    //                              todo GET - SET
    //============================================================================

    protected int getIdTable() {
        return idTable;
    }

    // notify change presenter
    private void setIdTable(int idTable) {
        this.idTable = idTable;
    }

    private void notifyChangePresenter(int idTable) {
        presenter = RoutePresenter.RegisterRoutesPresenter(idTable, this);
        notifyChangePresenter(presenter);
    }

    private String getVol() {
        if (vol.equals("")) {
            return getIdTable() == ConfigSongTable.ID_SONG_ARIRANG ?
                    volPresenter.getNewVol(ConfigSongTable.ID_SONG_ARIRANG) : vol;
        }
        return vol;
    }

    // FIXME: 4/5/2017 fix when change new vol from database, change VOL_DEFAULT
    private void setVol(String vol) {
        String VOL_DEFAULT;
        if (idTable == ConfigSongTable.ID_SONG_ARIRANG) {
            VOL_DEFAULT = volPresenter.getNewVol(ConfigSongTable.ID_SONG_ARIRANG);
            if (vol.isEmpty()) this.vol = VOL_DEFAULT;
            else this.vol = vol;
        } else if (idTable == ConfigSongTable.ID_SONG_CALIFORNIA) {
            VOL_DEFAULT = volPresenter.getNewVol(ConfigSongTable.ID_SONG_CALIFORNIA);
            if (vol.isEmpty()) this.vol = VOL_DEFAULT;
            else this.vol = vol;
        } else if (idTable == ConfigSongTable.ID_SONG_MUSICCORE) {
            VOL_DEFAULT = volPresenter.getNewVol(ConfigSongTable.ID_SONG_MUSICCORE);
            if (vol.isEmpty()) this.vol = VOL_DEFAULT;
            else this.vol = vol;
        } else if (idTable == ConfigSongTable.ID_SONG_VIETKTV) {
            VOL_DEFAULT =  volPresenter.getNewVol(ConfigSongTable.ID_SONG_VIETKTV);
            if (vol.isEmpty()) this.vol = VOL_DEFAULT;
            else this.vol = vol;
        }
    }

    private String getAlphabet() {
        return alphabet;
    }

    private void setAlphabet(String alphabet) {
        if (!alphabet.isEmpty()) {
            this.alphabet = alphabet;
            Log.i("alphabet", this.alphabet);
        } else {
            this.alphabet = "All";
        }
    }

    private void setListSong(List<SongArirang> listSong) {
        if (listSong == null) return;
        this.listSong = (List<T>) listSong;
    }

    private void addAllListSong(List<SongArirang> listSong) {
        if (listSong == null) return;
        this.listSong.addAll((Collection<? extends T>) listSong);
    }

    @Override
    public int getIdFragment() {
        return UtilHelper.FRAGMENT_SONG_LIST;
    }


    //============================================================================
    //                              todo OnMore
    //============================================================================


    @Override
    public void onLoadMore() {
        int idSong = listSong.size();
        Log.i("DEG", "gửi list size: " + idSong);
        presenter.getSongListWhenOnLoadMore(idSong,
                getVol(),
                getAlphabet(),
                DEFAULT_LANGUAGE);
    }

    @Override
    public void onPushDistance(int i) {
    }

    @Override
    public void onPushEnable(boolean b) {
    }

}
