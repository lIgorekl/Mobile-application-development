package ru.mirea.korovkin.mireaproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.mirea.korovkin.mireaproject.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.createAccountButton.setOnClickListener(v -> {
            createAccount();
        });

        binding.signInButton.setOnClickListener(v -> {
            signIn();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser =
                mAuth.getCurrentUser();

        if (currentUser != null) {

            openMainActivity();
        }
    }

    private void createAccount() {

        String email =
                binding.emailEditText.getText()
                        .toString();

        String password =
                binding.passwordEditText.getText()
                        .toString();

        if (!validateForm(email, password)) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(
                        email,
                        password
                )
                .addOnCompleteListener(this,
                        task -> {

                            if (task.isSuccessful()) {

                                Toast.makeText(
                                        LoginActivity.this,
                                        "Account created",
                                        Toast.LENGTH_SHORT
                                ).show();

                                openMainActivity();

                            } else {

                                Toast.makeText(
                                        LoginActivity.this,
                                        "Create account failed",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        });
    }

    private void signIn() {

        String email =
                binding.emailEditText.getText()
                        .toString();

        String password =
                binding.passwordEditText.getText()
                        .toString();

        if (!validateForm(email, password)) {
            return;
        }

        mAuth.signInWithEmailAndPassword(
                        email,
                        password
                )
                .addOnCompleteListener(this,
                        task -> {

                            if (task.isSuccessful()) {

                                Toast.makeText(
                                        LoginActivity.this,
                                        "Sign in success",
                                        Toast.LENGTH_SHORT
                                ).show();

                                openMainActivity();

                            } else {

                                Toast.makeText(
                                        LoginActivity.this,
                                        "Authentication failed",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        });
    }

    private boolean validateForm(
            String email,
            String password
    ) {

        if (TextUtils.isEmpty(email)) {

            binding.emailEditText
                    .setError("Required");

            return false;
        }

        if (TextUtils.isEmpty(password)) {

            binding.passwordEditText
                    .setError("Required");

            return false;
        }

        if (password.length() < 6) {

            binding.passwordEditText
                    .setError("Minimum 6 symbols");

            return false;
        }

        return true;
    }

    private void openMainActivity() {

        Intent intent =
                new Intent(
                        LoginActivity.this,
                        MainActivity.class
                );

        startActivity(intent);

        finish();
    }
}