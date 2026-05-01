package ru.mirea.korovkin.serviceapp_4_1;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.korovkin.serviceapp_4_1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(new String[]{
                    android.Manifest.permission.POST_NOTIFICATIONS
            }, 1);
        }

        // кнопка запуска сервиса
        binding.buttonPlay.setOnClickListener(v -> {
            Intent intent = new Intent(this, PlayerService.class);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                startForegroundService(intent); // для новых Android
            } else {
                startService(intent); // для старых Android
            }
        });

        // кнопка остановки
        binding.buttonStop.setOnClickListener(v -> {
            Intent intent = new Intent(this, PlayerService.class);
            stopService(intent);
        });
    }
}