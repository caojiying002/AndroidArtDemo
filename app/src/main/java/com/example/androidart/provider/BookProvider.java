package com.example.androidart.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.BinderThread;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.content.UriMatcher.NO_MATCH;
import static com.example.androidart.TAGs.TAG_PROVIDER;
import static com.example.androidart.provider.DbOpenHelper.BOOK_TABLE_NAME;
import static com.example.androidart.provider.DbOpenHelper.USER_TABLE_NAME;

public class BookProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.androidart.bookprovider";

    private static final Uri BOOK_URI = Uri.parse("content://" + AUTHORITY + "/book");
    private static final int BOOK_URI_CODE = 0;

    private static final Uri USER_URI = Uri.parse("content://" + AUTHORITY + "/user");
    private static final int USER_URI_CODE = 1;

    private static final UriMatcher URI_MATCHER;
    static {
        URI_MATCHER = new UriMatcher(NO_MATCH);
        URI_MATCHER.addURI(AUTHORITY, "book", BOOK_URI_CODE);
        URI_MATCHER.addURI(AUTHORITY, "user", USER_URI_CODE);
    }

    private SQLiteDatabase mDb;

    @MainThread
    @Override
    public boolean onCreate() {
        Log.d(TAG_PROVIDER, "BookProvider onCreate: threadName = " + Thread.currentThread().getName());

        initProviderData();
        return true;
    }

    private void initProviderData() {
        mDb = new DbOpenHelper(getContext()).getWritableDatabase();
//        mDb.execSQL("delete from " + DbOpenHelper.BOOK_TABLE_NAME);
//        mDb.execSQL("delete from " + DbOpenHelper.USER_TABLE_NAME);
//        mDb.execSQL("insert into book values(3,'Android');");
//        mDb.execSQL("insert into book values(4,'Ios');");
//        mDb.execSQL("insert into book values(5,'Html5');");
//        mDb.execSQL("insert into user values(1,'jake',1);");
//        mDb.execSQL("insert into user values(2,'jasmine',0);");
    }

    private String getTableName(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case BOOK_URI_CODE:
                return BOOK_TABLE_NAME;
            case USER_URI_CODE:
                return USER_TABLE_NAME;
            default:
                Log.w(TAG_PROVIDER, "Unsupported Uri: " + uri);
                return null;
        }
    }

    private ContentResolver getContextContentResolver() {
        return getContext() != null ? getContext().getContentResolver(): null;
    }

    @BinderThread
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(TAG_PROVIDER, "query: threadName = " + Thread.currentThread().getName());
        String table = getTableName(uri);
        if (table == null)
            return null;
        return mDb.query(table, projection, selection, selectionArgs, null, null, sortOrder, null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.d(TAG_PROVIDER, "getType: threadName = " + Thread.currentThread().getName());
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG_PROVIDER, "insert: threadName = " + Thread.currentThread().getName());
        String table = getTableName(uri);
        if (table == null)
            return null;
        long rowId = mDb.insert(table, null, values);
        if (rowId > -1)
            getContext().getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG_PROVIDER, "delete: threadName = " + Thread.currentThread().getName());
        String table = getTableName(uri);
        if (table == null)
            return 0;
        int count = mDb.delete(table, selection, selectionArgs);
        if (count > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG_PROVIDER, "update: threadName = " + Thread.currentThread().getName());
        String table = getTableName(uri);
        if (table == null)
            return 0;
        int count = mDb.update(table, values, selection, selectionArgs);
        if (count > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return 0;
    }
}
