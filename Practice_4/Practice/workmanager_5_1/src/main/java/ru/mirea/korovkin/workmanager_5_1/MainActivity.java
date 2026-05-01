package ru.mirea.korovkin.workmanager_5_1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import ru.mirea.korovkin.workmanager_5_1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // При нажатии запускаем Worker
        binding.button.setOnClickListener(v -> {

            // Создаём задачу
            /*Constraints constraints = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.UNMETERED) // только Wi-Fi
                    .setRequiresCharging(true) // только при зарядке
                    .build();

            OneTimeWorkRequest request =
                    new OneTimeWorkRequest.Builder(MyWorker.class)
                            .setConstraints(constraints)
                            .build();*/

            OneTimeWorkRequest request =
                    new OneTimeWorkRequest.Builder(MyWorker.class)
                            .build();

            // Отправляем в WorkManager
            WorkManager
                    .getInstance(this)
                    .enqueue(request);
        });
    }
}