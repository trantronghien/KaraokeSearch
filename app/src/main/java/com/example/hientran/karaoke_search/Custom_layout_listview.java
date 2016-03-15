package com.example.hientran.karaoke_search;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

public class Custom_layout_listview extends ArrayAdapter<SongQuery> {

    Context context;
    int resource;
    List<SongQuery> object;
    CheckBox chbox;
    DatabaseHelper helper;
    int giatricheck;
    SQLiteDatabase db;


    public Custom_layout_listview(Context context, int resource, List<SongQuery> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.object = objects;
    }

    // khởi tạo cho nó 1 giao diện
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, resource, null);
        TextView txtvId = (TextView) view.findViewById(R.id.textViewId);
        TextView txtvtenbaihat = (TextView) view.findViewById(R.id.textViewTenbaihat);
        TextView txtvloibat = (TextView) view.findViewById(R.id.textViewloibaihat);
        chbox = (CheckBox) view.findViewById(R.id.checkboxlike);


        final SongQuery item = object.get(position);  // lấy vị trí của 1 đối tượng
        txtvId.setText(item.get_id().toString());
        txtvtenbaihat.setText(item.getSname().toString());
        txtvloibat.setText(item.getSlyric().toString());
        //
        if (item.getFavorite() == 1){
            chbox.setChecked(true);
        }else {
            chbox.setChecked(false);
        }
        chbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    giatricheck = 1;
                    helper =  new DatabaseHelper(context);
                    db = helper.openDatabase();
                    helper.update(item.get_id(), giatricheck);
                    item.setFavorite(1);
                }
                else {
                    giatricheck = 0 ;
                    helper = new DatabaseHelper(context);
                    db = helper.openDatabase();
                    helper.update(item.get_id(), giatricheck);
                    item.setFavorite(0);
                }
            }
        });
        return view;
    }
}
