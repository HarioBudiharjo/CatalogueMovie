package com.hariobudiharjo.cataloguemovie.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.hariobudiharjo.cataloguemovie.Notification.AlarmReceiver;
import com.hariobudiharjo.cataloguemovie.Fragment.FavoriteFragment;
import com.hariobudiharjo.cataloguemovie.Fragment.NowPlayingFragment;
import com.hariobudiharjo.cataloguemovie.R;
import com.hariobudiharjo.cataloguemovie.Fragment.UpComingFragment;
import com.hariobudiharjo.cataloguemovie.Util.SPManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.hariobudiharjo.cataloguemovie.Provider.DatabaseContract.CONTENT_URI;

public class TabActivity extends AppCompatActivity {

    private AlarmReceiver alarmReceiver;

    private String TAG = "DEBUG";
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private SPManager pref;

    private ViewPager mViewPager;

    private Cursor list;

    private static String ID = "id";
    private static String JUDUL = "judul";
    private static String DESKRIPSI = "deskripsi";
    private static String GAMBAR = "gambar";
    private static String RELEASE = "rilis";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate savedInstanceState: " + savedInstanceState);

        Date today = new Date();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateToStr = format.format(today);
//        System.out.println(dateToStr);

        Log.d(TAG, "onCreate WAKTU: " + dateToStr);

        alarmReceiver = new AlarmReceiver();
        alarmReceiver.setRepeatingAlarm(this, "Catalogue Movie!", "07:00", "Catalogue Movie missing you!!!", 102);
        alarmReceiver.setRepeatingAlarm(this, "Catalogue Movie", "08:00", "list movie!!!", 101);
        list = getContentResolver().query(CONTENT_URI, null, null, null, null);

        while (list.moveToNext()) {
            Log.d(TAG + "CURSOR", "onCreate: " + list.getString(list.getColumnIndex(JUDUL)) + " : " + list.getString(list.getColumnIndex(ID)));
        }
        Log.d(TAG, "onCreate: " + list.toString());
        pref = new SPManager(this);
        String languageToLoad = pref.getBahasa();
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        setContentView(R.layout.activity_tab);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSearch();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.abIndo:
                pref.setBahasa("id");
                finish();
                startActivity(getIntent());
                return true;
            case R.id.abEnglish:
                pref.setBahasa("en");
                finish();
                startActivity(getIntent());
                return true;
            default:
                return true;
        }
    }

    private void goToSearch() {
        startActivity(new Intent(this, MainActivity.class));
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    NowPlayingFragment nowPlayingFragment = new NowPlayingFragment();
                    return nowPlayingFragment;
                case 1:
                    UpComingFragment upComingFragment = new UpComingFragment();
                    return upComingFragment;
                case 2:
                    FavoriteFragment favoriteFragment = new FavoriteFragment();
                    return favoriteFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
