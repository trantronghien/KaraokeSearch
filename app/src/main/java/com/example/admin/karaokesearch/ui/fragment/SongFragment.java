package com.example.admin.karaokesearch.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.karaokesearch.R;
import com.example.admin.karaokesearch.adapter.OnLoadMoreListener;
import com.example.admin.karaokesearch.adapter.SongRecyclerAdapter;
import com.example.admin.karaokesearch.models.Entities.SVol;
import com.example.admin.karaokesearch.models.Entities.SongTable;
import com.example.admin.karaokesearch.presenter.ISongPresenter;
import com.example.admin.karaokesearch.presenter.RoutePresenter;
import com.example.admin.karaokesearch.presenter.SVolPresenter;
import com.example.admin.karaokesearch.presenter.SongArirangPresenter;
import com.example.admin.karaokesearch.ui.activity.DetailItemActivity;
import com.example.admin.karaokesearch.ui.activity.MainActivity;
import com.example.admin.karaokesearch.util.ConfigSongTable;
import com.example.admin.karaokesearch.views.ListSongView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * default get Ariang table and new vol , all char
 * https://github.com/giolaoit/LoadMoreRecyclerView/blob/master/app/src/main/java/net/awpspace/loadmorerecycleview/MainActivity.java
 */
public class SongFragment<T extends SongTable> extends BaseFragment implements ListSongView, View.OnClickListener {

    private final String TAG_LOG = "SongFragment";
    private RecyclerView recyclerView;
    private Button btn_vol, btn_songTable, btn_alphabet;

    private ISongPresenter presenter;
    private SVolPresenter volPresenter;

    public final String DEFAULT_LANGUAGE = "vn";
    private final int LIMIT_NUMBER_LIST = 30;
    private int idTable = 1;
    private String vol = "";
    private String alphabet = "All";
    private ArrayList<SVol> listVol;

    boolean isClickButtonSpinner = false;

    private final int ID_BUTTON_VOL = 2;
    private final int ID_BUTTON_SONGTABLE = 1;
    private final int ID_BUTTON_ALPHABET = 3;

    private SongRecyclerAdapter<T> adapter;
    private boolean isDataChange = false;
    List<T> listSong;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();

    }

    @Override
    protected void inIt(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_song);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        btn_vol = (Button) view.findViewById(R.id.button_songTab_vol);
        btn_songTable = (Button) view.findViewById(R.id.button_songTab_tabSong);
        btn_alphabet = (Button) view.findViewById(R.id.button_songTab_alphabet);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        inIt(view);
        setDefaultDataForSpinner();

        presenter = RoutePresenter.RegisterRoutes(getIdTable(), this);
        presenter.getSongList(LIMIT_NUMBER_LIST, 0, getVol(), getAlphabet() ,DEFAULT_LANGUAGE);

        return view;
    }


    // set value default item button spinner
    private void setDefaultDataForSpinner() {
        volPresenter = new SVolPresenter();
        listVol = volPresenter.getListVolName(getIdTable());
        if (!isClickButtonSpinner) {
            setVol("");
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_song;
    }

    @Override
    protected void onViewCreated(View view) {
        super.onViewCreated(view);

        btn_vol.setOnClickListener(this);
        btn_songTable.setOnClickListener(this);
        btn_alphabet.setOnClickListener(this);

        adapter.setCheckBoxChangeListner(this);
        // recycleView scroll listener
        adapter.setOnLoadMoreListener(recyclerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    //============================================================================
    //                               Presenter
    //============================================================================
    //called when recycleView scroll from adapter.setOnLoadMoreListener(this);
    @Override
    public void reload(final List listSong) {
        adapter.addAll(listSong);
//        adapter.recyclerOnScrollListener(recyclerView);
        Log.i("reLoad", "list song: " + listSong.size());
    }

    @Override
    public void showNoData() {
        Log.d(TAG_LOG, "no data");
    }

    @Override
    public void showError() {
    }

    /**
     * set adapter RecyleView when has request from button spinner
     * @param listSong
     */
    @Override
    public void loadDataForRequestFromSpinner(final List listSong) {
        adapter = new SongRecyclerAdapter(recyclerView, listSong, this, getContext());
        recyclerView.setAdapter(adapter);

        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public boolean onLoadMore(long firstIdSongListCurrent, int totalItem, int visibleItemCount) {
                presenter.getSongListWhenReload(LIMIT_NUMBER_LIST , (int) firstIdSongListCurrent, getVol() , getAlphabet() , DEFAULT_LANGUAGE);
                return false;
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        super.onItemClick(position);
        T object = adapter.getItem(position);
        Intent intent = new Intent(getActivity().getBaseContext(), DetailItemActivity.class);
        intent.putExtra("object", object);
        intent.putExtra("idTable", getIdTable());
        Log.i("tab", "Mã: " + object.getZROWID() + "\tSong Name: " + object.getZSNAME() + "\tTác giả: " + object.getZSMETA());
        getActivity().startActivity(intent);
    }

    //============================================================================
    //                            Button DiaLog
    //============================================================================

    /**
     * click button spinner
     */
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
        ArrayAdapter dialogList = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, listDataRes) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(R.color.black_text);
                textView.setGravity(Gravity.CENTER);
                return textView;
            }
        };

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
                        setIdTable(position + 1);   // notify get list vol when change table
                        listVol.clear();
                        listVol = volPresenter.getListVolName(getIdTable());

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
                presenter.getSongList(LIMIT_NUMBER_LIST, 0, getVol(), getAlphabet() ,DEFAULT_LANGUAGE);
                Log.i("value", "id: " + getIdTable() + "\tvol: " + getVol() + "\tChuoi: " + getAlphabet());
            }
        });
    }
    //============================================================================
    //                              GET - SET
    //============================================================================

    private int getIdTable() {
        return idTable;
    }

    private void setIdTable(int idTable) {
        this.idTable = idTable;
    }

    private String getVol() {
        return vol;
    }

    // FIXME: 4/5/2017 fix when change new vol from database, change VOL_DEFAULT
    private void setVol(String vol) {
        String VOL_DEFAULT;
        if (idTable == ConfigSongTable.ID_SONG_ARIRANG) {
            VOL_DEFAULT = "60";
            if (vol.isEmpty()) this.vol = VOL_DEFAULT;
            else this.vol = vol;
        } else if (idTable == ConfigSongTable.ID_SONG_CALIFORNIA) {
            VOL_DEFAULT = "20";
            if (vol.isEmpty()) this.vol = VOL_DEFAULT;
            else this.vol = vol;
        } else if (idTable == ConfigSongTable.ID_SONG_MUSICCORE) {
            VOL_DEFAULT = "92";
            if (vol.isEmpty()) this.vol = VOL_DEFAULT;
            else this.vol = vol;
        } else if (idTable == ConfigSongTable.ID_SONG_VIETKTV) {
            VOL_DEFAULT = "95";
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
        }else{
            this.alphabet = "All";
        }
    }

    private void setSongAdapter(SongRecyclerAdapter<T> adapter) {
        this.adapter = adapter;
    }

    private SongRecyclerAdapter<T> getSongAdapter() {
        return adapter;
    }

    //============================================================================
    //                              Adapter implements 
    //============================================================================


    // checkbox item Adapter
    @Override
    public void onCheckedChanged(boolean isChecked, long idSong, String songNameCurrent, int favoriteValue) {
        String textSnack;
        if (isChecked) {
            presenter.updateFavorite(idSong, favoriteValue);
            textSnack = getContext().getResources().getString(R.string.check_favorite, songNameCurrent);

        } else {
            presenter.updateFavorite(idSong, favoriteValue);
            textSnack = getContext().getResources().getString(R.string.uncheck_favorite, songNameCurrent);
        }
        Snackbar.make(getView(), textSnack, Snackbar.LENGTH_LONG).show();
    }

}
