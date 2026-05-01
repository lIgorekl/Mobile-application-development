package ru.mirea.korovkin.cryptoloader_3_3;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import javax.crypto.SecretKey;

import ru.mirea.korovkin.cryptoloader_3_3.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<String> {

    private ActivityMainBinding binding;
    private static final int LOADER_ID = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(v -> {

            String text = binding.editText.getText().toString();

            // Проверка на пустой ввод
            if (text.isEmpty()) {
                Toast.makeText(this, "Введите текст", Toast.LENGTH_SHORT).show();
                return;
            }

            // Генерация AES-ключа
            SecretKey key = CryptoUtils.generateKey();

            // Шифрование текста
            byte[] encrypted = CryptoUtils.encryptMsg(text, key);

            // Передача данных в Loader через Bundle
            Bundle bundle = new Bundle();
            bundle.putByteArray("encrypted", encrypted);
            bundle.putByteArray("key", key.getEncoded());

            // restartLoader — перезапускает Loader с новыми данными
            LoaderManager.getInstance(this)
                    .restartLoader(LOADER_ID, bundle, this);
        });
    }

    // Создание Loader
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new MyLoader(this, args);
    }

    // Обработка результата от Loader
    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        // Выполняется в главном потоке
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
    }
}