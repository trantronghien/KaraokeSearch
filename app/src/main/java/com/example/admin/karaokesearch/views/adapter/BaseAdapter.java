package com.example.admin.karaokesearch.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.karaokesearch.models.SongTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 20/11/2017.
 */

public abstract class BaseAdapter<T extends SongTable> extends RecyclerView.Adapter<BaseAdapter.ViewHolder> {

    protected BaseAdapter.OnCheckedChangeListener checkBoxChange;
    private BaseAdapter.OnItemClickListener onItemClick;

    private List<T> listSong;

    protected BaseAdapter(List<T> listSong){
        this.listSong = listSong;
        this.listSong = listSong == null ? new ArrayList<T>() : listSong;
    }

    public BaseAdapter(List<T> listSong, BaseAdapter.OnItemClickListener listener) {
        this.listSong = listSong;
        this.onItemClick = listener;
    }

    @Override
    public int getItemCount() {
        return listSong.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(getItemResource(), parent, false), onItemClick);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        onBindItemViewHolder(holder, position);
    }

    public abstract int getItemResource();

    public abstract void onBindItemViewHolder(BaseAdapter.ViewHolder holder, int position);


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
    public void remove(int index ,T elem) {
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
        this.onItemClick = listener;
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

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private SparseArray<View> views = new SparseArray<>();
        private View convertView;
        private OnItemClickListener onItemClick;

        public ViewHolder(View itemView, OnItemClickListener onItemClick) {
            super(itemView);
            convertView = itemView;
            convertView.setOnClickListener(this);
            convertView.setOnLongClickListener(this);
            this.onItemClick = onItemClick;
        }

        @SuppressWarnings("unchecked")
        public <T extends View> T getView(int resId) {
            View v = views.get(resId);
            if (null == v) {
                v = convertView.findViewById(resId);
                views.put(resId, v);
            }
            return (T) v;
        }


        @Override
        public void onClick(View v) {
            onItemClick.onItemClick(getLayoutPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            onItemClick.onItemLongClick(getLayoutPosition());
            return true;
        }
    }
}
