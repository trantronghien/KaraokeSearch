package com.example.hientran.karaoke_search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class YeuThich extends AppCompatActivity implements OnScrollListener {
    SQLDatabaseSource db;
    List<SongQuery> list;
    Custom_layout_listview adapter;
    ListView lvshow;
    int cc = 0;

    public final static String ID_EXTRA = "com.example.hientran.karaoke_search._id";
    public final static String TITLE_EXTRA = "com.example.hientran.karaoke_search.sname";
    public final static String LYRIC_EXTRA = "com.example.hientran.karaoke_search.slyric";
    public final static String SOURCE_EXTRA = "com.example.hientran.karaoke_search.smeta";
    public final static String FAVORITE_EXTRA = "com.example.hientran.karaoke_search.favorite";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeu_thich);
        lvshow = (ListView)findViewById(R.id.listViewyeuthich);
        db = new SQLDatabaseSource(this);
        list = new ArrayList<SongQuery>();
        list = db.danhsachbaihattheofavorite();
        setAdapterListView(list);
        lvshow.setOnItemClickListener(onListClick);
        lvshow.setOnScrollListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }
    private void setAdapterListView(List<SongQuery> list){
        adapter = new Custom_layout_listview(this , R.layout.activity_custom_layout_listview , list);
        lvshow.setAdapter(adapter);
    }

    @Override
    protected void onResume(){
        super.onResume();

        if(adapter != null){
            list = db.danhsachbaihattheofavorite();
            setAdapterListView(list);
            lvshow.setSelection(cc);
        }
    }
    private AdapterView.OnItemClickListener onListClick=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // When clicked, show a toast with the TextView text
            Intent i = new Intent(YeuThich.this, customtable2.class);
            i.putExtra(TITLE_EXTRA, adapter.getItem(position).getSname());
            i.putExtra(LYRIC_EXTRA, adapter.getItem(position).getSlyric());
            i.putExtra(SOURCE_EXTRA, adapter.getItem(position).getSmeta());
            i.putExtra(ID_EXTRA, adapter.getItem(position).get_id());
            i.putExtra(FAVORITE_EXTRA, adapter.getItem(position).getFavorite());
            startActivity(i);
        }
    };

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub

    }
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // TODO Auto-generated method stub
        int cc=firstVisibleItem;
    }
}
