package com.example.hientran.karaoke_search;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.app.TabActivity;
import android.content.Intent;


public class MainActivity extends TabActivity  {

    SQLDatabaseSource db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);   // cố định màn hình nằm ngang
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);    // khóa xoay ngang màn hình
        db = new SQLDatabaseSource(this);

        TabHost tabHost = getTabHost();

        // tạo ra 2 tabSpec và thêm vào TabHost
        //=========== = Tab Tìm kiếm = ============//
        TabSpec timkiem = tabHost.newTabSpec("mục tìm kiếm ");
        // phần hiện thị của Tab
        timkiem.setIndicator("Tìm Kiếm");
        Intent timkiem_Intent = new Intent(MainActivity.this, Timkiem.class);
        timkiem.setContent(timkiem_Intent);



        //=========== = Tab Song = ============//
        TabSpec song = tabHost.newTabSpec("Tab song chứa list view song");
        song.setIndicator("Bài Hát");
        Intent song_Intent = new Intent(this, Song.class);
        song.setContent(song_Intent);


        //=========== = Tab yêu thích = ============//
        TabSpec yeuthich = tabHost.newTabSpec("tab Yêu Thích lưu trữ những bài hát yêu thích");
        yeuthich.setIndicator("Yêu Thích");
        Intent yeuthich_Intent = new Intent(this, YeuThich.class);
        yeuthich.setContent(yeuthich_Intent);

        //=========== = Tab Cập nhật = ============//
        TabSpec capnhat = tabHost.newTabSpec("cập nhật sql");
        capnhat.setIndicator("Cập nhật");
        Intent capnhat_Intent = new Intent(this, Capnhat.class);
        capnhat.setContent(capnhat_Intent);
    /*
        TabSpec
    */
        // thêm vào Tabhost
        tabHost.addTab(timkiem);
        tabHost.addTab(song);
        tabHost.addTab(yeuthich);
        tabHost.addTab(capnhat);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
