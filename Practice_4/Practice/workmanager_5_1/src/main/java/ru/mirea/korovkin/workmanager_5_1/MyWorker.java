package ru.mirea.korovkin.workmanager_5_1;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {

    public MyWorker(@NonNull Context context,
                    @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    // Главный метод (выполняется в фоне)
    @NonNull
    @Override
    public Result doWork() {

        // Лог — чтобы увидеть в Logcat
        Log.d("WORKER", "Начало работы");

        try {
            Thread.sleep(5000); // имитация долгой задачи
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d("WORKER", "Конец работы");

        // возвращаем результат
        return Result.success();
    }
}