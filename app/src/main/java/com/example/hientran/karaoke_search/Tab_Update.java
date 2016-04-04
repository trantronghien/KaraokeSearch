package com.example.hientran.karaoke_search;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class Tab_Update extends Activity {
    ProgressDialog pd;
    String name, sname, sabbr, slang, slyric, smeta;
    int id;
    int smanufacture;
    int favorite;
    DatabaseHelper helper;
    SQLiteDatabase db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //http://tangchi83.16mb.com/JSON/karaoke.json
                new ReadJson().execute("http://tangchi83.16mb.com/JSON/karaoke.json");


            }
        });
    }

    public class ReadJson extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pd = ProgressDialog.show(Tab_Update.this, "Thông Báo", "Đang Tải CSDL...", true);
        }

        @Override
        protected String doInBackground(String... params) {

            String chuoi1 = getXmlFromUrl(params[0]);
            return chuoi1;

        }

        @Override
        protected void onPostExecute(String s) {

            if (pd.isShowing()) {
                pd.dismiss();
                if (s == null) {
                    Toast.makeText(getApplicationContext(), "Kiểm tra kết nối của bạn ...",
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Tab_Update.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    try {
                        JSONObject root = new JSONObject(s);
                        JSONArray mang = root.getJSONArray("Karaoke");

                        for (int i = 0; i < mang.length(); i++) {
                            JSONObject user = mang.getJSONObject(i);

                            id = Integer.parseInt(user.getString("_id"));
                            name = user.getString("name");
                            sname = user.getString("snameclean");
                            sabbr = user.getString("sabbr");
                            smanufacture = Integer.parseInt(user.getString("smanufacture"));
                            slang = user.getString("slanguage");
                            slyric = user.getString("slyric");
                            smeta = user.getString("smeta");
                            favorite = Integer.parseInt(user.getString("favorite"));

                            // khởi tạo và insert Json vào data
                            //Toast.makeText(getApplicationContext() , name , Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getApplicationContext() , s ,Toast.LENGTH_SHORT).show();
                            helper = new DatabaseHelper(getBaseContext());
                            helper.openDatabase();
                            boolean check;
                            //try{
                                check = helper.insert(id, name, sname, sabbr, smanufacture, slang, slyric, smeta, favorite);
                                if(check == true) {
                                    Log.d("ket noi", "ket noi insert thanh cong");
                                }
                           // }catch (NullPointerException e){}
                            else{
                                    Log.d("ket noi" , "ket noi insert khong thanh cong");
                                }
                        }
                        helper.close();
                        Toast.makeText(getApplicationContext(), "Bạn đã Cập Nhật Thành Công", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Tab_Update.this, MainActivity.class);
                        startActivity(intent);
                    } catch (JSONException e) {
                        Log.d("Json", "đọc Json lỗi");
                    }
                }
            }

        }

        public String getXmlFromUrl(String urlString) {
            String xml = null;
            try {
                // defaultHttpClient lấy toàn bộ dữ liệu trong http đổ vào 1 chuỗi String
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urlString);

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                xml = EntityUtils.toString(httpEntity, HTTP.UTF_8);
                // set UTF-8 cho ra chữ unikey
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return xml;
        }

    }
}

