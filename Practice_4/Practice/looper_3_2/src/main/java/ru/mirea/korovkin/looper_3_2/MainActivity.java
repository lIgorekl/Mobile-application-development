package ru.mirea.korovkin.looper_3_2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.korovkin.looper_3_2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MyLooper myLooper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Подключаем ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Handler главного потока (UI)
        // сюда будут приходить данные из фонового потока
        Handler mainHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                String result = msg.getData().getString("result");
                binding.textView.setText(result);
            }
        };

        // Запускаем поток с Looper
        myLooper = new MyLooper(mainHandler);
        myLooper.start();

        // Обработка нажатия кнопки
        binding.buttonSend.setOnClickListener(v -> {

            // Создаём сообщение
            Message msg = Message.obtain();
            Bundle bundle = new Bundle();

            // Передаём данные
            bundle.putInt("age", 21);
            bundle.putString("job", "Студент");

            msg.setData(bundle);

            // Отправляем сообщение в другой поток
            // handler может быть ещё не создан -> проверяем
            if (myLooper.mHandler != null) {
                myLooper.mHandler.sendMessage(msg);
            }
        });
    }
}