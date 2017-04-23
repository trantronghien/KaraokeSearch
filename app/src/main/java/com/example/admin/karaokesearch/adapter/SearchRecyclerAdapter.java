package com.example.admin.karaokesearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.admin.karaokesearch.R;
import com.example.admin.karaokesearch.models.Entities.SongTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 3/15/2017.
 */

public class SearchRecyclerAdapter<T extends SongTable> extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> implements Filterable {

    private MyFilter mFilter;
    private static List<String> mOriginItmeList;
    private static List<String> mItemList;
    private OnItemClick onItemClick;
    Context context;
    List<T> listSong;


    public SearchRecyclerAdapter(Context context, List<T> listSong , OnItemClick onItemClick){
        this.context = context;
        this.listSong  = listSong;
        this.onItemClick = onItemClick;
    }

    @Override
    public SearchRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchRecyclerAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recycler_util, parent, false), onItemClick);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        T item = listSong.get(position);
        holder.txtSongName.setText(item.getZSNAME());
        // checkbox
        if (item.getFAVORITE() == 1) holder.cb_favorite.setChecked(true);
        else holder.cb_favorite.setChecked(false);
        holder.cb_favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    // chk_favorite = 1
                }else {
                    // chk_favorite = 0
                }
            }
        });

        holder.txtAuthor.setText(item.getZSMETA());
        holder.txtSongCode.setText(String.valueOf(item.getZROWID()));
        holder.txtLyric.setText(item.getZSLYRIC());

    }

    @Override
    public int getItemCount() {
        return listSong.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener ,View.OnLongClickListener{

        protected CheckBox cb_favorite;
        protected TextView txtSongName;
        protected TextView txtAuthor;
        protected TextView txtSongCode;
        protected TextView txtLyric;
        private OnItemClick onItemClick;

        private void inIt(View view){
            txtSongName = (TextView) view.findViewById(R.id.textView_item_name);
            cb_favorite = (CheckBox) view.findViewById(R.id.chb_item_favorite);
            txtAuthor =  (TextView) view.findViewById(R.id.textView_item_author);
            txtSongCode =  (TextView) view.findViewById(R.id.textView_item_song_code);
            txtLyric =  (TextView) view.findViewById(R.id.TextView_item_lyric);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        public ViewHolder(View itemView , OnItemClick onItemClick) {
            super(itemView);
            inIt(itemView);
            this.onItemClick = onItemClick;
        }

        @Override
        public void onClick(View v) {
            onItemClick.onItemClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            onItemClick.onItemLongClick(getAdapterPosition());
            return false;
        }
    }

   //============================================================================
    //                               Filter search view
    //============================================================================
    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new MyFilter();
        }
        return mFilter;
    }

    private class MyFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            String search = constraint.toString();
            if (TextUtils.isEmpty(search)) {
                results.values = mOriginItmeList;
                results.count = mOriginItmeList.size();
            } else {
                ArrayList<String> tempList = new ArrayList();
                for (String item : mOriginItmeList) {
                    if (item == search) {
                        tempList.add(item);
                    }
                }
                results.values = tempList;
                results.count = tempList.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mItemList = (ArrayList<String>) results.values;
        }
    }

    public interface OnItemClick {
        void onItemClick(int position);
        void onItemLongClick(int position);
    }


}
