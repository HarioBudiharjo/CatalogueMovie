package com.hariobudiharjo.favoritmovie.Provider;

import android.database.Cursor;
import android.net.Uri;

public class DatabaseContract {
    public static String TABLE_NOTE = "note";

    public static final String AUTHORITY = "com.hariobudiharjo.cataloguemovie";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NOTE)
            .build();
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }
    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong( cursor.getColumnIndex(columnName) );
    }
}
