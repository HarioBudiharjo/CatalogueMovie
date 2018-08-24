package com.hariobudiharjo.cataloguemovie.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.hariobudiharjo.cataloguemovie.Database.DatabaseHelper;
import com.hariobudiharjo.cataloguemovie.Model.MovieItems;

import java.util.ArrayList;

public class MovieHelper {
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    private static String DATABASE_NAME = "dbkamus";
    private static String TABLE_NAME = "table_movie";
    private static String ID = "id";
    private static String JUDUL = "judul";
    private static String DESKRIPSI = "deskripsi";
    private static String GAMBAR = "gambar";
    private static String RELEASE = "rilis";

    public MovieHelper(Context context) {
        this.context = context;
    }

    public MovieHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }

    public ArrayList<MovieItems> query() {
        ArrayList<MovieItems> arrayList = new ArrayList<MovieItems>();
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, ID + " DESC", null);
        cursor.moveToFirst();
        MovieItems kamus;
        if (cursor.getCount() > 0) {
            do {

                kamus = new MovieItems();
                kamus.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
                kamus.setJudul(cursor.getString(cursor.getColumnIndexOrThrow(JUDUL)));
                kamus.setDeskripsi(cursor.getString(cursor.getColumnIndexOrThrow(DESKRIPSI)));
                kamus.setGambar(cursor.getString(cursor.getColumnIndexOrThrow(GAMBAR)));
                kamus.setRelease(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE)));

                arrayList.add(kamus);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    //TODO ganti penamaan
    public ArrayList<MovieItems> getDataById(String id) {
        String result = "";
        Cursor cursor = database.query(TABLE_NAME, null, ID + "=?", new String[]{id}, null, null, ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<MovieItems> arrayList = new ArrayList<>();
        MovieItems mahasiswaModel;
        if (cursor.getCount() > 0) {
            do {
                mahasiswaModel = new MovieItems();
                mahasiswaModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
                mahasiswaModel.setJudul(cursor.getString(cursor.getColumnIndexOrThrow(JUDUL)));
                mahasiswaModel.setDeskripsi(cursor.getString(cursor.getColumnIndexOrThrow(DESKRIPSI)));
                mahasiswaModel.setGambar(cursor.getString(cursor.getColumnIndexOrThrow(GAMBAR)));
                mahasiswaModel.setRelease(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE)));

                arrayList.add(mahasiswaModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<MovieItems> getAllData() {
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<MovieItems> arrayList = new ArrayList<>();
        MovieItems mahasiswaModel;
        if (cursor.getCount() > 0) {
            do {
                mahasiswaModel = new MovieItems();
                mahasiswaModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
                mahasiswaModel.setJudul(cursor.getString(cursor.getColumnIndexOrThrow(JUDUL)));
                mahasiswaModel.setDeskripsi(cursor.getString(cursor.getColumnIndexOrThrow(DESKRIPSI)));
                mahasiswaModel.setGambar(cursor.getString(cursor.getColumnIndexOrThrow(GAMBAR)));
                mahasiswaModel.setRelease(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE)));


                arrayList.add(mahasiswaModel);
                cursor.moveToNext();


            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(MovieItems kamus) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(ID, kamus.getId());
        initialValues.put(JUDUL, kamus.getJudul());
        initialValues.put(DESKRIPSI, kamus.getDeskripsi());
        initialValues.put(GAMBAR, kamus.getGambar());
        initialValues.put(RELEASE, kamus.getRelease());
        return database.insert(TABLE_NAME, null, initialValues);
    }

    public int update(MovieItems kamus) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(JUDUL, kamus.getJudul());
        initialValues.put(DESKRIPSI, kamus.getDeskripsi());
        initialValues.put(GAMBAR, kamus.getGambar());
        initialValues.put(RELEASE, kamus.getRelease());
        return database.update(TABLE_NAME, initialValues, ID + "= '" + kamus.getId() + "'", null);
    }

    public int delete(int id) {
        return database.delete(TABLE_NAME, ID + " = '" + id + "'", null);
    }

    public void beginTransaction() {
        database.beginTransaction();
    }

    public void setTransactionSuccess() {
        database.setTransactionSuccessful();
    }

    public void endTransaction() {
        database.endTransaction();
    }

    public void insertTransaction(MovieItems mahasiswaModel) {
        String sql = "INSERT INTO " + TABLE_NAME + " (" + JUDUL + "," + DESKRIPSI + "," + GAMBAR + "," + RELEASE + ") VALUES (?, ?, ?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, mahasiswaModel.getJudul());
        stmt.bindString(2, mahasiswaModel.getDeskripsi());
        stmt.bindString(3, mahasiswaModel.getGambar());
        stmt.bindString(4, mahasiswaModel.getRelease());
        stmt.execute();
        stmt.clearBindings();

    }

    //provider
    public Cursor queryByIdProvider(String id) {
        return database.query(TABLE_NAME, null
                , ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(TABLE_NAME
                , null
                , null
                , null
                , null
                , null
                , ID + " DESC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(TABLE_NAME, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(TABLE_NAME, values, ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(TABLE_NAME, ID + " = ?", new String[]{id});
    }
}
