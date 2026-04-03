package ru.mirea.korovkin.sharer_2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextMessage);
        button = findViewById(R.id.buttonSend);

        button.setOnClickListener(v -> {
            String message = editText.getText().toString();

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, message);

            startActivity(Intent.createChooser(intent, "Выбор за вами!"));
        });
    }
}