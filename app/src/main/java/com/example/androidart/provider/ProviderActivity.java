package com.example.androidart.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.androidart.BaseAppCompatActivity;
import com.example.androidart.R;

import static com.example.androidart.TAGs.TAG_PROVIDER;

public class ProviderActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);

        Uri bookUri = Uri.parse("content://com.example.androidart.bookprovider/book");

        // 遍历图书表
        Cursor bookCursor = null;
        try {
            bookCursor = getContentResolver().query(bookUri, null, null, null, null);
            while (bookCursor != null && bookCursor.moveToNext()) {
                int bookId = bookCursor.getInt(0);
                String bookName = bookCursor.getString(1);
                Book book = new Book(bookId, bookName);
                Log.d(TAG_PROVIDER, "query book: " + book);
            }
        } finally {
            if (bookCursor != null) bookCursor.close();
        }

        // 在图书表中插入数据
        ContentValues values = new ContentValues();
        values.put("_id",6);
        values.put("name", "程序设计的艺术");
        getContentResolver().insert(bookUri, values);

        /*
        // 再次遍历图书
        Log.d(TAG_PROVIDER, "再次遍历book: ");
        try {
            bookCursor = getContentResolver().query(bookUri, null, null, null, null);
            while (bookCursor != null && bookCursor.moveToNext()) {
                int bookId = bookCursor.getInt(0);
                String bookName = bookCursor.getString(1);
                Book book = new Book(bookId, bookName);
                Log.d(TAG_PROVIDER, "query book: " + book);
            }
        } finally {
            if (bookCursor != null) bookCursor.close();
        }
         */

        // 遍历用户表
        Uri userUri = Uri.parse("content://com.example.androidart.bookprovider/user");
        Cursor userCursor = null;
        try {
            userCursor = getContentResolver().query(userUri, null, null, null, null);
            while (userCursor != null && userCursor.moveToNext()) {
                User user = new User();
                user.userId = userCursor.getInt(0);
                user.userName = userCursor.getString(1);
                user.isMale = (userCursor.getInt(2) > 0);
                Log.d(TAG_PROVIDER, "query user: " + user);
            }
        } finally {
            if (userCursor != null) userCursor.close();
        }
    }
}
