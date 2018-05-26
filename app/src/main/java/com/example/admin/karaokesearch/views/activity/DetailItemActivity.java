package com.example.admin.karaokesearch.views.activity;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.karaokesearch.R;
import com.example.admin.karaokesearch.models.SongTable;
import com.example.admin.karaokesearch.presenter.BaseSongPresenter;
import com.example.admin.karaokesearch.presenter.RoutePresenter;
import com.example.admin.karaokesearch.util.ConfigSongTable;
import com.example.admin.karaokesearch.util.UtilHelper;
import com.example.admin.karaokesearch.views.DetailActivitylView;
import com.example.admin.karaokesearch.views.adapter.SongRecyclerAdapter;

import java.util.ArrayList;


public class DetailItemActivity<T extends SongTable> extends BaseActivity implements
        View.OnClickListener,
        DetailActivitylView,
        SongRecyclerAdapter.OnItemClickListener,
        SongRecyclerAdapter.OnCheckedChangeListener{

    private LinearLayout btnWatch , btnShare;
    private RecyclerView recyleSuggestSong ;
    private CheckBox cb_favorite;
    private ImageView btnGoBack;
    private final String TAG_LOG = "DetailItemActivity";
    private View view;
    private String songNameCurrent;
    private SongRecyclerAdapter<T> adapter;
    private BaseSongPresenter presenter;

    private void inIt() {
        cb_favorite = (CheckBox) findViewById(R.id.checkBox_detail_favorite);
        btnWatch = (LinearLayout) findViewById(R.id.rl_detail_watch);
        btnWatch.setOnClickListener(this);
        btnShare = (LinearLayout) findViewById(R.id.rl_detail_share);
        btnShare.setOnClickListener(this);
        recyleSuggestSong = (RecyclerView) findViewById(R.id.list_suggest_song);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyleSuggestSong.setLayoutManager(mLinearLayoutManager);
        btnGoBack = (ImageView) findViewById(R.id.img_detail_goBack);
        btnGoBack.setOnClickListener(this);

        view = this.findViewById(R.id.parentContent);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);
        inIt();
        T object = getIntent().getParcelableExtra("object");
        setData(object);
        int idTable = Integer.parseInt(object.getZSMANUFACTURE());

        presenter = RoutePresenter
                .RegisterRoutesPresenter(idTable , this);

        // checkBox
        isCheckedCheckBox(object.getFAVORITE());
        onCheckBoxCheckedChange(object.getId() , presenter);

        //author
        presenter.getSongListFollowAuthor(object.getZSMETACLEAN() , "vn" , object.getId());
    }

    private void onCheckBoxCheckedChange(final long idSong, final BaseSongPresenter presenter) {
        cb_favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String textSnack ;
                int favorite;
                if(isChecked){
                    favorite = ConfigSongTable.FAVORITE;
                    textSnack = UtilHelper.getStringRes(R.string.check_favorite , getSongNameCurrent());
                    adapter.notifyDataSetChanged();
                }else {
                    favorite = ConfigSongTable.UNFAVORITE;
                    textSnack = UtilHelper.getStringRes(R.string.uncheck_favorite , getSongNameCurrent());
                    adapter.notifyDataSetChanged();
                }
                presenter.updateFavorite(idSong , favorite);
                Snackbar.make( view , textSnack , Snackbar.LENGTH_LONG).show();
            }
        });

    }

    private boolean isCheckedCheckBox(int favorite) {
        if (favorite == 0){
            cb_favorite.setChecked(false);
            return false;
        }else {
            cb_favorite.setChecked(true);
            return true;
        }
    }

    private void setData(T data) {
        TextView txtIdRow = (TextView) findViewById(R.id.tv_detail_id);
        txtIdRow.setText(String.valueOf(data.getZROWID()));
        TextView txtSongName = (TextView) findViewById(R.id.tv_detail_songName);
        txtSongName.setText(data.getZSNAME());
        TextView txtAuthor = (TextView) findViewById(R.id.tv_detail_author);
        txtAuthor.setText(data.getZSMETA());
        TextView txtVol = (TextView) findViewById(R.id.tv_detail_vol);
        txtVol.setText("Vol " + String.valueOf(data.getZSVOL()));
        TextView volLyric = (TextView) findViewById(R.id.tv_detail_lyric);
        volLyric.setText(data.getZSLYRIC());

        setSongNameCurrent(data.getZSNAME());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_detail_watch:
                Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
                return;
            case R.id.rl_detail_share:
                return;
            case R.id.img_detail_goBack:
                onBackPressed();
                return;
        }
    }

    @Override
    public void whenItemClick(ArrayList listSong) {
        adapter = new SongRecyclerAdapter( listSong , this);
        adapter.setCheckBoxChangeListner(this);
        recyleSuggestSong.setAdapter(adapter);
    }

    @Override
    public void isUpdatedCheckBox(int isUpdated, int po) {
    }

    private void setSongNameCurrent(String songNameCurrent) {
        this.songNameCurrent = songNameCurrent;
    }

    private String getSongNameCurrent() {
        return songNameCurrent;
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "onItemClick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(int position) {
        Toast.makeText(this, "onItemLongClick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(boolean isChecked, int positionAt, long idSong) {
        int favorite = isChecked ? ConfigSongTable.FAVORITE : ConfigSongTable.UNFAVORITE;
        presenter.updateFavorite(idSong,favorite);
    }
}
