package ru.mirea.korovkin.lesson5;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.korovkin.lesson5.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SensorManager sensorManager =
                (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> sensors =
                sensorManager.getSensorList(Sensor.TYPE_ALL);

        ArrayList<String> sensorList = new ArrayList<>();

        for (Sensor sensor : sensors) {

            String sensorInfo =
                    "Название: " + sensor.getName() +
                            "\nТип: " + sensor.getStringType() +
                            "\nМаксимум: " + sensor.getMaximumRange();

            sensorList.add(sensorInfo);
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        sensorList
                );

        binding.listViewSensors.setAdapter(adapter);
    }
}