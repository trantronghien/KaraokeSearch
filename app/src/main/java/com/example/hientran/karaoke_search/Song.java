package com.example.hientran.karaoke_search;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.content.Intent;
import android.widget.AbsListView.OnScrollListener;


import java.util.ArrayList;
import java.util.List;

public class Song extends Activity implements OnScrollListener {
    SQLDatabaseSource db;
    List<SongQuery> list;

    Custom_layout_listview adapter;
    ListView lvshow;
    String arr[] = {"Arirang-Nhạc Việt (5 Số)", "Arirang-Nhạc Anh (5 Số)", "California-Nhạc Việt (6 Số)",
            "California-Nhạc Anh (6 Số)"};
    String arr2[] = { "All", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X", "Y", "Z" };
    Spinner spin, spin1;
    int cc = 0;
    String bb;
    ArrayAdapter<String> adapter1;
    ArrayAdapter<String> adapter2;
    public final static String ID_EXTRA = "com.example.hientran.karaoke_search._id";
    public final static String TITLE_EXTRA = "com.example.hientran.karaoke_search.sname";
    public final static String LYRIC_EXTRA = "com.example.hientran.karaoke_search.slyric";
    public final static String SOURCE_EXTRA = "com.example.hientran.karaoke_search.smeta";
    public final static String FAVORITE_EXTRA = "com.example.hientran.karaoke_search.favorite";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        lvshow = (ListView)findViewById(R.id.lvhienthi);
        spin = (Spinner) findViewById(R.id.spinner1);
        spin1 = (Spinner) findViewById(R.id.spinner2);
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr);
        adapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);  //

        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr2);
        adapter2.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        spin.setAdapter(adapter1);
        spin1.setAdapter(adapter2);

        spin.setOnItemSelectedListener(new MyProcessEvent());
        spin1.setOnItemSelectedListener(new MyProcessEvent1());

        lvshow.setOnItemClickListener(onListClick);
        lvshow.setOnScrollListener(this);


        db = new SQLDatabaseSource(this);
        list = new ArrayList<SongQuery>();

        list = db.danhsachbaihat();
        adapter = new Custom_layout_listview(this , R.layout.activity_custom_layout_listview , list);
        lvshow.setAdapter(adapter);
    }

    private class MyProcessEvent implements OnItemSelectedListener {
        public void onNothingSelected(AdapterView<?> arg0) {
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            switch (position) {
                case 0:

                    list = db.danhsachbaihattheovn();
                    setAdapterListView(list);
                    break;
                case 1:

                    list = db.danhsachbaihattheoen();
                    setAdapterListView(list);
                    break;
                case 2:
                    list = db.danhsachbaihattheovn1();
                    setAdapterListView(list);
                    break;
                case 3:

                    list = db.danhsachbaihattheoen1();
                    setAdapterListView(list);
                    break;
            }
        }
    }

    private class MyProcessEvent1 implements OnItemSelectedListener {
        public void onNothingSelected(AdapterView<?> arg0) {
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            if (spin.getSelectedItem().toString().equals("Arirang-Nhạc Việt (5 Số)")) {
                bb = spin1.getSelectedItem().toString();
                db.chonso(bb);
                list = db.danhsachbaihattheovn();
                setAdapterListView(list);
            }
            if (spin.getSelectedItem().toString().equals("Arirang-Nhạc Anh (5 Số)")) {
                bb = spin1.getSelectedItem().toString();
                db.chonso(bb);
                list = db.danhsachbaihattheoen();
                setAdapterListView(list);
            }
            if (spin.getSelectedItem().toString().equals("California-Nhạc Việt (6 Số)")) {
                bb = spin1.getSelectedItem().toString();
                db.chonso(bb);
                list = db.danhsachbaihattheovn1();
                setAdapterListView(list);
            }
            if (spin.getSelectedItem().toString().equals("California-Nhạc Anh (6 Số)")) {
                bb = spin1.getSelectedItem().toString();
                db.chonso(bb);
                list = db.danhsachbaihattheoen1();
                setAdapterListView(list);
            }
        }
    }

    // phần listView
    private void setAdapterListView(List<SongQuery> list) {    //
        adapter = new Custom_layout_listview(this, R.layout.activity_custom_layout_listview, list);
        lvshow.setAdapter(adapter);
        bb = spin1.getSelectedItem().toString();
        db.chonso(bb);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }


    // chức năng bấm hiện bảng thông tin khi nhấn item
    private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // When clicked, show a toast with the TextView text
            Intent i = new Intent(Song.this, customtable2.class);
            i.putExtra(TITLE_EXTRA, adapter.getItem(position).getSname());
            i.putExtra(LYRIC_EXTRA, adapter.getItem(position).getSlyric());
            i.putExtra(SOURCE_EXTRA, adapter.getItem(position).getSmeta());
            i.putExtra(ID_EXTRA, adapter.getItem(position).get_id());
            i.putExtra(FAVORITE_EXTRA, adapter.getItem(position).getFavorite());
            startActivity(i);
        };
    };

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        if (spin.getSelectedItem().toString().equals("Arirang-Nhạc Việt (5 Số)")) {
            list = db.danhsachbaihattheovn();
            setAdapterListView(list);
            lvshow.setSelection(cc);
        }
        if (spin.getSelectedItem().toString().equals("Arirang-Nhạc Anh (5 Số)")) {
            list = db.danhsachbaihattheoen();
            setAdapterListView(list);
            lvshow.setSelection(cc);
        }
        if (spin.getSelectedItem().toString().equals("California-Nhạc Việt (6 Số)")) {
            list = db.danhsachbaihattheovn1();
            setAdapterListView(list);
            lvshow.setSelection(cc);
        }
        if (spin.getSelectedItem().toString().equals("California-Nhạc Anh (6 Số)")) {
            list = db.danhsachbaihattheoen1();
            setAdapterListView(list);
            lvshow.setSelection(cc);
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // TODO Auto-generated method stub
        cc = firstVisibleItem;
    }


}
