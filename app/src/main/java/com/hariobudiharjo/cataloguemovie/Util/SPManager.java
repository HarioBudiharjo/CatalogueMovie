package com.hariobudiharjo.cataloguemovie.Util;

import android.content.Context;
import android.content.SharedPreferences;

public class SPManager {
    private static final String PREF_NAME = "SPCatalogueMovie";
    private static final String KEY_BAHASA = "bahasa";
    private final SharedPreferences pref;
    private static final int PRIVATE_MODE = 0;

    public SPManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    }

    public void setBahasa(String bahasa) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_BAHASA, bahasa);
        editor.apply();
    }

    public String getBahasa() {
        return pref.getString(KEY_BAHASA, "id");
    }

}
