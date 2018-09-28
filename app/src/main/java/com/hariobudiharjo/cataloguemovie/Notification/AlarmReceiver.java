package com.hariobudiharjo.cataloguemovie.Notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.hariobudiharjo.cataloguemovie.BuildConfig;
import com.hariobudiharjo.cataloguemovie.Model.MovieItems;
import com.hariobudiharjo.cataloguemovie.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String TYPE_ONE_TIME = "OneTimeAlarm";
    public static final String TYPE_REPEATING = "RepeatingAlarm";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE = "type";

    private final int ID_REPEATING_LIST = 101;
    private final int ID_REPEATING_BACK = 102;
    String TAG = "JAM REPEAT";

    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        String message = intent.getStringExtra(EXTRA_MESSAGE);

//        String title = type.equalsIgnoreCase(TYPE_ONE_TIME) ? TYPE_ONE_TIME : TYPE_REPEATING;
        String title = type;
        int notifId = type.equalsIgnoreCase("Catalogue Movie") ? ID_REPEATING_LIST : ID_REPEATING_BACK;

//        showToast(context, title, message);

        if (notifId == ID_REPEATING_LIST) {
            getNowPlaying(context);
        } else {
            showAlarmNotification(context, title, message, notifId);
        }

    }

    // Gunakan metode ini untuk menampilkan toast

    private void showToast(Context context, String title, String message) {
        Toast.makeText(context, title + " : " + message, Toast.LENGTH_LONG).show();
    }

    // Gunakan metode ini untuk menampilkan notifikasi
    private void showAlarmNotification(Context context, String title, String message, int notifId) {
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "AlarmManager channel";

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_access_time_black)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        /*
        Untuk android Oreo ke atas perlu menambahkan notification channel
        Materi ini akan dibahas lebih lanjut di modul extended
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            /* Create or update. */
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);
        }

    }


    // Metode ini digunakan untuk menjalankan alarm repeating
    public void setRepeatingAlarm(Context context, String type, String time, String message, int id_alarm) {

        Log.d(TAG, "setRepeatingAlarm: " + time);
        // Validasi inputan waktu terlebih dahulu
        if (isDateInvalid(time, TIME_FORMAT)) return;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);

        String timeArray[] = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id_alarm, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        Toast.makeText(context, "Repeating alarm set up", Toast.LENGTH_SHORT).show();
    }


    // Gunakan metode ini untuk mengecek apakah alarm tersebut sudah terdaftar di alarm manager
//    public boolean isAlarmSet(Context context, String type) {
//        Intent intent = new Intent(context, AlarmReceiver.class);
//        int requestCode = type.equalsIgnoreCase(TYPE_ONE_TIME) ? ID_ONETIME : ID_REPEATING;
//
//        return PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_NO_CREATE) != null;
//    }

    private String TIME_FORMAT = "HH:mm";

    public boolean isDateInvalid(String date, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
            df.setLenient(false);
            df.parse(date);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }


    private void getNowPlaying(final Context context) {
        Log.d("Masuk Function", "Masuk pak eko!!!!");
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final String dateToStr = format.format(today);

        Log.d(TAG, "Running");
        AsyncHttpClient client = new AsyncHttpClient();
        String api_key = BuildConfig.API_KEY;
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + api_key + "&language=en-US";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.d(TAG, result);
                try {
                    Log.d(TAG, "onSuccess: Masuk success");
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        MovieItems movieItem = new MovieItems(movie);
                        if (movieItem.getRelease().equals(dateToStr)) {
                            Log.d(TAG, "onSuccess: " + movieItem);
                            showAlarmNotification(context, movieItem.getJudul(), movieItem.getDeskripsi(), ID_REPEATING_LIST);
                            Log.d("Masuk Notif", movieItem.getJudul() + " " + movieItem.getDeskripsi());
                        }else {
                            Log.d("Masuk berkeriteria", movieItem.getJudul() + " " + movieItem.getDeskripsi());
//                            showAlarmNotification(context, movieItem.getJudul() + "ganteng", movieItem.getDeskripsi(), ID_REPEATING_LIST);
                        }
//                        movieItems.add(movieItem);
                    }
//                    Log.d(TAG, "onSuccess: " + movieItems);
                } catch (Exception e) {
                    Log.d(TAG, "onSuccess: masuk error : " + e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}

