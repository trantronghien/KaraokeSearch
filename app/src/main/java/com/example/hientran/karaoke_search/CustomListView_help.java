package com.example.hientran.karaoke_search;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by hientran on 4/4/2016.
 */
public class CustomListView_help extends ArrayAdapter<String> {

    private final Activity content;
    private final Integer[] hinh;
    private final String[] itemname;

    public CustomListView_help(Activity content, Integer[] image, String[] itemname) {
        super(content , R.layout.item_listview_help , itemname);
        this.content = content;
        this.hinh = image;
        this.itemname = itemname;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = content.getLayoutInflater();
        View v = inflater.inflate(R.layout.item_listview_help, null, true);

        ImageView image  = (ImageView)v.findViewById(R.id.hinhhelp);
        TextView chuoi = (TextView)v.findViewById(R.id.loidan);
        image.setImageResource(hinh[position]);
        chuoi.setText(itemname[position]);
        return v;
    }
}
