package com.example.admin.karaokesearch.views.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.karaokesearch.R;

import java.util.ArrayList;

/**
 * Created by admin on 8/11/2017.
 */

public class DialogListAdapter extends BaseAdapter {
    private ArrayList<String> list;
    private Context context;

    public DialogListAdapter(ArrayList<String> list, Context context) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_spinner_list, parent , false);
            holder = new ViewHolder();
            holder.txtItem = (TextView) convertView.findViewById(R.id.tv_tinted_spinner);
            holder.txtItem.setTextColor(context.getColor(R.color.black_text));
            holder.txtItem.setGravity(Gravity.CENTER);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtItem.setText(list.get(position).toString());
        return convertView;
    }

    private static class ViewHolder {
        TextView txtItem;
    }

}
