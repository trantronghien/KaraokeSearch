package com.example.hientran.karaoke_search;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Capnhat extends AppCompatActivity {

    DatabaseHelper helper;
    SQLiteDatabase db;
    Button btnupdate , btnhelp;
    String name,sname,sabbr,slang,slyric,smeta;
    int id;
    int favorite;
    TextView tv1,tv2,tv3,tv4;
    JSONArray mang;
    ArrayList<String> mid;
    ArrayList<String> mname ;
    ArrayList<String> msname;
    ArrayList<String> msabbr ;
    ArrayList<String> mslang ;
    ArrayList<String> mslyric;
    ArrayList<String> msmeta ;
    ArrayList<String> mfavorite;
    int mang1=2;
    JSONObject user;
    String a1;
    ProgressDialog pd;
    ImageView imgview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capnhat);

        ConnectivityManager manager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info != null && info.isConnected()){
            Toast.makeText(getApplicationContext(), "Netwwork is Available", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext() , "Netwwork is not Available" , Toast.LENGTH_SHORT).show();
        }

        btnupdate=(Button)findViewById(R.id.buttonupdate);
        btnhelp = (Button)findViewById(R.id.buttonhelp);

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Capnhat.this,Tab_Update.class);
                startActivity(i);
            }
        });
        btnhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Capnhat.this , Tab_Help.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }
}
