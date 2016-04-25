package com.example.hientran.karaoke_search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Timkiem extends Activity implements SearchView.OnQueryTextListener {
    public final static String ID_EXTRA="com.example.hientran.karaoke_search._id";
    public final static String TITLE_EXTRA="com.example.hientran.karaoke_search.sname";
    public final static String LYRIC_EXTRA="com.example.hientran.karaoke_search.slyric";
    public final static String SOURCE_EXTRA="com.example.hientran.karaoke_search.smeta";
    public final static String FAVORITE_EXTRA="com.example.hientran.karaoke_search.favorite";

    SQLDatabaseSource db;
    List<SongQuery> list;
    ListView lvhienthi;
    Custom_layout_listview adapter;
    SearchView searchView;
    String a="";
    String b="......";
    int a1=0;
    ArrayAdapter<String> adapter1;
    int cc =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timkiem);

        lvhienthi = (ListView)findViewById(R.id.lvhienthi);

        db=new SQLDatabaseSource(this);
        list = new ArrayList<SongQuery>();
        list=db.danhsachbaihattheofavorite1();
        setAdapterListView(list);
        lvhienthi.setOnItemClickListener(onListClick);
        searchView=(SearchView)findViewById(R.id.sv1);
        searchView.setOnQueryTextListener(this);



        lvhienthi.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //hide KB
                InputMethodManager imm =  (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
            }

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                cc=firstVisibleItem;
            }
        });


    }

    public void setAdapterListView(List<SongQuery> list){
        adapter = new Custom_layout_listview(this, R.layout.activity_custom_layout_listview, list);
        lvhienthi.setAdapter(adapter);
    }



    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        {
            if(adapter!=null)
            {
                list=db.danhsachbaihattheoma(a.toLowerCase().toString());
                setAdapterListView(list);
                lvhienthi.setSelection(cc);
            }
        }
    }

    private AdapterView.OnItemClickListener onListClick=new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
            // When clicked, show a toast with the TextView text
            Intent i=new Intent(Timkiem.this,customtable2.class);
            i.putExtra(TITLE_EXTRA,adapter.getItem(position).getSname());
            i.putExtra(LYRIC_EXTRA,adapter.getItem(position).getSlyric());
            i.putExtra(SOURCE_EXTRA,adapter.getItem(position).getSmeta());
            i.putExtra(ID_EXTRA,adapter.getItem(position).get_id());
            i.putExtra(FAVORITE_EXTRA,adapter.getItem(position).getFavorite());
            a1=position;
            startActivity(i);

        };
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//   Inflate the menu; this adds items to the action bar if it is present.
//	super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();

//        inflater.inflate(R.menu.main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//   Handle action bar item clicks here. The action bar will
//   automatically handle clicks on the Home/Up button, so long
//   as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    // cập nhật thêm tìm kiếm tên ca sĩ
    @Override
    public boolean onQueryTextChange(String chuoi) {

        list = db.danhsachbaihattheoma(chuoi.toLowerCase().toString()); //
        a = chuoi;
        setAdapterListView(list);
        adapter.notifyDataSetChanged();
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String chuoi) {
        return false;
    }

    public static void hideKeyboard( Context context ) {

        try {
            InputMethodManager inputManager = ( InputMethodManager ) context.getSystemService(Context.INPUT_METHOD_SERVICE);

            View view = ( (Activity) context ).getCurrentFocus();
            if ( view != null ) {
                inputManager.hideSoftInputFromWindow( view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS );
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        int dem = 0;
//        if(keyCode == KeyEvent.KEYCODE_BACK){
//            if(dem == 2){
//                System.exit(0);  // thoát
//            }
//            Toast.makeText(Timkiem.this, " Nhấn back lần nữa để thoát ", Toast.LENGTH_SHORT).show();
//            dem++;
//
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
