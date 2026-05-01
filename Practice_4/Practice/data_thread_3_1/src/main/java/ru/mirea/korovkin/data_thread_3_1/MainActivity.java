package ru.mirea.korovkin.data_thread_3_1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import ru.mirea.korovkin.data_thread_3_1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonStart.setOnClickListener(v -> {

            // Runnable 1
            final Runnable runn1 = () -> {
                binding.tvInfo.setText(
                        "1. runOnUiThread — выполняется сразу в UI потоке\n"
                );
            };

            // Runnable 2
            final Runnable runn2 = () -> {
                binding.tvInfo.append(
                        "2. post — ставится в очередь и выполняется после\n"
                );
            };

            // Runnable 3
            final Runnable runn3 = () -> {
                binding.tvInfo.append(
                        "3. postDelayed — выполняется с задержкой\n"
                );
            };

            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(2);

                    // 1 способ
                    runOnUiThread(runn1);

                    TimeUnit.SECONDS.sleep(1);

                    // 3 способ (с задержкой)
                    binding.tvInfo.postDelayed(runn3, 2000);

                    // 2 способ
                    binding.tvInfo.post(runn2);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        });
    }
}