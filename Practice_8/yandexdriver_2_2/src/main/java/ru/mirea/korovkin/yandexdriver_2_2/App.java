package ru.mirea.korovkin.yandexdriver_2_2;

import android.app.Application;

import com.yandex.mapkit.MapKitFactory;

public class App extends Application {

    private final String MAPKIT_API_KEY =
            "73c6070d-97ac-4984-ab36-0258eeefd8fc";

    @Override
    public void onCreate() {
        super.onCreate();

        MapKitFactory.setApiKey(MAPKIT_API_KEY);
    }
}