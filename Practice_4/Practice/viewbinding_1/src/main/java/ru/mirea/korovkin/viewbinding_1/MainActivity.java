package ru.mirea.korovkin.viewbinding_1;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import ru.mirea.korovkin.viewbinding_1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Инициализация binding (связываем layout с кодом)
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // Устанавливаем layout через binding
        setContentView(binding.getRoot());

        // Обработчик кнопки Play
        binding.buttonPlay.setOnClickListener(v -> {
            // Меняем текст — имитация проигрывания
            binding.songTitle.setText("Играет музыка");
        });

        binding.buttonPause.setOnClickListener(v -> {
            binding.songTitle.setText("Пауза");
        });
    }
}