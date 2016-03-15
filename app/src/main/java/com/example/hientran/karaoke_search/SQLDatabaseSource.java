package com.example.hientran.karaoke_search;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by hientran on 2/16/2016.
 */
public class SQLDatabaseSource {
    SQLiteDatabase db;
    DatabaseHelper helper;
    String a;

    public SQLDatabaseSource(Context context)
    {
        helper=new DatabaseHelper(context);
        helper.createDatabase();
        db=helper.openDatabase();

    }
    public void chonso(String bb)
    {
        a=bb;
        if(a.equals("All"))
        {
            a="";
        }
    }
    public List<SongQuery> danhsachbaihattheoma(String tenbaihat)
    {
        List<SongQuery> list=new ArrayList<SongQuery>();
        if(tenbaihat.isEmpty())
        {
            tenbaihat="......";
            String[] Column={DatabaseHelper.SONG_ID,DatabaseHelper.SONG_TITLE,DatabaseHelper.SONG_TITLE_SIMPLE,DatabaseHelper.SONG_TITLE_MINI,DatabaseHelper.SONG_LYRIC,DatabaseHelper.SONG_SOURCE,DatabaseHelper.SONG_FAVORITE};
            String truyvan="Select " + Column[0]+ "," + Column[1]
                    + " , " + Column[2] + " , " + Column[3] + " , " + Column[4]+ " , " + Column[5] + " , " + Column[6] +  " From "
                    + DatabaseHelper.TABLE_SONG +  " Where " + DatabaseHelper.SONG_TITLE_SIMPLE + " LIKE '" + tenbaihat.toLowerCase() +"%' "+ " OR " + DatabaseHelper.SONG_TITLE_MINI + " LIKE '"+ tenbaihat.toLowerCase() +"%' ";
//        +"Or" + DatabaseHelper.SONG_TITLE_MINI +" LIKE '%" + tenbaihat.toLowerCase() +"%' ";
            Cursor c=db.rawQuery(truyvan,null);
            c.moveToFirst();
            while(!c.isAfterLast())
            {
                SongQuery item=new SongQuery();
                item.set_id(c.getString(0));
                item.setSname(c.getString(1));
                item.setSnameclean(c.getString(2));
                item.setSlyric(c.getString(4));
                item.setSmeta(c.getString(5));
                item.setFavorite(c.getInt(6));
                list.add(item);
                c.moveToNext();
            }
            c.close();
            return list;
        }
        else
        {
            String[] Column={DatabaseHelper.SONG_ID,DatabaseHelper.SONG_TITLE,DatabaseHelper.SONG_TITLE_SIMPLE,DatabaseHelper.SONG_TITLE_MINI,DatabaseHelper.SONG_LYRIC,DatabaseHelper.SONG_SOURCE,DatabaseHelper.SONG_FAVORITE};
            String truyvan="Select " + Column[0]+ "," + Column[1]
                    + " , " + Column[2] + " , " + Column[3] + " , " + Column[4]+ " , " + Column[5] + " , " + Column[6] +  " From "
                    + DatabaseHelper.TABLE_SONG +  " Where " + DatabaseHelper.SONG_TITLE_SIMPLE + " LIKE '" + tenbaihat.toLowerCase() +"%' "+ " OR " + DatabaseHelper.SONG_TITLE_MINI + " LIKE '"+ tenbaihat.toLowerCase() +"%' ";
//             +"Or" + DatabaseHelper.SONG_TITLE_MINI +" LIKE '%" + tenbaihat.toLowerCase() +"%' ";
            Cursor c=db.rawQuery(truyvan,null);
            c.moveToFirst();
            while(!c.isAfterLast())
            {
                SongQuery item=new SongQuery();
                item.set_id(c.getString(0));
                item.setSname(c.getString(1));
                item.setSnameclean(c.getString(2));
                item.setSlyric(c.getString(4));
                item.setSmeta(c.getString(5));
                item.setFavorite(c.getInt(6));
                list.add(item);
                c.moveToNext();
            }
            c.close();
            return list;
        }
    }




    public List<SongQuery> danhsachbaihattheofavorite()
    {
        List<SongQuery> list=new ArrayList<SongQuery>();

        String[] Column={DatabaseHelper.SONG_ID,DatabaseHelper.SONG_TITLE,DatabaseHelper.SONG_TITLE_SIMPLE,DatabaseHelper.SONG_LYRIC,DatabaseHelper.SONG_SOURCE,DatabaseHelper.SONG_FAVORITE};
        String truyvan="Select " + Column[0]+ "," + Column[1]
                + " , " + Column[2] + " , " + Column[3] + " , " + Column[4]+ " , " + Column[5] +  " From "
                + DatabaseHelper.TABLE_SONG +  " Where "
                + DatabaseHelper.SONG_FAVORITE +"=1";
        Cursor c=db.rawQuery(truyvan,null);
        c.moveToFirst();
        while(!c.isAfterLast())
        {
            SongQuery item=new SongQuery();
            item.set_id(c.getString(0));
            item.setSname(c.getString(1));
            item.setSnameclean(c.getString(2));
            item.setSlyric(c.getString(3));
            item.setSmeta(c.getString(4));
            item.setFavorite(c.getInt(5));

            list.add(item);
            c.moveToNext();
        }
        c.close();
        return list;
    }

    public List<SongQuery> danhsachbaihattheovn()
    {
        List<SongQuery> list=new ArrayList<SongQuery>();

        String[] Column={DatabaseHelper.SONG_ID,DatabaseHelper.SONG_TITLE,DatabaseHelper.SONG_TITLE_SIMPLE,DatabaseHelper.SONG_LYRIC,DatabaseHelper.SONG_SOURCE,DatabaseHelper.SONG_FAVORITE};
        String truyvan="Select " + Column[0]+ "," + Column[1]
                + " , " + Column[2] + " , " + Column[3] + " , " + Column[4]+ " , " + Column[5] +  " From "
                + DatabaseHelper.TABLE_SONG +  " Where "
                + DatabaseHelper.SONG_LANG +" LIKE 'vn' " + " AND "+ DatabaseHelper.SONG_MANU + " LIKE '1'" + " AND "+ DatabaseHelper.SONG_TITLE_MINI + " LIKE " +"'"+ a +"%'" ;
        Cursor c=db.rawQuery(truyvan,null);
        c.moveToFirst();
        while(!c.isAfterLast())
        {
            SongQuery item=new SongQuery();
            item.set_id(c.getString(0));
            item.setSname(c.getString(1));
            item.setSnameclean(c.getString(2));
            item.setSlyric(c.getString(3));
            item.setSmeta(c.getString(4));
            item.setFavorite(c.getInt(5));

            list.add(item);
            c.moveToNext();
        }
        c.close();
        return list;
    }

    public List<SongQuery> danhsachbaihattheovn1()
    {
        List<SongQuery> list=new ArrayList<SongQuery>();

        String[] Column={DatabaseHelper.SONG_ID,DatabaseHelper.SONG_TITLE,DatabaseHelper.SONG_TITLE_SIMPLE,DatabaseHelper.SONG_LYRIC,DatabaseHelper.SONG_SOURCE,DatabaseHelper.SONG_FAVORITE};
        String truyvan="Select " + Column[0]+ "," + Column[1]
                + " , " + Column[2] + " , " + Column[3] + " , " + Column[4]+ " , " + Column[5] +  " From "
                + DatabaseHelper.TABLE_SONG +  " Where "
                + DatabaseHelper.SONG_LANG +" LIKE 'vn' " + " AND "+ DatabaseHelper.SONG_MANU + " LIKE '2'" + " AND "+ DatabaseHelper.SONG_TITLE_MINI + " LIKE " +"'"+ a +"%'" ;
        Cursor c=db.rawQuery(truyvan,null);
        c.moveToFirst();
        while(!c.isAfterLast())
        {
            SongQuery item=new SongQuery();
            item.set_id(c.getString(0));
            item.setSname(c.getString(1));
            item.setSnameclean(c.getString(2));
            item.setSlyric(c.getString(3));
            item.setSmeta(c.getString(4));
            item.setFavorite(c.getInt(5));

            list.add(item);
            c.moveToNext();
        }
        c.close();
        return list;
    }

    public List<SongQuery> danhsachbaihattheoen()
    {
        List<SongQuery> list=new ArrayList<SongQuery>();

        String[] Column={DatabaseHelper.SONG_ID,DatabaseHelper.SONG_TITLE,DatabaseHelper.SONG_TITLE_SIMPLE,DatabaseHelper.SONG_LYRIC,DatabaseHelper.SONG_SOURCE,DatabaseHelper.SONG_FAVORITE};
        String truyvan="Select " + Column[0]+ "," + Column[1]
                + " , " + Column[2] + " , " + Column[3] + " , " + Column[4]+ " , " + Column[5] +  " From "
                + DatabaseHelper.TABLE_SONG +  " Where "
                + DatabaseHelper.SONG_LANG +" LIKE 'en' " + " AND "+ DatabaseHelper.SONG_MANU + " LIKE '1'" + " AND "+ DatabaseHelper.SONG_TITLE_MINI + " LIKE " +"'"+ a +"%'" ;
        Cursor c=db.rawQuery(truyvan,null);
        c.moveToFirst();
        while(!c.isAfterLast())
        {
            SongQuery item=new SongQuery();
            item.set_id(c.getString(0));
            item.setSname(c.getString(1));
            item.setSnameclean(c.getString(2));
            item.setSlyric(c.getString(3));
            item.setSmeta(c.getString(4));
            item.setFavorite(c.getInt(5));

            list.add(item);
            c.moveToNext();
        }
        c.close();
        return list;
    }

    public List<SongQuery> danhsachbaihattheoen1()
    {
        List<SongQuery> list=new ArrayList<SongQuery>();

        String[] Column={DatabaseHelper.SONG_ID,DatabaseHelper.SONG_TITLE,DatabaseHelper.SONG_TITLE_SIMPLE,DatabaseHelper.SONG_LYRIC,DatabaseHelper.SONG_SOURCE,DatabaseHelper.SONG_FAVORITE};
        String truyvan="Select " + Column[0]+ "," + Column[1]
                + " , " + Column[2] + " , " + Column[3] + " , " + Column[4]+ " , " + Column[5] +  " From "
                + DatabaseHelper.TABLE_SONG +  " Where "
                + DatabaseHelper.SONG_LANG +" LIKE 'en' " + " AND "+ DatabaseHelper.SONG_MANU + " LIKE '2'" + " AND "+ DatabaseHelper.SONG_TITLE_MINI + " LIKE " +"'"+ a +"%'" ;
        Cursor c=db.rawQuery(truyvan,null);
        c.moveToFirst();
        while(!c.isAfterLast())
        {
            SongQuery item=new SongQuery();
            item.set_id(c.getString(0));
            item.setSname(c.getString(1));
            item.setSnameclean(c.getString(2));
            item.setSlyric(c.getString(3));
            item.setSmeta(c.getString(4));
            item.setFavorite(c.getInt(5));

            list.add(item);
            c.moveToNext();
        }
        c.close();
        return list;
    }
    public List<SongQuery> danhsachbaihattheofavorite1()
    {
        List<SongQuery> list=new ArrayList<SongQuery>();

        String[] Column={DatabaseHelper.SONG_ID,DatabaseHelper.SONG_TITLE,DatabaseHelper.SONG_TITLE_SIMPLE,DatabaseHelper.SONG_LYRIC,DatabaseHelper.SONG_SOURCE,DatabaseHelper.SONG_FAVORITE};
        String truyvan="Select " + Column[0]+ "," + Column[1]
                + " , " + Column[2] + " , " + Column[3] + " , " + Column[4]+ " , " + Column[5] +  " From "
                + DatabaseHelper.TABLE_SONG +  " Where "
                + DatabaseHelper.SONG_FAVORITE +"=3";
        Cursor c=db.rawQuery(truyvan,null);
        c.moveToFirst();
        while(!c.isAfterLast())
        {
            SongQuery item=new SongQuery();
            item.set_id(c.getString(0));
            item.setSname(c.getString(1));
            item.setSnameclean(c.getString(2));
            item.setSlyric(c.getString(3));
            item.setSmeta(c.getString(4));
            item.setFavorite(c.getInt(5));

            list.add(item);
            c.moveToNext();
        }
        c.close();
        return list;
    }
    public List<SongQuery> danhsachbaihat()
    {
        List<SongQuery> list=new ArrayList<SongQuery>();
        String[] Column={DatabaseHelper.SONG_ID,DatabaseHelper.SONG_TITLE,DatabaseHelper.SONG_TITLE_SIMPLE,DatabaseHelper.SONG_LYRIC,DatabaseHelper.SONG_SOURCE,DatabaseHelper.SONG_FAVORITE};
        Cursor c=db.query(DatabaseHelper.TABLE_SONG,Column,null,null,null,null,null);
        c.moveToFirst();
        while(!c.isAfterLast())
        {
            SongQuery item=new SongQuery();
            item.set_id(c.getString(0));
            item.setSname(c.getString(1));
            item.setSnameclean(c.getString(2));
            item.setSlyric(c.getString(3));
            item.setSmeta(c.getString(4));
            item.setFavorite(c.getInt(5));

            list.add(item);
            c.moveToNext();

        }
        c.close();
        return list;
    }


}