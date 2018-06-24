package com.hariobudiharjo.cataloguemovie;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieItems {
    private int id;
    private String judul;
    private String deskripsi;
    private String gambar;
    private String release;

    public MovieItems(JSONObject object) throws JSONException {

        int id = object.getInt("id");
        String judul = object.getString("title");
        String deskripsi = object.getString("overview");
        String gambar = object.getString("poster_path");
        String release = object.getString("release_date");

        gambar = "http://image.tmdb.org/t/p/w185/" + gambar;
        this.id = id;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.gambar = gambar;
        this.release = release;
    }

    public int getId() {
        return id;
    }

    public String getJudul() {
        return judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getGambar() {
        return gambar;
    }

    public String getRelease() {
        return release;
    }
}

