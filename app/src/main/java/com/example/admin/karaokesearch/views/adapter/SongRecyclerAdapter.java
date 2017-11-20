package com.example.admin.karaokesearch.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.admin.karaokesearch.R;
import com.example.admin.karaokesearch.models.SongTable;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by admin on 3/10/2017.
 * SongTable is class Sample for all table song
 */

public class SongRecyclerAdapter<T extends SongTable> extends RecyclerView.Adapter<SongRecyclerAdapter.ViewHolder> {

    private static final String TAG = "SongRecyclerAdapter";
    private List<T> listSong;
    private OnItemClickListener listener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    //    private boolean isMoreLoading = false;
//    private ScrollRecyclerListener.OnLoadMoreListener onLoadMoreListener;
    private OnCheckedChangeListener checkBoxChange;
    /**
     * support set event scroll RecyclerView
     *
     * @param listSong
     */

    public SongRecyclerAdapter(List<T> listSong) {
        this.listSong = listSong == null ? new ArrayList<T>() : listSong;
//        this.onLoadMoreListener = onLoadMoreListener;
    }

    public SongRecyclerAdapter(List<T> listSong, OnItemClickListener listener) {
        this.listSong = listSong;
        this.listener = listener;
//        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public SongRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_util, parent, false);
        return new SongRecyclerAdapter.ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final T item = listSong.get(position);
        holder.txtSongName.setText(item.getZSNAME());
        // Checkbox
        if (item.getFAVORITE() == 1){
            holder.cbFavorite.setChecked(true);
        } else{
            holder.cbFavorite.setChecked(false);
        }
        holder.cbFavorite.setOnClickListener(new View.OnClickListener() {
            boolean isChecked = holder.cbFavorite.isChecked();
            @Override
            public void onClick(View v) {
                isChecked = !isChecked;
                if (checkBoxChange != null) {
                    checkBoxChange.onCheckedChanged(
                                    isChecked,
                                    position,
                                    item.getId());
                }
                holder.cbFavorite.setChecked(isChecked);
            }
        });
        holder.txtAuthor.setText(item.getZSMETA());
        holder.txtSongCode.setText(String.valueOf(item.getZROWID()));
        holder.txtLyric.setText(item.getZSLYRIC());
    }

    public void updateCheckBoxView(int fav, final int pos) {
        listSong.get(pos).setFAVORITE(fav);
        notifyItemChanged(pos);
    }

    @Override
    public int getItemCount() {
        return listSong.size();
    }

    @Override
    public int getItemViewType(int position) {
        return listSong.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        OnItemClickListener listener;
        protected CheckBox cbFavorite;
        protected TextView txtSongName;
        protected TextView txtAuthor;
        protected TextView txtSongCode;
        protected TextView txtLyric;

        private void inIt(View itemView) {
            txtSongName = (TextView) itemView.findViewById(R.id.textView_item_name);
            cbFavorite = (CheckBox) itemView.findViewById(R.id.chb_item_favorite);
            txtAuthor = (TextView) itemView.findViewById(R.id.textView_item_author);
            txtSongCode = (TextView) itemView.findViewById(R.id.textView_item_song_code);
            txtLyric = (TextView) itemView.findViewById(R.id.TextView_item_lyric);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
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


    public T getItem(int position) {
        return listSong.get(position);
    }

    @SuppressWarnings("unused")
    public void add(int index, T elem) {
        listSong.add(index, elem);
        notifyDataSetChanged();
    }

    @SuppressWarnings("unused")
    public void addAll(List<T> elem) {
        listSong.clear();
        listSong.addAll(elem);
        notifyDataSetChanged();
    }

    public void addItemMore(List<T> elem) {
        final int positionStart = listSong.size() + 1;
        listSong.addAll(elem);
        notifyItemRangeInserted(positionStart, listSong.size());
    }

    @SuppressWarnings("unused")
    public void remove(T elem) {
        listSong.remove(elem);
        notifyDataSetChanged();
    }

    @SuppressWarnings("unused")
    public void remove(int index) {
        listSong.remove(index);
        notifyItemRemoved(index);
    }

    @SuppressWarnings("unused")
    public void replace(int index, T elem) {
        listSong.set(index, elem);
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> list) {
        listSong.clear();
        listSong.addAll(list);
        notifyDataSetChanged();
    }

    public void clearData() {
        listSong.clear();
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onItemLongClick(int position);
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(boolean isChecked, int positionAt, long idSong);
    }


    public void setCheckBoxChangeListner(OnCheckedChangeListener checkBoxChange) {
        this.checkBoxChange = checkBoxChange;
    }
}
