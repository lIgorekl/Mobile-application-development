package ru.mirea.korovkin.looper_3_2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class MyLooper extends Thread {

    public Handler mHandler;
    private Handler mainHandler;

    public MyLooper(Handler mainThreadHandler) {
        this.mainHandler = mainThreadHandler;
    }

    @Override
    public void run() {

        // Создаём очередь сообщений в этом потоке
        Looper.prepare();

        // Handler для обработки входящих сообщений
        mHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {

                // Получаем данные из сообщения
                int age = msg.getData().getInt("age");
                String job = msg.getData().getString("job");

                try {
                    // Задержка = возраст * 100
                    Thread.sleep(age * 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Формируем результат
                String result = "Возраст: " + age + ", Профессия: " + job;

                // Отправляем результат обратно в главный поток
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", result);
                message.setData(bundle);

                mainHandler.sendMessage(message);
            }
        };

        // Запускаем бесконечный цикл обработки сообщений
        Looper.loop();
    }
}