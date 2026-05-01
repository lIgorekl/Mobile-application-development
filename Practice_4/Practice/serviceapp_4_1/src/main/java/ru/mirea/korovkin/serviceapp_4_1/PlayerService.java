package ru.mirea.korovkin.serviceapp_4_1;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class PlayerService extends Service {

    private MediaPlayer mediaPlayer;
    public static final String CHANNEL_ID = "MusicChannel";

    @Override
    public IBinder onBind(Intent intent) {
        return null; // привязка не используется
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("SERVICE", "onCreate вызван");

        // Создание уведомления (обязательно для foreground)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Music Service",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManagerCompat.from(this).createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("С днем рождения")
                .setContentText("Играет музыка")
                .setSmallIcon(R.mipmap.ic_launcher);

        // переводим сервис в foreground
        startForeground(1, builder.build());

        // Инициализация плеера
        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        mediaPlayer.setLooping(false);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("SERVICE", "onStartCommand вызван");

        // запуск музыки
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // остановка музыки
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}