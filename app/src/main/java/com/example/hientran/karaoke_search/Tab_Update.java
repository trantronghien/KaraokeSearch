package com.example.hientran.karaoke_search;



import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;

//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

public class Tab_Update extends Activity {
	ProgressDialog pd;
	String a1;
	String name,sname,sabbr,slang,slyric,smeta;
	int id;
	int smanufacture;
	int favorite;
	DatabaseHelper helper;
	SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				new ReadJson().execute("#");  // web đọc web


			}
		});
	}

	public class ReadJson extends AsyncTask<String, Integer, String>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		    pd = ProgressDialog.show(Tab_Update.this,"Thông Báo","Đang Tải CSDL...",true);
		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			//String chuoi1=getXmlFromUrl(params[0]);
			//return chuoi1;
            return null;
		}
		@Override
		protected void onPostExecute(String s) {
			// TODO Auto-generated method stub
			if(pd.isShowing())
			{
		     pd.dismiss();
				if(s==null)
				{
					Toast.makeText(getApplicationContext(),"Không Tìm Thấy Dữ Liệu Hoặc Kết Nối Bị Lỗi...",
							Toast.LENGTH_LONG).show();
				}
			else
			{
				try {
					JSONObject root =new JSONObject(s);
					JSONArray mang = root.getJSONArray("BaiHat");
					for (int i=0; i<mang.length();i++){

						JSONObject user = mang.getJSONObject(i);

					 id=Integer.parseInt(user.getString("_id"));
					 name=user.getString("name");
					 sname=user.getString("snameclean");
					 sabbr=user.getString("sabbr");
					 smanufacture=Integer.parseInt(user.getString("smanufacture"));
					 slang=user.getString("slanguage");
				    slyric=user.getString("slyric");
					 smeta=user.getString("smeta");
					 favorite=Integer.parseInt(user.getString("favorite"));
						helper=new DatabaseHelper(getBaseContext());
						db=helper.openDatabase();
					    helper.insert(id,name,sname,sabbr,smanufacture,slang,slyric,smeta,favorite);
			}
					helper.close();
					Toast.makeText(getApplicationContext(),"Bạn đã Cập Nhật Thành Công",Toast.LENGTH_LONG).show();
					Intent i=new Intent(Tab_Update.this,MainActivity.class);
					startActivity(i);
					}
			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}}

	}



//	public String getXmlFromUrl(String urlString) {
//        String xml = null;
//        try {
//            // defaultHttpClient lấy toàn bộ dữ liệu trong http đổ vào 1 chuỗi String
//            DefaultHttpClient httpClient = new DefaultHttpClient();
//            HttpPost httpPost = new HttpPost(urlString);
//
//            HttpResponse httpResponse = httpClient.execute(httpPost);
//            HttpEntity httpEntity = httpResponse.getEntity();
//            xml = EntityUtils.toString(httpEntity, HTTP.UTF_8);
//            // set UTF-8 cho ra chữ unikey
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        // return XML
//        return xml;
//    }

}
}
