package com.xinxin.facelinker.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xinxin on 2015/7/24.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    //数据库创建时调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table person (_id integer primary key autoincrement, name char(10), phone char(20), money integer(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
