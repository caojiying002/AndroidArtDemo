package com.example.androidart.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.MainThread;

import static com.example.androidart.TAGs.TAG_PROVIDER;

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "book_provider.db";
    private static final int DB_VERSION = 1;

    public static final String BOOK_TABLE_NAME = "book";
    public static final String USER_TABLE_NAME = "user";

    private String CREATE_BOOK_TABLE = "CREATE TABLE IF NOT EXISTS "
            + BOOK_TABLE_NAME + "(_id INTEGER PRIMARY KEY," + "name TEXT)";

    private String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS "
            + USER_TABLE_NAME + "(_id INTEGER PRIMARY KEY," + "name TEXT,"
            + "sex INT)";

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @MainThread
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG_PROVIDER, "DbOpenHelper onCreate, threadName = " + Thread.currentThread().getName());
        db.execSQL(CREATE_BOOK_TABLE);
        db.execSQL(CREATE_USER_TABLE);


        db.execSQL("insert into book values(3,'Android');");
        db.execSQL("insert into book values(4,'Ios');");
        db.execSQL("insert into book values(5,'Html5');");
        db.execSQL("insert into user values(1,'jake',1);");
        db.execSQL("insert into user values(2,'jasmine',0);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO ignored
    }
}
