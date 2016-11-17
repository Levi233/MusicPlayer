package com.chenhao.musicplayer.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by chenhao on 2016/11/9.
 */

public class MusicDao {
    private SQLiteDatabase mDB;
    public MusicDao(Context context) {
        MySQLiteOpenHelper openHelper = new MySQLiteOpenHelper(context);
        mDB = openHelper.getWritableDatabase();
    }

    public boolean add(long rid,String name,String artist,String url){
        ContentValues values = new ContentValues();
        values.put("rid", rid);
        values.put("name", name);
        values.put("artist", artist);
        values.put("url",url);
        long result = mDB.insert("info", null, values);
        if(result == -1){
            return false;
        }
        return true;
    }

    public void del(){
        mDB.delete("info", null, null);
    }

    public Cursor find(){
        Cursor cursor = mDB.query("info", null, null, null, null, null, null);
        return cursor;
    }
}
