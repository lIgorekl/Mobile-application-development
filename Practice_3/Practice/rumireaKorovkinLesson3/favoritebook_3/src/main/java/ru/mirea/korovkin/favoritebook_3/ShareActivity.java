package ru.mirea.korovkin.favoritebook_3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShareActivity extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        TextView textView = findViewById(R.id.textViewDevBook);
        editText = findViewById(R.id.editTextBook);

        String devBook = getIntent().getStringExtra(MainActivity.KEY);
        textView.setText("Любимая книга разработчика: " + devBook);
    }

    public void sendResult(android.view.View view) {
        String text = editText.getText().toString();

        Intent data = new Intent();
        data.putExtra(MainActivity.USER_MESSAGE, text);

        setResult(Activity.RESULT_OK, data);
        finish();
    }
}