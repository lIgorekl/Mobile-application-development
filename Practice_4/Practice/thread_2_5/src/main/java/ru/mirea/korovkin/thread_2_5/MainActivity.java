package ru.mirea.korovkin.thread_2_5;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

import ru.mirea.korovkin.thread_2_5.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private int counter = 0; // счётчик потоков

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Инициализация binding (связываем XML с кодом)
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Получаем текущий (главный) поток
        Thread mainThread = Thread.currentThread();

        // Выводим имя главного потока
        binding.textViewResult.setText("Имя потока: " + mainThread.getName());

        // Меняем имя главного потока
        mainThread.setName("МОЯ ГРУППА: 08, НОМЕР: 13");

        // Выводим новое имя потока
        binding.textViewResult.append("\nНовое имя: " + mainThread.getName());

        // Логируем стек вызовов (для отладки)
        Log.d("THREAD", "Stack: " + Arrays.toString(mainThread.getStackTrace()));

        // Логируем группу потоков
        Log.d("THREAD", "Group: " + mainThread.getThreadGroup());

        // Обработчик обычного нажатия кнопки
        binding.buttonCalculate.setOnClickListener(v -> {

            // Получаем данные из полей ввода
            String lessonsStr = binding.editTextLessons.getText().toString();
            String daysStr = binding.editTextDays.getText().toString();

            // Проверка на пустые значения
            if (lessonsStr.isEmpty() || daysStr.isEmpty()) {
                binding.textViewResult.setText("Введите данные");
                return;
            }

            new Thread(() -> {

                // Увеличиваем номер потока
                int numberThread = counter++;
                Log.d("THREAD", "Запущен поток № " + numberThread);

                try {

                    int lessons = Integer.parseInt(lessonsStr);
                    int days = Integer.parseInt(daysStr);

                    double result = (double) lessons / days;

                    Thread.sleep(3000);

                    // Возвращаемся в главный поток для обновления UI
                    runOnUiThread(() -> {
                        binding.textViewResult.setText("Среднее: " + result);
                    });

                } catch (Exception e) {
                    runOnUiThread(() -> {
                        binding.textViewResult.setText("Ошибка ввода");
                    });
                }

                Log.d("THREAD", "Завершён поток № " + numberThread);

            }).start();
        });

        // Долгое нажатие — демонстрация зависания главного потока
        binding.buttonCalculate.setOnLongClickListener(v -> {

            // Засекаем время окончания "зависания"
            long endTime = System.currentTimeMillis() + 5000;

            // Блокируем главный поток
            while (System.currentTimeMillis() < endTime) {
                // UI в этот момент НЕ отвечает
            }

            return true;
        });
    }
}