package com.example.admin.karaokesearch.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.admin.karaokesearch.R;
import com.example.admin.karaokesearch.models.SongTable;

import java.util.List;

/**
 * Created by admin on 20/11/2017.
 */

public class FavoriteRecyclerAdapter<T extends SongTable> extends BaseAdapter {

    protected FavoriteRecyclerAdapter(List listSong) {
        super(listSong);
    }

    protected FavoriteRecyclerAdapter(List listSong,BaseAdapter.OnItemClickListener listener) {
        super(listSong , listener);
    }

    @Override
    public int getItemResource() {
        return R.layout.item_recycler_util;
    }

    @Override
    public void onBindItemViewHolder(final ViewHolder holder, final int position) {
        final T item = (T) getItem(position);
        TextView txtSongName =  holder.getView(R.id.textView_item_name);
        final CheckBox cbFavorite = holder.getView(R.id.chb_item_favorite);
        TextView txtAuthor = holder.getView(R.id.textView_item_author);
        TextView txtSongCode = holder.getView(R.id.textView_item_song_code);
        TextView txtLyric = holder.getView(R.id.TextView_item_lyric);

        // Checkbox
        if (item.getFAVORITE() == 1){
            cbFavorite.setChecked(true);
        } else{
            cbFavorite.setChecked(false);
        }
        cbFavorite.setOnClickListener(new View.OnClickListener() {
            boolean isChecked = cbFavorite.isChecked();
            @Override
            public void onClick(View v) {
                isChecked = !isChecked;
                if (checkBoxChange != null) {
                    checkBoxChange.onCheckedChanged(
                            isChecked,
                            position,
                            item.getId());
                }
                cbFavorite.setChecked(isChecked);
            }
        });
        txtSongName.setText(item.getZSNAME());
        txtAuthor.setText(item.getZSMETA());
        txtSongCode.setText(String.valueOf(item.getZROWID()));
        txtLyric.setText(item.getZSLYRIC());

    }
}
