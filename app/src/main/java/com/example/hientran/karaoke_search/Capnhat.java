package com.example.hientran.karaoke_search;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;



public class Capnhat extends AppCompatActivity {

    Button btnupdate , btnhelp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capnhat);

//        ConnectivityManager manager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
//        NetworkInfo info = manager.getActiveNetworkInfo();
//        if(info != null && info.isConnected()){
//            Toast.makeText(getApplicationContext(), "Netwwork is Available", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(getApplicationContext() , "Netwwork is not Available" , Toast.LENGTH_SHORT).show();
//        }


        btnupdate=(Button)findViewById(R.id.buttonupdate);
        btnhelp = (Button)findViewById(R.id.buttonhelp);

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(Capnhat.this,Tab_Update.class);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
        btnhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Capnhat.this , Tab_Help.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    // bắt phím back




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }


}
