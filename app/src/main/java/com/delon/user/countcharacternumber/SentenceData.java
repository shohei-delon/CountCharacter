package com.delon.user.countcharacternumber;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by User on 2016/05/31.
 */

class SentenceData {

    static private SQLiteDatabase database;
    private SentenceSQLiteOpenHelper helper;

    SentenceData(Context context){
        helper = new SentenceSQLiteOpenHelper(context);
        database = helper.getWritableDatabase();

    }

    Cursor getAll(){
        return database.query(SentenceSQLiteOpenHelper.TABLE_NAME, null, null, null, null, null, null);
    }

    void insert(String title,String contents, int number){
        ContentValues values = new ContentValues();
        values.put(SentenceSQLiteOpenHelper.SENTENCE_TITLE, title);
        values.put(SentenceSQLiteOpenHelper.SENTENCE_CONTENTS, contents);
        values.put(SentenceSQLiteOpenHelper.SENTENCE_NUMBER, number);
        database.insertOrThrow(SentenceSQLiteOpenHelper.TABLE_NAME, null, values);
    }

    void delete(int id){
        database.delete(SentenceSQLiteOpenHelper.TABLE_NAME, SentenceSQLiteOpenHelper.SENTENCE_ID + " = " + id, null);
    }

    Cursor sortCountNumber(){
        String sql = "SELECT * FROM " + SentenceSQLiteOpenHelper.TABLE_NAME+ " ORDER BY  "+ SentenceSQLiteOpenHelper.SENTENCE_NUMBER + " ;";
        return database.rawQuery(sql,null);
    }

}
