package com.example.hientran.karaoke_search;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by hientran on 2/5/2016.
 */
public class DatabaseHelper  extends SQLiteOpenHelper {

    public static final String DATASE_NAME = "karaokevietnam";
    public static final String TABLE_SONG = "song";

    public static final String SONG_ID = "_id";
    public static final String SONG_TITLE = "sname";
    public static final String SONG_TITLE_SIMPLE = "snameclean";
    public static final String SONG_TITLE_MINI = "sabbr";
    public static final String SONG_MANU = "smanufacture";
    public static final String SONG_LANG = "slanguage";
    public static final String SONG_LYRIC = "slyric";
    public static final String SONG_SOURCE = "smeta";
    public static final String SONG_FAVORITE = "favorite";


    private static String DB_PATH = "/data/data/com.example.hientran.karaoke_search/databases/";
    private static String DB_PATH1 = "/data/data/com.example.hientran.karaoke_search/";
    Context context ;
    String duongdanDatabase = "";
    DatabaseHelper DBHelper;     // sử dụng update




//    public long insert1(String a , String b , String c , String d , String i ,String e, String f, String g, String h){
//        ContentValues values = new ContentValues();
//        values.put(SONG_ID, a);
//        values.put(SONG_TITLE, b);
//        values.put(SONG_TITLE_SIMPLE, c);
//        values.put(SONG_TITLE_MINI, d);
//        values.put(SONG_MANU, i);
//        values.put(SONG_LANG, e);
//        values.put(SONG_LYRIC, f);
//        values.put(SONG_SOURCE, g);
//        values.put(SONG_FAVORITE, h);
//        SQLiteDatabase db = DBHelper.getWritableDatabase();
//
//        return db.insert(TABLE_SONG, null, values);
//    }

    //insert
    public boolean insert(int a , String b , String c , String d , int i ,String e, String f, String g, int h){
        ContentValues values = new ContentValues();
        SQLiteDatabase database = this.getWritableDatabase();
        values.put(SONG_ID, a);
        values.put(SONG_TITLE, b);
        values.put(SONG_TITLE_SIMPLE, c);
        values.put(SONG_TITLE_MINI, d);
        values.put(SONG_MANU, i);
        values.put(SONG_LANG, e);
        values.put(SONG_LYRIC, f);
        values.put(SONG_SOURCE, g);
        values.put(SONG_FAVORITE, h);
        long result = database.insert(TABLE_SONG, null, values);
        if(result == - 1){
            return false;
        } else {
            return true;
        }

    }

    // phần update
    public int update(String _id, int name) {
        ContentValues args = new ContentValues();
        args.put(SONG_FAVORITE, name);
        DBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        return db.update(TABLE_SONG, args, SONG_ID + "=" + _id, null);
    }

    // đường dẫn data/data/package của mình

    public DatabaseHelper(Context context) {
        super(context, DATASE_NAME, null, 1);
        this.context = context;
        duongdanDatabase = DB_PATH +DATASE_NAME;   // sửa đổi ở đyâ
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public SQLiteDatabase openDatabase(){
        return SQLiteDatabase.openDatabase(duongdanDatabase , null , SQLiteDatabase.OPEN_READWRITE); // biến flags là SQLiteDatabase.OPEN_READWRITE cho phép đọc và ghi
    }
    // coppy file database vào máy
    public void coppyDatabase()  {
        try {
            InputStream input = context.getAssets().open(DATASE_NAME);  // context là activity nào gọi nó
            OutputStream out = new FileOutputStream(DB_PATH + DATASE_NAME);
            byte[] buffer = new byte[1024];  // 1kb = 1024 byte
            int lenght = 0;   // biến đo dung lương file data base

            // mỗi lần đọc input.read(buffer) mỗi lần đọc thì truyền dữ liệu vào buffer và buffer giảm xuống đồng thời lenght tăng lên
            while ((lenght = input.read(buffer)) > 0){
                out.write(buffer , 0 , lenght);   // buffer là luồng  , ghi từ 0M đến hết lenght
            }
            out.flush();  // đẩy dữ liệu vào
            out.close();
            input.close();
            Log.d("coppydata", "coppy thành công ");
        } catch (IOException e) {
            Log.d("coppydata" , "coppy không thành công ");
            e.printStackTrace();
        }
    }



    // hàm tạo thông qua hàm kiểm tra để kiểm tra và coppy dữ liệu vào mấy luôn
    public void createDatabase (){
        boolean kt = KiemTraDB();
        if(kt){
            Log.d("ketnoi" , "máy đã có Databases");
        }
        else{
            this.getWritableDatabase();       //gọi phương thức này nó sẽ khởi tạo ra Data base nhưng không có dữ liệu
            coppyDatabase();
            openDatabase();

            Log.d("ketnoi", "máy chưa có Database coppy dữ liệu");
        }
    }

    // kiểm tra nếu như open thành công thì trả ra true ngược lại trả false
    public boolean KiemTraDB(){
        SQLiteDatabase kiemtraDB = null;
        try {
            kiemtraDB = SQLiteDatabase.openDatabase(duongdanDatabase , null , SQLiteDatabase.OPEN_READONLY);
            Log.d("ketnoi" , "kết nối thành công");
        } catch (Exception e){
            Log.d("ketnoi" , "kết nối không thành công");
        }
        if(kiemtraDB != null ){
            kiemtraDB.close();
        }
        return ( kiemtraDB != null ? true : false );
    }
}
