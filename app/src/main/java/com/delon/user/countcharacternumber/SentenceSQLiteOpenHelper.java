package com.delon.user.countcharacternumber;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 2016/05/31.
 */

public class SentenceSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "save_sentence.db";
    private static final int DB_VERSION = 2;

    static final String TABLE_NAME = "myTable";
    static final String SENTENCE_ID = "id";
    static final String SENTENCE_TITLE = "title";
    static final String SENTENCE_CONTENTS = "contents";
    static final String SENTENCE_NUMBER = "number";

    public SentenceSQLiteOpenHelper(Context c) {
        super(c, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +" ( " + SENTENCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SENTENCE_TITLE + " TEXT NOT NULL, " +
                SENTENCE_CONTENTS + " TEXT, " +
                SENTENCE_NUMBER + " INTEGER);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_NAME + ";");
        onCreate(db);
    }



}
