package com.hariobudiharjo.cataloguemovie.Database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
    private static String DATABASE_NAME = "dbkamus";
    private static String TABLE_NAME = "table_movie";
    private static String ID = "id";
    private static String JUDUL = "judul";
    private static String DESKRIPSI = "deskripsi";
    private static String GAMBAR = "gambar";
    private static String RELEASE = "rilis";

    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_MAHASISWA = "create table " + TABLE_NAME +
            " (" + ID + " integer primary key, " +
            JUDUL + " text not null, " +
            DESKRIPSI + " text not null, " +
            GAMBAR + " text not null, " +
            RELEASE + " text not null);";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MAHASISWA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
