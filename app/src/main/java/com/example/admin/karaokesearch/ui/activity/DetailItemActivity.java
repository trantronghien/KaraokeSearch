package com.example.admin.karaokesearch.ui.activity;


import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.karaokesearch.R;
import com.example.admin.karaokesearch.adapter.SongRecyclerAdapter;
import com.example.admin.karaokesearch.models.Entities.SongTable;
import com.example.admin.karaokesearch.presenter.ISongPresenter;
import com.example.admin.karaokesearch.presenter.SongArirangPresenter;
import com.example.admin.karaokesearch.util.ConfigSongTable;
import com.example.admin.karaokesearch.views.DetaiActivitylView;

import java.util.ArrayList;


public class DetailItemActivity<T extends SongTable> extends AppCompatActivity implements View.OnClickListener , DetaiActivitylView{

    private LinearLayout btnWatch , btnShare;
    private RecyclerView recyleSuggestSong ;
    private CheckBox cb_favorite;
    private RelativeLayout btnGoBack;
    private final String TAG_LOG = "DetailItemActivity";
    private View view;
    private String songNameCurrent;
    private SongRecyclerAdapter<T> adapter;

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
        btnGoBack = (RelativeLayout) findViewById(R.id.btn_detail_back);

        view = this.findViewById(R.id.parentContent);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);
        inIt();
        T object = getIntent().getParcelableExtra("object");
        setData(object);
        int idTable = getIntent().getIntExtra("idTable" , 1);

        SongArirangPresenter aringrangPresenter = new SongArirangPresenter(this);


        // checkBox
        isCheckedCheckBox(object.getFAVORITE());
        onCheckBoxCheckedChange(object.getId() , aringrangPresenter);

        //author
        aringrangPresenter.getSongListFollowAuthor(object.getZSMETACLEAN() , "vn");
    }

    private void onCheckBoxCheckedChange(final long idSong, final ISongPresenter presenter) {
        cb_favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String textSnack ;
                if(isChecked){
                    presenter.updateFavorite(idSong , ConfigSongTable.FAVORITE);
                    textSnack = getApplicationContext().getResources().getString(R.string.check_favorite , getSongNameCurrent());
                    adapter.notifyDataSetChanged();
                }else {
                    presenter.updateFavorite(idSong ,ConfigSongTable.UNFAVORITE);
                    textSnack = getApplicationContext().getResources().getString(R.string.uncheck_favorite , getSongNameCurrent());
                    adapter.notifyDataSetChanged();
                }
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
            case R.id.btn_detail_back:
                Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this , MainActivity.class));
                return;
        }
    }

    @Override
    public void loadDataFollowAuthor(ArrayList listSong) {
        adapter = new SongRecyclerAdapter<>(recyleSuggestSong , listSong);
        recyleSuggestSong.setAdapter(adapter);
    }

    @Override
    public void isUpdatedByCheckBox(boolean isUpdated) {
        if (isUpdated == true){

        }
    }

    private void setSongNameCurrent(String songNameCurrent) {
        this.songNameCurrent = songNameCurrent;
    }

    private String getSongNameCurrent() {
        return songNameCurrent;
    }
}
