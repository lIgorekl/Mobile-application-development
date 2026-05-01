package ru.mirea.korovkin.mireaproject_6;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {

    public MyWorker(@NonNull Context context,
                    @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {

        try {
            Thread.sleep(3000); // имитация работы
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Result.success();
    }
}
