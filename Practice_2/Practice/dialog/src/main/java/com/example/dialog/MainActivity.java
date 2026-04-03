package com.example.dialog;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onClickShowDialog(View view) {
        MyDialogFragment dialog = new MyDialogFragment();
        dialog.show(getSupportFragmentManager(), "mirea");
    }

    public void onOkClicked() {
        Toast.makeText(this, "Иду дальше", Toast.LENGTH_LONG).show();
    }

    public void onCancelClicked() {
        Toast.makeText(this, "Нет", Toast.LENGTH_LONG).show();
    }

    public void onNeutralClicked() {
        Toast.makeText(this, "На паузе", Toast.LENGTH_LONG).show();
    }


    public void onClickShowTimeDialog(View view) {
        new MyTimeDialogFragment().show(getSupportFragmentManager(), "time");
    }

    public void onClickShowDateDialog(View view) {
        new MyDateDialogFragment().show(getSupportFragmentManager(), "date");
    }

    public void onClickShowProgressDialog(View view) {
        new MyProgressDialogFragment().show(getSupportFragmentManager(), "progress");
    }

    public void onTimeSelected(int hour, int minute) {
        Toast.makeText(this, "Время: " + hour + ":" + minute, Toast.LENGTH_LONG).show();
    }

    public void onDateSelected(int year, int month, int day) {
        Toast.makeText(this, "Дата: " + day + "/" + (month+1) + "/" + year, Toast.LENGTH_LONG).show();
    }
}