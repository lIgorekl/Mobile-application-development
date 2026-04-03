package ru.mirea.korovkin.intentapp_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView textView = findViewById(R.id.textViewResult);

        Intent intent = getIntent();
        String time = intent.getStringExtra("time");

        int number = intent.getIntExtra("number", 0);
        int square = number * number;

        textView.setText("КВАДРАТ МОЕГО НОМЕРА = " + square +
                ", текущее время: " + time);
    }
}