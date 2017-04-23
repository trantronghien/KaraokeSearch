package com.example.admin.karaokesearch.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin.karaokesearch.R;
import com.example.admin.karaokesearch.models.Entities.SongTable;
import com.example.admin.karaokesearch.util.ConfigSongTable;

import java.util.List;
import java.util.Locale;


/**
 * Created by admin on 3/10/2017.
 * SongTable is class Sample for all table song
 */

public class SongRecyclerAdapter<T extends SongTable> extends RecyclerView.Adapter {

    private List<T> listSong;
    private OnItemClickListener listener;
    private final int VIEW_TYPE_ITEM = 1;
    private final int VIEW_TYPE_LOADING = 0;
    private boolean loading = false;
    private OnLoadMoreListener onLoadMoreListener;
    private Context context;
    private OnCheckedChangeListener checkBoxChange;
    private String songNameCurrent;
    private RecyclerView recyclerView;

    /**
     * support set event scroll RecyclerView
     *
     * @param listSong
     * @param recyclerView
     */
    public SongRecyclerAdapter(RecyclerView recyclerView, List<T> listSong) {
        this.listSong = listSong;
        recyclerOnScrollListener(recyclerView);
    }

    public SongRecyclerAdapter(RecyclerView recyclerView, List<T> listSong, OnItemClickListener listener, Context context) {
        this.listSong = listSong;
        recyclerOnScrollListener(recyclerView);
        this.listener = listener;
        this.context = context;
    }

    public void recyclerOnScrollListener(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            this.recyclerView = recyclerView;
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    final int visibleItemCount = linearLayoutManager.getChildCount();
                    final int totalItemCount = linearLayoutManager.getItemCount();
                    int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    long firstIdSongListCurrent = listSong.get(totalItemCount - 1).getId();
                    Log.i("TAG_ADAPTER", "" + firstIdSongListCurrent);
                    if (!loading) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore(firstIdSongListCurrent, totalItemCount, visibleItemCount);
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setOnLoadMoreListener(RecyclerView recyclerView) {
        recyclerOnScrollListener(recyclerView);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_recycler_util, parent, false);
            vh = new SongRecyclerAdapter.ViewHolder(v, listener);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_recycler_progress, parent, false);
            vh = new ProgressViewHolder(v);
        }
        return vh;

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final T item = listSong.get(position);
        if (holder instanceof SongRecyclerAdapter.ViewHolder) {
            SongRecyclerAdapter.ViewHolder holderPar = (SongRecyclerAdapter.ViewHolder) holder;
            holderPar.txtSongName.setText(item.getZSNAME());
            // checkbox
            setSongNameCurrent(item.getZSNAME());
//            if (item.getFAVORITE() == 1) holderPar.cb_favorite.setChecked(true);
//            else holderPar.cb_favorite.setChecked(false);
            holderPar.cb_favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        checkBoxChange.onCheckedChanged(isChecked, item.getId(), getSongNameCurrent(), ConfigSongTable.FAVORITE);
                    } else {
                        checkBoxChange.onCheckedChanged(!isChecked, item.getId(), getSongNameCurrent(), ConfigSongTable.UNFAVORITE);
                    }

                }
            });
            holderPar.txtAuthor.setText(item.getZSMETA());
            holderPar.txtSongCode.setText(String.valueOf(item.getZROWID()));
            holderPar.txtLyric.setText(item.getZSLYRIC());
        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    public T getItem(int position) {
        return listSong.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return listSong.get(position) != null ? VIEW_TYPE_ITEM : VIEW_TYPE_LOADING;
    }

    @Override
    public int getItemCount() {
        return listSong.size();
    }

    @SuppressWarnings("unused")
    public void add(int index, T elem) {
        listSong.add(index, elem);
        notifyDataSetChanged();
    }

    @SuppressWarnings("unused")
    public void addAll(List<T> elem) {
        listSong.addAll(elem);
        notifyDataSetChanged();
    }

    @SuppressWarnings("unused")
    public void remove(T elem) {
        listSong.remove(elem);
        notifyDataSetChanged();
    }

    @SuppressWarnings("unused")
    public void remove(int index) {
        listSong.remove(index);
        notifyDataSetChanged();
    }

    @SuppressWarnings("unused")
    public void replace(int index, T elem) {
        listSong.set(index, elem);
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> elem) {
        listSong.clear();
        listSong.addAll(elem);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        OnItemClickListener listener;
        protected CheckBox cb_favorite;
        protected TextView txtSongName;
        protected TextView txtAuthor;
        protected TextView txtSongCode;
        protected TextView txtLyric;

        private void inIt(View itemView) {
            txtSongName = (TextView) itemView.findViewById(R.id.textView_item_name);
            cb_favorite = (CheckBox) itemView.findViewById(R.id.chb_item_favorite);
            txtAuthor = (TextView) itemView.findViewById(R.id.textView_item_author);
            txtSongCode = (TextView) itemView.findViewById(R.id.textView_item_song_code);
            txtLyric = (TextView) itemView.findViewById(R.id.TextView_item_lyric);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(getLayoutPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listener != null) {
                        listener.onItemLongClick(getLayoutPosition());
                    }
                    return false;
                }
            });
        }

        public ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            inIt(itemView);
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(getLayoutPosition());
            }

        }

        @Override
        public boolean onLongClick(View v) {
            if (listener != null) {
                listener.onItemLongClick(getLayoutPosition());
            }
            return false;
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }

    }


    public interface OnItemClickListener {
        void onItemClick(int position);

        void onItemLongClick(int position);
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(boolean isChecked, long idSong, String songNameCurrent, int favoriteValue);
    }

    public void setCheckBoxChangeListner(OnCheckedChangeListener checkBoxChange) {
        this.checkBoxChange = checkBoxChange;
    }

    private void setSongNameCurrent(String songNameCurrent) {
        this.songNameCurrent = songNameCurrent;
    }

    private String getSongNameCurrent() {
        return songNameCurrent;
    }

    public void setLoaded() {
        loading = false;
    }

}
